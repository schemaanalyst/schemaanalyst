package org.schemaanalyst.deprecated;

import java.util.List;
import java.util.ArrayList;

import plume.*;

import com.rits.cloning.Cloner;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.datageneration.ConstraintGoalReport;
import org.schemaanalyst.datageneration.CoverageReport;
import org.schemaanalyst.datageneration.DataGenerator;
import org.schemaanalyst.datageneration.GoalReport;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiserFactory;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.deprecated.configuration.Configuration;
import org.schemaanalyst.script.ScriptCreator;
import org.schemaanalyst.script.MutantScriptCreator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.random.Random;
import org.schemaanalyst.util.random.SimpleRandom;
import org.schemaanalyst.mutation.MutationReport;
import org.schemaanalyst.mutation.MutationReportScore;
import org.schemaanalyst.mutation.MutantReport;
import org.schemaanalyst.mutation.MutantRecord;
import org.schemaanalyst.mutation.SQLExecutionReport;
import org.schemaanalyst.mutation.SQLExecutionRecord;
import org.schemaanalyst.mutation.SQLInsertRecord;
import org.schemaanalyst.mutation.mutators.ConstraintMutator;
import org.schemaanalyst.mutation.MutationTypeStatusSummary;
import org.schemaanalyst.mutation.MutationUtilities;

import experiment.ExperimentalResults;
import experiment.ExperimentConfiguration;

public class SchemaAnalyst {

    /**
     * Return code for a still born mutant
     */
    private static final int STILL_BORN = -1;
    /**
     * The starting symbol for mutant description abbreviation
     */
    private static final String START = "(";
    /**
     * The ending symbol for mutant description abbreviation
     */
    private static final String END = ",";
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
     * The ending symbol for mutant description abbreviation
     */
    private static final String NOTSTILLBORN = "notstillborn";
    /**
     * The ending symbol for mutant description abbreviation
     */
    private static final String INTERSECTED = "intersection";
    /**
     * The denominator of the fraction for converting to seconds
     */
    private static final double NANODENOMINATOR = 1000000000.0;

    /**
     * The main method for performing database-aware test data generation using
     * search-based methods.
     */
    public static void main(String[] args) {

        // BEGIN TIME for test data generation
        long beginTestDataGenerationTime = System.nanoTime();

        // extract all of the database configurations
        Configuration configuration = new Configuration();
        Options options = new Options("SchemaAnalyst [options]", configuration);
        options.parse_or_usage(args);

        // print the debugging information
        if (Configuration.debug) {
            System.out.println(options.settings());
        }

        // print the help screen to see the commands
        if (Configuration.help) {
            options.print_usage();
        }

        // create the mutation report that will store results and calculate the score
        MutationReport mutationReport = new MutationReport();

        // create the SQL execution report for the ORIGINAL create tables
        SQLExecutionReport originalReport = new SQLExecutionReport();

        // create the database using reflection; this is based on the
        // type of the database provided in the configuration (i.e.,
        // the user could request the Postres database in FQN)
        DBMS database = constructDatabase(Configuration.type);
        SQLWriter sqlWriter = database.getSQLWriter();

        // initialize the connection to the real relational database
        //TODO: Fix SchemaAnalyst to use Runner, so this will not fail
        if (true) // Hack to avoid the simple 'unreachable' checks in Javac
            throw new RuntimeException("SchemaAnalyst class cannot construct DatabaseInteractor since refactor");
        DatabaseInteractor databaseInteraction = database.getDatabaseInteractor(null, null, null);

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

        // PERFORM TEST DATA GENERATION WITH THE ORIGINAL SCHEMA

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

        // generate test data
        ValueFactory valueFactory = database.getValueFactory();

        DataGenerator dataGenerator =
                constructDataGenerator(Configuration.datagenerator, schema, valueFactory);

        CoverageReport report = dataGenerator.generate();

        // System.out.println("********** COVERAGE!");
        // System.out.println(report.getCoveragePercentage().doubleValue());

        List<GoalReport> goalReports = report.getSuccessfulGoalReports();

        // run the insert statements that were automatically generated;
        // the return value is the number of records that were modified
        // for each of the individual inserts that are executed
        for (GoalReport goalReport : goalReports) {
            Data data = goalReport.getData();

            // get the listing of statements for this data item 
            List<String> statements = sqlWriter.writeInsertStatements(data);

            // extract the boolean about whether this is satisfying or negating
            boolean isSatisfyingData = true;

            if (goalReport instanceof ConstraintGoalReport) {
                isSatisfyingData = ((ConstraintGoalReport) goalReport).getConstraint() == null;
            }

            // write out a comment showing that this batch of statements is starting
            dualCommentOutput("-- START executing batch of insert statements", scriptCreator);

            // write out the total number of statements executed in this batch
            dualCommentOutput("-- Total number of statements to execute: " + statements.size(),
                    scriptCreator);

            // write out all of the comments in the script for this data item
            dualCommentOutput("-- " + goalReport.getDescription(), scriptCreator);

            // write out all of the statements and then execute them for this data item 
            for (String statement : statements) {

                // write out the actual statement that we are executing
                dualCommentOutput(statement + ";", scriptCreator);

                // extract the number of modified record counts, used in mutation analysis
                Integer returnCounts = databaseInteraction.executeUpdate(statement);

                // create a new SQL Execution Record for this INSERT statement
                SQLInsertRecord currentInsert = new SQLInsertRecord();
                currentInsert.setStatement(statement);
                currentInsert.setReturnCode(returnCounts);

                // set whether this is trying to satisfy or negate the schema
                if (isSatisfyingData) {
                    currentInsert.tryToSatisfy();
                } else {
                    currentInsert.tryToNegate();
                }

                // add the INSERT SQLExecutionRecord to the ORIGINAL report
                originalReport.addInsertStatement(currentInsert);

                // write out a comment showing the number of modified records
                dualCommentOutput("-- Number of modified records for previous insert: " + returnCounts,
                        scriptCreator);

            }

            // write out a comment showing that this batch of statements is starting
            dualCommentOutput("-- FINISH executing batch of insert statements", scriptCreator);
            if (Configuration.script) {
                scriptCreator.print("");
            }
        }

        // END TIME for test data generation
        long endTestDataGenerationTime = System.nanoTime();

        // PERFORM MUTATION ANALYSIS WITH ALL OF THE MUTANT SCHEMAS

        // NOTE: the mutationReport contains all of the INSERTS that satisfy and
        // negate the schema in the originalReport because the OR was added in first

        // BEGIN TIME FOR mutation analysis
        long beginMutationAnalysisTime = System.nanoTime();

        // indicate that we have not yet encountered a still born mutant
        boolean stillBorn = false;

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

            // *** code needs to be better tested, only useful for debugging, was causing problem with ITrust

            // add the comments for this mutant to the currentMutantReport
            // currentMutantReport.setDescription(MutationUtilities.
            // 				       abbreviateMutantDescription(mutantCommentsBuilder.toString(), START, END));

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
                    //System.out.println("Still born mutant!!");
                    stillBorn = true;
                    //System.out.println("Value of i: " + i);
                    //System.out.println(currentMutantCreateTable);
                }
            }

            // run all of the ORIGINAL INSERTS on the mutant schema
            SQLExecutionReport retrievedOriginalReport = mutationReport.getOriginalReport();
            List<SQLInsertRecord> originalInsertStatements = retrievedOriginalReport.getInsertStatements();
            for (SQLInsertRecord originalInsertRecord : originalInsertStatements) {
                // create a MutantRecord
                MutantRecord insertMutantRecord = new MutantRecord();

                // extract the statement from the SQLInsertRecord
                String statement = originalInsertRecord.getStatement();

                // add the current statement into the insertMutantRecord
                insertMutantRecord.setStatement(statement);

                // extract the number of modified record counts, used in mutation analysis
                Integer returnCounts = databaseInteraction.executeUpdate(statement);

                // add the return code to the insertMutantRecord
                insertMutantRecord.setReturnCode(returnCounts);

                // indicate whether this insertMutantRecord is satisfying or negating
                if (originalInsertRecord.isTryingToSatisfy()) {
                    insertMutantRecord.tryToSatisfy();
                } else if (originalInsertRecord.isTryingToNegate()) {
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

            // store the mutant still born information for this MutantReport
            if (stillBorn) {
                currentMutantReport.setStillBorn(true);
                stillBorn = false;
            }

            // don't know if it is killed yet here
            // System.out.println("Computing intersection!");
            currentMutantReport.computeIntersected();

            // store the current insertMutantRecord inside of the mutationReport
            mutationReport.addMutantReport(currentMutantReport);
        }

        // calculate the mutation score and the main summary statistics
        mutationReport.calculateMutationScoresAndStatistics();

        // END TIME FOR mutation analysis
        long endMutationAnalysisTime = System.nanoTime();

        // STEP ONE: save the data about mutation scores from the mutation report into the experimental results 
        saveMutationScoresSpecial(experimentalResults, mutationReport, report);

        // STEP TWO: save the data about the types and the kill, not kill, still born information into the experimental results
        MutationTypeStatusSummary mutationTypeStatusSummary = mutationReport.createMutationTypeStatusSummary();
        saveMutationTypeInformationRedundant(experimentalResults, mutationTypeStatusSummary);

        // STEP THREE: save the data about the satisfy/negate and kill/not kill scores
        saveSatisfyNegateMutationInformationRedundant(experimentalResults, mutationReport);

        // STEP FOUR: save the data about the execution time of test data generation and mutation analysis
        saveExecutionTimeRedundant(experimentalResults,
                (endTestDataGenerationTime - beginTestDataGenerationTime),
                (endMutationAnalysisTime - beginMutationAnalysisTime));

        // write out the mutation report for storage and later analysis
        //System.out.println(mutationTypeStatusSummary);
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
     * Save the information about the execution times of test data generation
     * and mutation analysis.
     */
    public static void saveExecutionTimeRedundant(ExperimentalResults experimentalResults,
            long elapsedTimeForDataGeneration,
            long elapsedTimeForMutationAnalysis) {
        // create the cloner that we will use to make the deep-copy duplicates of current experimentalResults
        Cloner cloner = new Cloner();

        // create a deep-copy clone of the experimentalResults for storing this redundant, but with new, line of data
        ExperimentalResults currentExperimentalResults = cloner.deepClone(experimentalResults);
        currentExperimentalResults.setName("ExecutionTime");

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
     * Save the information about the purpose of an insert and whether it killed
     * or not.
     */
    public static void saveSatisfyNegateMutationInformationRedundant(ExperimentalResults experimentalResults,
            MutationReport mutationReport) {
        // create a listing of the purpose of the insert statements
        ArrayList<String> insertPurposes = new ArrayList<>();
        insertPurposes.add("satisfy");
        insertPurposes.add("negate");

        // create a listing of the status of the mutants
        ArrayList<String> mutantStatuses = new ArrayList<>();
        mutantStatuses.add("Kill");
        mutantStatuses.add("NotKill");

        // create the cloner that we will use to make the deep-copy duplicates of current experimentalResults
        Cloner cloner = new Cloner();

        // set the flag to control whether or not the header has been written
        boolean wroteHeader = false;

        // iterate through each of the mutationTypes, adding the three relevant fields for each (each run of this
        // for loop adds three variables to the data file (kill, not killed, still born)
        for (String insertPurpose : insertPurposes) {
            for (String mutantStatus : mutantStatuses) {
                // create a deep-copy clone of the experimentalResults for storing this redundant, but with new, line of data
                ExperimentalResults currentExperimentalResults = cloner.deepClone(experimentalResults);
                currentExperimentalResults.setName("InsertMutationCount");

                // add the current parameters to this experimental result
                currentExperimentalResults.addResult("insertpurpose", insertPurpose);
                currentExperimentalResults.addResult("mutantstatus", mutantStatus);

                // make the string variable that will be the key name for this current data point
                String currentScoreKey = insertPurpose + "And" + mutantStatus + "Score";

                // extract the information about this current part of the experimental result
                MutationReportScore currentScore = mutationReport.getScores().get(currentScoreKey);
                int currentScoreNumerator = currentScore.getNumerator();

                // add the value for this current score to the data file
                currentExperimentalResults.addResult("value", Integer.toString(currentScoreNumerator));

                // we have not yet written the header for the insert mutation counts, so go ahead and do this the 
                // first time and then indicate that no future runs of SchemaAnalyst need to do this again
                if (!experimentalResults.wroteInsertCountHeader()) {
                    //System.out.println("Going to write the count header!");		    
                    currentExperimentalResults.writeHeader();
                    experimentalResults.didWriteInsertCountHeader();
                }

                // write the results to the file system
                currentExperimentalResults.writeResults();
                currentExperimentalResults.save();
                experimentalResults.save();

                // save the SATISFY AND KILL score in the data file
                // MutationReportScore satisfyAndKillScore = mutationReport.getScores().get("satisfyAndKillScore");
                // int satisfyAndKillScoreNumerator = satisfyAndKillScore.getNumerator();
                // int satisfyAndKillScoreDenominator = satisfyAndKillScore.getDenominator();
                // double satisfyAndKillScoreCalculated = satisfyAndKillScore.getScore();
                // experimentalResults.addResult("satisfyandkillscorenumerator", Integer.toString(satisfyAndKillScoreNumerator));
                // experimentalResults.addResult("satisfyandkillscoredenominator", Integer.toString(satisfyAndKillScoreDenominator));
                // experimentalResults.addResult("satisfyandkillscore", Double.toString(satisfyAndKillScoreCalculated));

            }
        }
    }

    /**
     * Save the mutant type information into the report in a redundant fashion.
     */
    public static void saveMutationTypeInformationRedundant(ExperimentalResults experimentalResults,
            MutationTypeStatusSummary mutationTypeStatusSummary) {
        // extract the listing of all of the mutant types that we must add to the data file
        List<String> mutationTypes = mutationTypeStatusSummary.getMutantTypes();

        // create a listing of the various mutant status codes that we need
        ArrayList<String> mutationStatusCodes = new ArrayList<>();
        // mutationStatusCodes.add(KILLED);
        // mutationStatusCodes.add(NOTKILLED);
        mutationStatusCodes.add(STILLBORN);
        mutationStatusCodes.add(NOTSTILLBORN);

        //mutationStatusCodes.add(INTERSECTED);

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
                currentExperimentalResults.setName("MutationCount");

                // add in the counts in a way that is redundant but more amenable to analysis in R
                currentExperimentalResults.addResult("mutationtype",
                        (mutationType.replace(" ", "")).toLowerCase());
                currentExperimentalResults.addResult("mutationstatus",
                        mutationStatusCode);


                if (mutationStatusCode.equals(NOTSTILLBORN)) {
                    currentExperimentalResults.addResult("mutationcount",
                            Integer.toString((mutationTypeStatusSummary.getStatusCount(mutationType,
                            KILLED)
                            + mutationTypeStatusSummary.getStatusCount(mutationType,
                            NOTKILLED))
                            - mutationTypeStatusSummary.
                            getStatusCount(mutationType, STILLBORN)));
                } else {
                    currentExperimentalResults.addResult("mutationcount",
                            Integer.toString(mutationTypeStatusSummary.
                            getStatusCount(mutationType, mutationStatusCode)));
                }

                // we have not yet written the header for the mutation counts, so go ahead and do this the 
                // first time and then indicate that no future runs of SchemaAnalyst need to do this again
                if (!experimentalResults.wroteCountHeader()) {
                    //System.out.println("Going to write the count header!");		    
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
     * Save the mutant type information into the report in a non-redundant
     * fashion.
     */
    public static void saveMutationTypeInformation(ExperimentalResults experimentalResults,
            MutationTypeStatusSummary mutationTypeStatusSummary) {
        // extract the listing of all of the mutant types that we must add to the data file
        List<String> mutationTypes = mutationTypeStatusSummary.getMutantTypes();

        // iterate through each of the mutationTypes, adding the three relevant fields for each (each run of this
        // for loop adds three variables to the data file (kill, not killed, still born)
        for (String mutationType : mutationTypes) {
            // add the results for the mutants that were KILLED
            experimentalResults.addResult((mutationType.replace(" ", "")).toLowerCase() + KILLED,
                    Integer.toString(mutationTypeStatusSummary.getKilledCount(mutationType)));
            // add the results for the mutants that were NOT KILLED
            experimentalResults.addResult((mutationType.replace(" ", "")).toLowerCase() + NOTKILLED,
                    Integer.toString(mutationTypeStatusSummary.getAliveCount(mutationType)));
            // add the results for the mutants that were STILL BORN
            experimentalResults.addResult((mutationType.replace(" ", "")).toLowerCase() + STILLBORN,
                    Integer.toString(mutationTypeStatusSummary.getStillBornCount(mutationType)));
        }
    }

    /**
     * Save the mutation score with a special format, currently the method
     * chosen for storing the data.
     */
    public static void saveMutationScoresSpecial(ExperimentalResults experimentalResults, MutationReport mutationReport,
            CoverageReport coverageReport) {

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
        scoreExperimentalResults.setName("MutationScore");

        scoreExperimentalResults.addResult("meaning", "score");
        scoreExperimentalResults.addResult("value", Double.toString(mutationScoreCalculated));

        // write the results to the file system
        experimentalResults.goWriteHeader();
        scoreExperimentalResults.writeResults();
        scoreExperimentalResults.save();

        // create a deep-copy clone of the experimentalResults for storing this redundant, but with new, line of data
        ExperimentalResults interactionsExperimentalResults = cloner.deepClone(experimentalResults);
        interactionsExperimentalResults.setName("MutationScore");

        interactionsExperimentalResults.addResult("meaning", "interaction");
        interactionsExperimentalResults.addResult("value", Integer.toString(weightedMutationScoreDenominator / mutationScoreDenominator));

        // write the results to the file system
        experimentalResults.goWriteHeader();
        interactionsExperimentalResults.writeResults();
        interactionsExperimentalResults.save();

        // create a deep-copy clone of the experimentalResults for storing this redundant, but with new, line of data
        ExperimentalResults coverageExperimentalResults = cloner.deepClone(experimentalResults);
        coverageExperimentalResults.setName("MutationScore");

        coverageExperimentalResults.addResult("meaning", "coverage");
        coverageExperimentalResults.addResult("value", Double.toString(coverageReport.getCoveragePercentage().doubleValue()));

        // write the results to the file system
        experimentalResults.goWriteHeader();
        coverageExperimentalResults.writeResults();
        coverageExperimentalResults.save();
    }

    /**
     * Save the mutation scores into the report in a redundant fashion that
     * eases data analysis and visualization.
     */
    public static void saveMutationScoresRedundant(ExperimentalResults experimentalResults, MutationReport mutationReport) {
        // create the list for the metric variable in the data table
        ArrayList<String> metrics = new ArrayList<>();
        metrics.add("mutationScore");
        metrics.add("weightedMutationScore");

        // create the list for the part variable in the data table
        ArrayList<String> parts = new ArrayList<>();
        parts.add("numerator");
        parts.add("denominator");
        parts.add("score");

        // REMINDER: based on those above levels, we will store a numerical value!

        // create the cloner that we will use to make the deep-copy duplicates of current experimentalResults
        Cloner cloner = new Cloner();

        // set the flag to control whether or not the header has been written
        boolean wroteHeader = false;

        // got through all of the data values and incrementally create a result and store it in the data file
        for (String metric : metrics) {
            for (String part : parts) {
                // create a deep-copy clone of the experimentalResults for storing this redundant, but with new, line of data
                ExperimentalResults currentExperimentalResults = cloner.deepClone(experimentalResults);

                // add the current names of the two key parameters
                currentExperimentalResults.addResult("metric", metric);
                currentExperimentalResults.addResult("part", part);

                // add the data results that are important for this current configuration
                MutationReportScore mutationScore = mutationReport.getScores().get(metric);

                // get the data value that we are currently looking at
                double value = mutationScore.getValue(part);

                // store the current data value into the current experimental results
                currentExperimentalResults.addResult("value", Double.toString(value));

                // write the results to the file system
                currentExperimentalResults.writeResults();
                currentExperimentalResults.save();

                // the header has not yet been written, write it this time and then set the flag for later times
                if (!wroteHeader) {
                    wroteHeader = true;
                    experimentalResults.goWriteHeader();
                }
            }
        }
    }

    /**
     * Save the mutation scores into the report in the standard, non-redundant
     * fashion.
     */
    public static void saveMutationScores(ExperimentalResults experimentalResults, MutationReport mutationReport) {
        // save the STANDARD mutation score in the data file
        MutationReportScore mutationScore = mutationReport.getScores().get("mutationScore");
        int mutationScoreNumerator = mutationScore.getNumerator();
        int mutationScoreDenominator = mutationScore.getDenominator();
        double mutationScoreCalculated = mutationScore.getScore();
        experimentalResults.addResult("mutationscorenumerator", Integer.toString(mutationScoreNumerator));
        experimentalResults.addResult("mutationscoredenominator", Integer.toString(mutationScoreDenominator));
        experimentalResults.addResult("mutationscore", Double.toString(mutationScoreCalculated));

        // save the WEIGHTED mutation score in the data file
        MutationReportScore weightedMutationScore = mutationReport.getScores().get("weightedMutationScore");
        int weightedMutationScoreNumerator = weightedMutationScore.getNumerator();
        int weightedMutationScoreDenominator = weightedMutationScore.getDenominator();
        double weightedMutationScoreCalculated = weightedMutationScore.getScore();
        experimentalResults.addResult("weightedmutationscorenumerator", Integer.toString(weightedMutationScoreNumerator));
        experimentalResults.addResult("weightedmutationscoredenominator", Integer.toString(weightedMutationScoreDenominator));
        experimentalResults.addResult("weightedmutationscore", Double.toString(weightedMutationScoreCalculated));

        // save the SATISFY AND KILL score in the data file
        MutationReportScore satisfyAndKillScore = mutationReport.getScores().get("satisfyAndKillScore");
        int satisfyAndKillScoreNumerator = satisfyAndKillScore.getNumerator();
        int satisfyAndKillScoreDenominator = satisfyAndKillScore.getDenominator();
        double satisfyAndKillScoreCalculated = satisfyAndKillScore.getScore();
        experimentalResults.addResult("satisfyandkillscorenumerator", Integer.toString(satisfyAndKillScoreNumerator));
        experimentalResults.addResult("satisfyandkillscoredenominator", Integer.toString(satisfyAndKillScoreDenominator));
        experimentalResults.addResult("satisfyandkillscore", Double.toString(satisfyAndKillScoreCalculated));

        // save the SATISFY AND NOT KILL score in the data file
        MutationReportScore satisfyAndNotKillScore = mutationReport.getScores().get("satisfyAndNotKillScore");
        int satisfyAndNotKillScoreNumerator = satisfyAndNotKillScore.getNumerator();
        int satisfyAndNotKillScoreDenominator = satisfyAndNotKillScore.getDenominator();
        double satisfyAndNotKillScoreCalculated = satisfyAndNotKillScore.getScore();
        experimentalResults.addResult("satisfyandnotkillscorenumerator", Integer.toString(satisfyAndNotKillScoreNumerator));
        experimentalResults.addResult("satisfyandnotkillscoredenominator", Integer.toString(satisfyAndNotKillScoreDenominator));
        experimentalResults.addResult("satisfyandnotkillscore", Double.toString(satisfyAndNotKillScoreCalculated));

        // save the NEGATE AND KILL score in the data file
        MutationReportScore negateAndKillScore = mutationReport.getScores().get("negateAndKillScore");
        int negateAndKillScoreNumerator = negateAndKillScore.getNumerator();
        int negateAndKillScoreDenominator = negateAndKillScore.getDenominator();
        double negateAndKillScoreCalculated = negateAndKillScore.getScore();
        experimentalResults.addResult("negateandkillscorenumerator", Integer.toString(negateAndKillScoreNumerator));
        experimentalResults.addResult("negateandkillscoredenominator", Integer.toString(negateAndKillScoreDenominator));
        experimentalResults.addResult("negateandkillscore", Double.toString(negateAndKillScoreCalculated));

        // save the NEGATE AND NOT KILL score in the data file
        MutationReportScore negateAndNotKillScore = mutationReport.getScores().get("negateAndNotKillScore");
        int negateAndNotKillScoreNumerator = negateAndNotKillScore.getNumerator();
        int negateAndNotKillScoreDenominator = negateAndNotKillScore.getDenominator();
        double negateAndNotKillScoreCalculated = negateAndNotKillScore.getScore();
        experimentalResults.addResult("negateandnotkillscorenumerator", Integer.toString(negateAndNotKillScoreNumerator));
        experimentalResults.addResult("negateandnotkillscoredenominator", Integer.toString(negateAndNotKillScoreDenominator));
        experimentalResults.addResult("negateandnotkillscore", Double.toString(negateAndNotKillScoreCalculated));
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
     * Add all of the parameters to the experimental results -- these will be
     * written out to the data file.
     */
    public static void addParametersToResults(ExperimentalResults experimentalResults) {

        // due to performance concerns and for ease of implementation, I am no longer storing all of the script files
        //experimentalResults.addResult("scriptfile", Configuration.scriptfile);

        experimentalResults.addResult("datagenerator", Configuration.datagenerator);
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
     * Create the generator based on the name provided
     */
    public static DataGenerator constructDataGenerator(
            String generatorName, Schema schema, ValueFactory valueFactory) {

        Random random = new SimpleRandom(Configuration.randomseed);
        CellRandomiser cellRandomizer = constructCellRandomizationProfile(random);
/*
        if (generatorName.equals("alternatingvalue")) {

            Search<Data> search = new AlternatingValueSearch(
                    random,
                    new RandomDataInitializer(cellRandomizer),
                    new RandomDataInitializer(cellRandomizer));

            TerminationCriterion terminationCriterion = new CombinedTerminationCriterion(
                    new CounterTerminationCriterion(search.getEvaluationsCounter(),
                    Configuration.maxevaluations),
                    new OptimumTerminationCriterion<>(search));

            search.setTerminationCriterion(terminationCriterion);

            return new SearchConstraintCoverer(search,
                    schema,
                    valueFactory,
                    Configuration.satisfyrows,
                    Configuration.negaterows);
        }

        if (generatorName.equals("alternatingvalue_defaults")) {

            Search<Data> search = new AlternatingValueSearch(
                    random,
                    new NoDataInitialization(),
                    new RandomDataInitializer(cellRandomizer));

            TerminationCriterion terminationCriterion = new CombinedTerminationCriterion(
                    new CounterTerminationCriterion(search.getEvaluationsCounter(),
                    Configuration.maxevaluations),
                    new OptimumTerminationCriterion<>(search));

            search.setTerminationCriterion(terminationCriterion);

            return new SearchConstraintCoverer(search,
                    schema,
                    valueFactory,
                    Configuration.satisfyrows,
                    Configuration.negaterows);
        }

        if (generatorName.equals("random")) {

            Search<Data> search = new RandomSearch(cellRandomizer);

            TerminationCriterion terminationCriterion = new CombinedTerminationCriterion(
                    new CounterTerminationCriterion(search.getEvaluationsCounter(),
                    Configuration.maxevaluations),
                    new OptimumTerminationCriterion<>(search));

            search.setTerminationCriterion(terminationCriterion);

            return new SearchConstraintCoverer(
                    search,
                    schema,
                    valueFactory,
                    Configuration.satisfyrows,
                    Configuration.negaterows);
        }

        if (generatorName.equals("naiverandom")) {

            return new NaiveRandomConstraintCoverer(schema,
                    valueFactory,
                    cellRandomizer,
                    Configuration.naiverandom_rowspertable,
                    Configuration.naiverandom_maxtriespertable);
        }

        if (generatorName.equals("domainspecific")) {


            return new DomainSpecificConstraintCoverer(
                    schema,
                    valueFactory,
                    Configuration.satisfyrows,
                    Configuration.negaterows,
                    Configuration.maxevaluations,
                    cellRandomizer, random);
            
        }
        
        */

        throw new RuntimeException("Unknown Data Generator \"" + generatorName + "\"");
    }

    public static CellRandomiser constructCellRandomizationProfile(Random random) {
        String name = Configuration.randomprofile;

        if (name.equals("small")) {
            return CellRandomiserFactory.small(random);
        }

        if (name.equals("large")) {
            return CellRandomiserFactory.large(random);
        }

        throw new RuntimeException("Unknown random profile \"" + name + "\"");
    }
}
