/*
 */
package experiment.mutation2013;

import experiment.ExperimentConfiguration;
import experiment.ExperimentalResults;
import experiment.util.XMLSerialiser;

import java.io.File;
import java.util.List;

import org.schemaanalyst.configuration.Configuration;
import org.schemaanalyst.configuration.FolderConfiguration;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.MutantRecord;
import org.schemaanalyst.mutation.MutantReport;
import org.schemaanalyst.mutation.MutationReport;
import org.schemaanalyst.mutation.MutationReportScore;
import org.schemaanalyst.mutation.MutationUtilities;
import org.schemaanalyst.mutation.SQLExecutionRecord;
import org.schemaanalyst.mutation.SQLExecutionReport;
import org.schemaanalyst.mutation.SQLInsertRecord;
import org.schemaanalyst.mutation.mutators.ConstraintMutator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;

import plume.Options;

/**
 *
 * @author chris
 */
public class MutationAnalysis {
    private static int STILL_BORN = -1;

    public static void main(String[] args) {        
        // parse options
        Options options = new Options("MutationAnalysis [options]", new Configuration());
        options.parse_or_usage(args);
        ExperimentConfiguration.project = Configuration.project;
        
        // Start results file
        ExperimentalResults experimentalResults = ExperimentalResults.retrieve();
        experimentalResults.reset();
        experimentalResults.addResult("datagenerator", Configuration.datagenerator);
	experimentalResults.addResult("database", MutationUtilities.removePrefixFromCaseStudyName(Configuration.database));
	experimentalResults.addResult("type", MutationUtilities.removePrefixFromCaseStudyName(Configuration.type));
	experimentalResults.addResult("trial", Integer.toString(Configuration.trial));

        // create the database using reflection; this is based on the
        // type of the database provided in the configuration (i.e.,
        // the user could request the Postres database in FQN)
        DBMS database = null;
        try {
            database = (DBMS) Class.forName(Configuration.type).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Could not construct database type \"" + Configuration.type + "\"");
        }
        SQLWriter sqlWriter = database.getSQLWriter();

        // load the SQL execution report for the ORIGINAL create tables
        String reportPath = FolderConfiguration.results_dir + File.separator + "data-generation" + File.separator + Configuration.database + ".xml";
        SQLExecutionReport originalReport = XMLSerialiser.load(reportPath);

        // initialize the connection to the real relational database
        DatabaseInteractor databaseInteraction = database.getDatabaseInteractor();

        // create the schema using reflection; this is based on the
        // name of the database provided in the configuration
        Schema schema = null;
        try {
            schema = (Schema) Class.forName(Configuration.database).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Could not construct schema \"" + Configuration.database + "\"");
        }

        // start mutation timing
        long startTime = System.currentTimeMillis();
        
        boolean stillBorn = false;
        
        MutationReport mutationReport = new MutationReport();
        mutationReport.setOriginalReport(originalReport);
        
        // create all of the MUTANT SCHEMAS that we store inside of the database 
        ConstraintMutatorWithoutFK cm = new ConstraintMutatorWithoutFK();
        List<Schema> mutants = cm.produceMutants(schema);
        int i = 1;
        for (Schema mutant : mutants) {
            
            System.out.println("Mutant " + i);

            // drop tables inside the previous schema
            List<String> dropTableStatementsMutants = sqlWriter.writeDropTableStatements(mutant, true);
//            for (String statement : dropTableStatementsMutants) {
//                databaseInteraction.executeUpdate(statement);
//            }

            // create the MutantReport that will store the details about these inserts
            MutantReport currentMutantReport = new MutantReport();

            // create the MUTANT schema inside of the real database
            List<String> createTableStatementsMutants = sqlWriter.writeCreateTableStatements(mutant);
            for (String statement : createTableStatementsMutants) {
                int returnCount = databaseInteraction.executeUpdate(statement);

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

            // run all of the ORIGINAL INSERTS on the mutant schema
            SQLExecutionReport retrievedOriginalReport = originalReport;
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
                if (insertMutantRecord.getReturnCode() == originalInsertRecord.getReturnCode()) {
                    insertMutantRecord.sparedMutant();
                } else {
                    insertMutantRecord.killedMutant();
                }
                
                // add this insertMutantRecord to the mutant report
                currentMutantReport.addMutantStatement(insertMutantRecord);
            }
            
            // indicate we have moved on to the next MUTANT
            i++;

            // store the mutant still born information for this MutantReport
            if (stillBorn) {
                currentMutantReport.bornStill();
                stillBorn = false;
            }

            // don't know if it is killed yet here
            // System.out.println("Computing intersection!");
            currentMutantReport.computeIntersection();

            // store the current insertMutantRecord inside of the mutationReport
            mutationReport.addMutantReport(currentMutantReport);
            
            // drop tables inside the previous schema
            for (String statement : dropTableStatementsMutants) {
                databaseInteraction.executeUpdate(statement);
            }
        }

        // calculate the mutation score and the main summary statistics
        mutationReport.calculateMutationScoresAndStatistics();
        MutationReportScore score = mutationReport.getScores().get("mutationScore");

        // END TIME FOR mutation analysis
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        
        experimentalResults.addResult("mutationtime", new Long(totalTime).toString());
        experimentalResults.addResult("mutationscore_numerator", Integer.toString(score.getNumerator()));
        experimentalResults.addResult("mutationscore_denominator", Integer.toString(score.getDenominator()));
        
        if (!experimentalResults.wroteHeader()) {
            experimentalResults.writeHeader();
            experimentalResults.didWriteCountHeader();
        }
        
        experimentalResults.writeResults();
        experimentalResults.save();
    }
}
