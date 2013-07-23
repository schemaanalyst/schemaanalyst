package dbmonster;

import java.util.List;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.lang.StringBuilder;
import java.io.PrintWriter;
import java.io.File;

import plume.*;

import com.rits.cloning.Cloner;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.deprecated.SchemaAnalyst;
import org.schemaanalyst.deprecated.configuration.Configuration;
import org.schemaanalyst.script.ScriptCreator;
import org.schemaanalyst.script.MutantScriptCreator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.mutation.MutationReport;
import org.schemaanalyst.mutation.MutationReportScore;
import org.schemaanalyst.mutation.MutantReport;
import org.schemaanalyst.mutation.MutantRecord;
import org.schemaanalyst.mutation.DBMonsterSQLExecutionReport;
import org.schemaanalyst.mutation.SQLExecutionRecord;
import org.schemaanalyst.mutation.SQLInsertRecord;
import org.schemaanalyst.mutation.SQLSelectRecord;
import org.schemaanalyst.mutation.mutators.ConstraintMutator;
import org.schemaanalyst.mutation.SpyLogParser;
import org.schemaanalyst.mutation.MutationUtilities;
import org.schemaanalyst.mutation.MutationTypeStatusSummary;

import experiment.ExperimentalResults;
import experiment.ExperimentConfiguration;

public class DBMonster {

    /**
     * Return code for a still born mutant
     */
    private static final int STILL_BORN = -1;
    /**
     * The ending symbol for mutant description abbreviation
     */
    private static final String KILLED = "killed";
    /**
     * The ending symbol for mutant description abbreviation
     */
    private static final String NOTKILLED = "notkilled";
    /**
     * The ending symbol for mutant description abbreviation
     */
    private static final String STILLBORN = "stillborn";
    /**
     * The starting symbol for mutant description abbreviation
     */
    private static final String START = "(";
    /**
     * The ending symbol for mutant description abbreviation
     */
    private static final String END = ",";
    /**
     * The denominator of the fraction for converting to seconds
     */
    private static final double NANODENOMINATOR = 1000000000.0;

    /**
     * The main method for performing database-aware test data generation using
     * search-based methods.
     */
    public static void main(String[] args) {

        // extract all of the database configurations
        Configuration configuration = new Configuration();
        Options options = new Options("DBMonster [options]", configuration);
        options.parse_or_usage(args);

        // print the debugging information
        if (Configuration.debug) {
            System.out.println(options.settings());
        }

        // print the help screen to see the commands
        if (Configuration.help) {
            options.print_usage();
        }

        System.out.println("**** " + Configuration.database);

        // create the mutation report that will store results and calculate the score
        MutationReport mutationReport = new MutationReport();

        // create the SQL execution report for the ORIGINAL create tables
        DBMonsterSQLExecutionReport originalReport = new DBMonsterSQLExecutionReport();

        // create the database using reflection; this is based on the
        // type of the database provided in the configuration (i.e.,
        // the user could request the Postres database in FQN)
        DBMS database = constructDatabase(Configuration.type);
        SQLWriter sqlWriter = database.getSQLWriter();

        // initialize the connection to the real relational database
        DatabaseInteractor databaseInteraction = database.getDatabaseInteractor();

        // create the schema using reflection; this is based on the
        // name of the database provided in the configuration
        Schema schema = constructSchema(Configuration.database);

        // write out the initial configuration because we are running SchemaAnalyst in stand-alone mode
        if (Configuration.standalone) {
            // create the ExperimentalResults for the first time and save them to the file system
            ExperimentConfiguration experimentConfiguration = new ExperimentConfiguration();
            experimentConfiguration.project = Configuration.project;
            ExperimentalResults experimentalResults = new ExperimentalResults();
            experimentalResults.save();
        }

        // reload the experimental results from the file system for this run of SchemaAnalyst
        ExperimentConfiguration.project = configuration.project;
        ExperimentalResults experimentalResults = ExperimentalResults.retrieve();

        // set the parameters for this run of the experiment inside the header and the row of data
        addParametersToResults(experimentalResults);

        // RE-RUN THE ALREADY GENERATED DBMONSTER DATA WITH THE ORIGINAL SCHEMA

        // print the schema as debugging information
        if (Configuration.debug) {
            System.out.println();
            System.out.println(sqlWriter.writeCreateTableStatements(schema));
        }

        // create the schema to a file for reproducibility part; configure both
        // the script for test data generation and the one for mutation analysis
        ScriptCreator scriptCreator = ScriptCreator.getScriptCreator();
        MutantScriptCreator mutantScriptCreator = new MutantScriptCreator();
        if (Configuration.script) {
            scriptCreator.configure();
            mutantScriptCreator.configure();
        }

        // drop tables inside the schema
        List<String> dropTableStatements = sqlWriter.writeDropTableStatements(schema, true);
        for (String statement : dropTableStatements) {
            databaseInteraction.executeUpdate(statement);
            if (Configuration.script) {
                scriptCreator.print(statement + ";");
            }
        }

        // create the schema inside of the real database
        List<String> createTableStatements = sqlWriter.writeCreateTableStatements(schema);
        for (String statement : createTableStatements) {
            int returnCount = databaseInteraction.executeUpdate(statement);
            if (Configuration.script) {
                scriptCreator.print(statement + ";");
            }

            // create a SQLExecutionRecord for this CREATE TABLE
            SQLExecutionRecord currentCreateTable = new SQLExecutionRecord();
            currentCreateTable.setStatement(statement);
            currentCreateTable.setReturnCode(returnCount);

            // add the CREATE TABLE SQLExecutionRecord to the ORIGINAL report
            originalReport.addCreateTableStatement(currentCreateTable);
        }

        // add the ORIGINAL report to the MUTATION report
        mutationReport.setOriginalReport(originalReport);

        // create a list of relevant statements from the SpyLog from DBMonster
        // (we are looking for both SELECT and INSERT statements)
        List<String> selectAndInsertStatements = new ArrayList<String>();

        try {
            selectAndInsertStatements = SpyLogParser.createRelevantComponents(Configuration.spylog);
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the Spy log file!");
            e.printStackTrace();
        }

        // execute each of the select or insert statements to the real database
        for (String selectOrInsertStatement : selectAndInsertStatements) {
            if (Configuration.debug) {
                System.out.println();
                System.out.println(selectOrInsertStatement);
            }

            // transform the empty strings to NULLs in order to give a boost to the DBMonster data generator
            // this will later influence the mutation analysis when it will also run the these transformed
            // statements instead of the ones that contained the emptry strings
            if (Configuration.transformemptystrings) {
                selectOrInsertStatement = SpyLogParser.replaceEmptyStringWithNull(selectOrInsertStatement);
            }

            // extract the number of modified record counts, used in mutation analysis
            Integer returnCounts = databaseInteraction.execute(selectOrInsertStatement);

            // create a new SQL Execution Record for this INSERT statement
            if (SpyLogParser.isInsertStatement(selectOrInsertStatement)) {
                SQLInsertRecord currentInsert = new SQLInsertRecord();
                currentInsert.setStatement(selectOrInsertStatement);
                currentInsert.setReturnCode(returnCounts);

                // we assume that DBMonster is _always_ trying to satisfy the 
                // schema, even though there are some situations when it does not
                // actually do so (we cannot discern the intent of DBMonster)
                currentInsert.tryToSatisfy();

                // add the INSERT SQLExecutionRecord to the ORIGINAL report
                originalReport.addInsertStatement(currentInsert);
            } // create a new SQL Execution Record for this SELECT statement
            else if (SpyLogParser.isSelectStatement(selectOrInsertStatement)) {
                SQLSelectRecord currentSelect = new SQLSelectRecord();
                currentSelect.setStatement(selectOrInsertStatement);
                currentSelect.setReturnCode(returnCounts);

                // add the SELECT SQLExecutionRecord to the ORIGINAL report
                originalReport.addSelectStatement(currentSelect);
            }

        }

        // PERFORM MUTATION ANALYSIS WITH ALL OF THE MUTANT SCHEMAS

        long beginMutationAnalysisTime = System.nanoTime();

        // indicate that we have not yet encountered a still born mutant
        boolean stillBorn = false;

        // NOTE: the mutationReport contains all of the INSERTS that satisfy and
        // negate the schema in the originalReport because the OR was added in first

        // create all of the MUTANT SCHEMAS that we store inside of the database 
        ConstraintMutator cm = new ConstraintMutator();
        List<Schema> mutants = cm.produceMutants(schema);
        int i = 1;
        for (Schema mutant : mutants) {

            if (Configuration.debug) {
                System.out.println("\nMutant " + i);
            }

            if (Configuration.script) {
                mutantScriptCreator.print("-- Running mutant number: " + i);
                List<String> mutantComments = mutant.getComments();
                for (String mutantComment : mutantComments) {
                    mutantScriptCreator.print("-- " + mutantComment);
                }
            }

            // drop tables inside the previous schema
            List<String> dropTableStatementsMutants = sqlWriter.writeDropTableStatements(mutant, true);
            for (String statement : dropTableStatementsMutants) {
                databaseInteraction.executeUpdate(statement);
                if (Configuration.script) {
                    mutantScriptCreator.print(statement + ";");
                }
            }

            // collect all of the comments for this mutant for the MutantReport
            List<String> mutantComments = mutant.getComments();
            StringBuilder mutantCommentsBuilder = new StringBuilder();
            for (String mutantComment : mutantComments) {
                mutantCommentsBuilder.append(mutantComment);
                mutantCommentsBuilder.append(" ");
            }

            // create the MutantReport that will store the details about these inserts
            MutantReport currentMutantReport = new MutantReport();

            // add the comments for this mutant to the currentMutantReport
            // currentMutantReport.setDescription(mutantCommentsBuilder.toString());

            // ** same as SchemaAnalyst, this line of code needs to be better tested

            // add the comments for this mutant to the currentMutantReport
            currentMutantReport.setDescription(MutationUtilities.
                    abbreviateMutantDescription(mutantCommentsBuilder.toString(), START, END));

            // create the MUTANT schema inside of the real database
            List<String> createTableStatementsMutants = sqlWriter.writeCreateTableStatements(mutant);
            for (String statement : createTableStatementsMutants) {
                int returnCount = databaseInteraction.executeUpdate(statement);
                if (Configuration.script) {
                    mutantScriptCreator.print(statement + ";");
                }
                // add the MUTANT schema create table to the current mutant report
                SQLExecutionRecord currentMutantCreateTable = new SQLExecutionRecord();
                currentMutantCreateTable.setStatement(statement);
                currentMutantCreateTable.setReturnCode(returnCount);
                currentMutantReport.addCreateTableStatement(currentMutantCreateTable);

                // we have found a still born mutant and 
                if (returnCount == STILL_BORN) {
                    stillBorn = true;
                }
            }

            // put the SELECT and INSERT lists into a single list 
            DBMonsterSQLExecutionReport retrievedOriginalReport =
                    (DBMonsterSQLExecutionReport) mutationReport.getOriginalReport();
            List<SQLInsertRecord> originalInsertStatements = retrievedOriginalReport.getInsertStatements();
            List<SQLSelectRecord> originalSelectStatements = retrievedOriginalReport.getSelectStatements();
            ArrayList<SQLExecutionRecord> completeList = new ArrayList<SQLExecutionRecord>();
            completeList.addAll(originalInsertStatements);
            completeList.addAll(originalSelectStatements);

            // write the ORIGINAL INSERTS to the file system so that that can be read in and
            // their constraint coverage can be calculated
            if (Configuration.constraintcoverage) {
                saveDBMonsterInserts(originalInsertStatements);
            }

            // run all of the ORIGINAL INSERTS and SELECTS on the mutant schema
            for (SQLExecutionRecord originalInsertRecord : completeList) {
                // create a MutantRecord
                MutantRecord insertMutantRecord = new MutantRecord();

                // extract the statement from the SQLInsertRecord
                String statement = originalInsertRecord.getStatement();

                // add the current statement into the insertMutantRecord
                insertMutantRecord.setStatement(statement);

                // extract the number of modified record counts, used in mutation analysis
                Integer returnCounts = databaseInteraction.execute(statement);

                // add the return code to the insertMutantRecord
                insertMutantRecord.setReturnCode(returnCounts);

                // we are dealing with an insert statement, so it could try to satisfy or negate
                if (originalInsertRecord instanceof SQLInsertRecord) {

                    // indicate whether this insertMutantRecord is satisfying or negating
                    if (((SQLInsertRecord) originalInsertRecord).isTryingToSatisfy()) {
                        insertMutantRecord.tryToSatisfy();
                    } else if (((SQLInsertRecord) originalInsertRecord).isTryingToNegate()) {
                        insertMutantRecord.tryToNegate();
                    }
                } // we are dealing with a select statement, by convention it is always negating
                // (the inserts are actually always satisfying by convention in DBMonster)
                else {
                    insertMutantRecord.tryToNegate();
                }

                // determine whether or not this insert killed the schema mutant; it kills
                // the mutant when its return code is the same as the return code of the
                // orginal insert statement, for the mutant schema instead of original schema
                if (insertMutantRecord.getReturnCode() != originalInsertRecord.getReturnCode()) {
                    insertMutantRecord.setKilled(true);
                }

                // add this insertMutantRecord to the mutant report
                currentMutantReport.addMutantStatement(insertMutantRecord);
            }

            // indicate we have moved on to the next MUTANT
            i++;

            if (stillBorn) {
                currentMutantReport.setStillBorn(true);
                stillBorn = false;
            }

            // store the current insertMutantRecord inside of the mutationReport
            mutationReport.addMutantReport(currentMutantReport);
        }

        // calculate the mutation score and the main summary statistics
        mutationReport.calculateMutationScoresAndStatistics();

        // END TIME FOR mutation analysis
        long endMutationAnalysisTime = System.nanoTime();

        // STEP ONE: save the data about mutation scores from the mutation report into the experimental results 
        saveMutationScoresSpecial(experimentalResults, mutationReport);

        // STEP TWO: save the data about the types and the kill, not kill, still born information into the experimental results
        MutationTypeStatusSummary mutationTypeStatusSummary = mutationReport.createMutationTypeStatusSummary();
        saveMutationTypeInformationRedundant(experimentalResults, mutationTypeStatusSummary);

        // STEP FOUR: save the data about the execution time of test data generation and mutation analysis
        saveExecutionTimeRedundant(experimentalResults,
                (endMutationAnalysisTime - beginMutationAnalysisTime));

        // write out the mutation report for storage and later analysis
        MutationReport.configureForReportSaving();
        MutationReport.save(mutationReport);
        MutationReport.close();

        // close the scriptoutput so that the lines appear in the file
        if (Configuration.script) {
            scriptCreator.close();
            mutantScriptCreator.close();
        }

        // write out the experimental results
        //experimentalResults.writeResults();
        experimentalResults.reset();
        experimentalResults.save();
    }

    /**
     * Add all of the parameters to the experimental results -- these will be
     * written out to the data file.
     */
    public static void addParametersToResults(ExperimentalResults experimentalResults) {

        // due to performance concerns and for ease of implementation, I am no longer storing all of the script files
        //experimentalResults.addResult("scriptfile", Configuration.scriptfile);

        experimentalResults.addResult("datagenerator", "dbmonster");
        // truncate the name of case study so that it is easier to display in the graphs
        experimentalResults.addResult("database", MutationUtilities.removePrefixFromCaseStudyName(Configuration.database));
        experimentalResults.addResult("type", Configuration.type);
        experimentalResults.addResult("satisfyrows", ((new StringBuffer()).append(Configuration.satisfyrows)).toString());
        experimentalResults.addResult("negaterows", ((new StringBuffer()).append(Configuration.negaterows)).toString());
        experimentalResults.addResult("maxevaluations", ((new StringBuffer()).append(Configuration.maxevaluations)).toString());
        experimentalResults.addResult("naiverandomrowspertable",
                ((new StringBuffer()).append(Configuration.naiverandom_rowspertable)).toString());
        experimentalResults.addResult("naiverandommaxtriespertable",
                ((new StringBuffer()).append(Configuration.naiverandom_maxtriespertable)).toString());
        experimentalResults.addResult("trial", Integer.toString(Configuration.trial));
    }

    /**
     * Save the mutation score with a special format, currently the method
     * chosen for storing the data.
     */
    public static void saveMutationScoresSpecial(ExperimentalResults experimentalResults, MutationReport mutationReport) {

        // get the STANDARD mutation score 
        MutationReportScore mutationScore = mutationReport.getScores().get("mutationScore");
        int mutationScoreNumerator = mutationScore.getNumerator();
        int mutationScoreDenominator = mutationScore.getDenominator();
        double mutationScoreCalculated = mutationScore.getScore();

        // get the WEIGHTED mutation score
        MutationReportScore weightedMutationScore = mutationReport.getScores().get("weightedMutationScore");
        int weightedMutationScoreNumerator = weightedMutationScore.getNumerator();
        int weightedMutationScoreDenominator = weightedMutationScore.getDenominator();
        double weightedMutationScoreCalculated = weightedMutationScore.getScore();

        // create the cloner that we will use to make the deep-copy duplicates of current experimentalResults
        Cloner cloner = new Cloner();

        // create a deep-copy clone of the experimentalResults for storing this redundant, but with new, line of data
        ExperimentalResults scoreExperimentalResults = cloner.deepClone(experimentalResults);
        scoreExperimentalResults.setName("DBMonsterMutationScore");

        scoreExperimentalResults.addResult("meaning", "score");
        scoreExperimentalResults.addResult("value", Double.toString(mutationScoreCalculated));

        // write the results to the file system
        //experimentalResults.goWriteHeader();	
        scoreExperimentalResults.writeResults();
        scoreExperimentalResults.save();

        // create a deep-copy clone of the experimentalResults for storing this redundant, but with new, line of data
        ExperimentalResults interactionsExperimentalResults = cloner.deepClone(experimentalResults);
        interactionsExperimentalResults.setName("DBMonsterMutationScore");

        interactionsExperimentalResults.addResult("meaning", "interaction");
        interactionsExperimentalResults.addResult("value", Integer.toString(weightedMutationScoreDenominator / mutationScoreDenominator));

        // write the results to the file system
        experimentalResults.goWriteHeader();
        interactionsExperimentalResults.writeResults();
        interactionsExperimentalResults.save();
    }

    /**
     * Save the mutant type information into the report in a redundant fashion.
     */
    public static void saveMutationTypeInformationRedundant(ExperimentalResults experimentalResults,
            MutationTypeStatusSummary mutationTypeStatusSummary) {
        // extract the listing of all of the mutant types that we must add to the data file
        List<String> mutationTypes = mutationTypeStatusSummary.getMutantTypes();

        // create a listing of the various mutant status codes that we need
        ArrayList<String> mutationStatusCodes = new ArrayList<String>();
        mutationStatusCodes.add(KILLED);
        mutationStatusCodes.add(NOTKILLED);
        mutationStatusCodes.add(STILLBORN);

        // create the cloner that we will use to make the deep-copy duplicates of current experimentalResults
        Cloner cloner = new Cloner();

        // set the flag to control whether or not the header has been written
        boolean wroteHeader = false;

        // iterate through each of the mutationTypes, adding the three relevant fields for each (each run of this
        // for loop adds three variables to the data file (kill, not killed, still born)
        for (String mutationType : mutationTypes) {
            for (String mutationStatusCode : mutationStatusCodes) {
                // create a deep-copy clone of the experimentalResults for storing this redundant, but with new, line of data
                ExperimentalResults currentExperimentalResults = cloner.deepClone(experimentalResults);
                currentExperimentalResults.setName("DBMonsterMutationCount");

                // add in the counts in a way that is redundant but more amenable to analysis in R
                currentExperimentalResults.addResult("mutationtype",
                        (MutationUtilities.squashMutantDescription(mutationType)).toLowerCase());
                currentExperimentalResults.addResult("mutationstatus",
                        mutationStatusCode);
                currentExperimentalResults.addResult("mutationcount",
                        Integer.toString(mutationTypeStatusSummary.
                        getStatusCount(mutationType, mutationStatusCode)));

                // we have not yet written the header for the mutation counts, so go ahead and do this the 
                // first time and then indicate that no future runs of SchemaAnalyst need to do this again
                if (!experimentalResults.wroteCountHeader()) {
                    System.out.println("Going to write the count header!");
                    currentExperimentalResults.writeHeader();
                    experimentalResults.didWriteCountHeader();
                }

                // write the results to the file system
                currentExperimentalResults.writeResults();
                currentExperimentalResults.save();
                experimentalResults.save();

                // the header has not yet been written, write it this time and then set the flag for later times
                // if(!wroteHeader) {
                //     wroteHeader=true;
                //     experimentalResults.goWriteHeader();
                // }
            }
        }
    }

    /**
     * Save the information about the execution times of test data generation
     * and mutation analysis.
     */
    public static void saveExecutionTimeRedundant(ExperimentalResults experimentalResults,
            long elapsedTimeForMutationAnalysis) {

        // we have this timing store somewhere else because of the way that DBMonster works
        // so just redundantly store the cost for mutation analysis (they are in separate graphs)
        long elapsedTimeForDataGeneration = elapsedTimeForMutationAnalysis;

        // create the cloner that we will use to make the deep-copy duplicates of current experimentalResults
        Cloner cloner = new Cloner();

        // create a deep-copy clone of the experimentalResults for storing this redundant, but with new, line of data
        ExperimentalResults currentExperimentalResults = cloner.deepClone(experimentalResults);
        currentExperimentalResults.setName("DBMonsterExecutionTime");

        // add the elapsed time in second to the data file
        //currentExperimentalResults.addResult("type", "datageneration");
        currentExperimentalResults.addResult("datagenerationtime",
                Double.toString((double) elapsedTimeForDataGeneration / NANODENOMINATOR));

        // add the elapsed time in second to the data file
        //currentExperimentalResults.addResult("type", "mutationanalysis");
        currentExperimentalResults.addResult("mutationanalysistime",
                Double.toString((double) elapsedTimeForMutationAnalysis / NANODENOMINATOR));

        // we have not yet written the header for the execution time, so go ahead and do this the 
        // first time and then indicate that no future runs of SchemaAnalyst need to do this again
        if (!experimentalResults.wroteExecutionTimeHeader()) {
            currentExperimentalResults.writeHeader();
            experimentalResults.didWriteExecutionTimeHeader();
        }

        // write the results to the file system
        currentExperimentalResults.writeResults();
        currentExperimentalResults.save();
        experimentalResults.save();
    }

    /**
     * Output the specified comment depending on the configuration.
     */
    public static void dualCommentOutput(String comment, ScriptCreator scriptCreator) {
        if (Configuration.debug) {
            System.out.println(comment);
        }

        if (Configuration.script) {
            scriptCreator.print(comment);
        }
    }

    /**
     * Create the Database instanced based on the name provided.
     */
    public static DBMS constructDatabase(String databaseType) {
        try {
            return (DBMS) Class.forName(databaseType).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Could not construct database type \"" + databaseType + "\"");
        }
    }

    /**
     * Create the Schema instance based on the name provided.
     */
    public static Schema constructSchema(String schemaName) {
        try {
            return (Schema) Class.forName(schemaName).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Could not construct schema \"" + schemaName + "\"");
        }
    }

    /**
     * Write out the DBMonster insert listing to be used for calculating
     * constraint coverage
     */
    public static void saveDBMonsterInserts(List<SQLInsertRecord> inserts) {
        PrintWriter dataOutput = null;
        try {
            // create the scripts directory for storing the automatically
            // generated scripts for satisfying and violating the schema
            File dataDirectory = new File(Configuration.project
                    + "DBMonsterData/");

            // if the DBMonsterData/ directory does not exist, then create it
            if (!dataDirectory.exists()) {
                dataDirectory.mkdir();
            }

            // create a PrintWriter associated with the text file
            dataOutput = new PrintWriter(Configuration.project
                    + "DBMonsterData/"
                    + Configuration.scriptfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // go through each of the inserts and write them out to the file system
        for (SQLInsertRecord currentInsert : inserts) {
            String statement = currentInsert.getStatement();
            print(statement, dataOutput);
        }
    }

    /**
     *
     */
    public static void print(String line, PrintWriter dataOutput) {
        dataOutput.println(line);
        dataOutput.flush();
    }

    public static void close(PrintWriter dataOutput) {
        dataOutput.close();
    }
}
