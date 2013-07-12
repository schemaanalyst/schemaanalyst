/*
 */
package experiment.mutation2013;

import experiment.ExperimentConfiguration;
import experiment.ExperimentalResults;
import experiment.util.XMLSerialiser;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.schemaanalyst.configuration.FolderConfiguration;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.deprecated.Configuration;
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
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlwriter.SQLWriter;

import plume.Options;

/**
 *
 * @author chris
 */
public class MutationAnalysisParallel {

    private static int STILL_BORN = -1;
    private static SQLWriter sqlWriter;
    private static SQLExecutionReport originalReport;
    private static DatabaseInteractor databaseInteraction;
    private static MutationReport mutationReport;

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
        experimentalResults.addResult("threads", Integer.toString(Configuration.threadpoolsize));

        // create the database using reflection; this is based on the
        // type of the database provided in the configuration (i.e.,
        // the user could request the Postres database in FQN)
        DBMS database = null;
        try {
            database = (DBMS) Class.forName(Configuration.type).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Could not construct database type \"" + Configuration.type + "\"");
        }
        sqlWriter = database.getSQLWriter();

        // load the SQL execution report for the ORIGINAL create tables
        String reportPath = FolderConfiguration.results_dir + File.separator + "data-generation" + File.separator + Configuration.database + ".xml";
        originalReport = XMLSerialiser.load(reportPath);

        // initialize the connection to the real relational database
        databaseInteraction = database.getDatabaseInteractor();

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

        mutationReport = new MutationReport();
        mutationReport.setOriginalReport(originalReport);

        // create all of the MUTANT SCHEMAS that we store inside of the database 
        ConstraintMutatorWithoutFK cm = new ConstraintMutatorWithoutFK();
        List<Schema> mutants = cm.produceMutants(schema);

        // parallel step- rename mutants
        renameMutants(mutants);

        ExecutorService executor = Executors.newFixedThreadPool(Configuration.threadpoolsize);
        int i = 1;
        for (Schema mutant : mutants) {
            MutationAnalysisRunnable runnable = new MutationAnalysisRunnable(mutant, i);
            executor.execute(runnable);
            i++;
        }
        try {
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.HOURS);
        } catch (InterruptedException ex) {
            Logger.getLogger(MutationAnalysisSchemataParallel.class.getName()).log(Level.SEVERE, null, ex);
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

    /**
     * Prepends each mutant with the relevant mutation number
     *
     * @param mutants
     */
    private static void renameMutants(List<Schema> mutants) {
        for (int i = 0; i < mutants.size(); i++) {
            for (Table table : mutants.get(i).getTables()) {
                table.setName("mutant_" + (i + 1) + "_" + table.getName());
            }
        }
    }

    private static class MutationAnalysisRunnable implements Runnable {

        Schema mutant;
        int mutantNumber;

        public MutationAnalysisRunnable(Schema mutant, int mutantNumber) {
            this.mutant = mutant;
            this.mutantNumber = mutantNumber;
        }

        @Override
        public void run() {
            System.out.println("Mutant " + mutantNumber);

            boolean stillBorn = false;

            // drop tables inside the previous schema
            List<String> dropTableStatementsMutants = sqlWriter.writeDropTableStatements(mutant, true);

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

            // rebuild schemata prefix
            String schemataInsert = "INSERT INTO mutant_" + mutantNumber + "_";

            // run all of the ORIGINAL INSERTS on the mutant schema
            SQLExecutionReport retrievedOriginalReport = originalReport;
            List<SQLInsertRecord> originalInsertStatements = retrievedOriginalReport.getInsertStatements();
            for (SQLInsertRecord originalInsertRecord : originalInsertStatements) {
                // create a MutantRecord
                MutantRecord insertMutantRecord = new MutantRecord();

                // extract the statement from the SQLInsertRecord
                String statement = originalInsertRecord.getStatement();

                // alter for mutant schemata
                statement = statement.replaceAll("INSERT INTO ", schemataInsert);

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

            // store the mutant still born information for this MutantReport
            if (stillBorn) {
                currentMutantReport.bornStill();
            }

            // System.out.println("Computing intersection!");
            currentMutantReport.computeIntersection();

            // drop tables inside the previous schema
            for (String statement : dropTableStatementsMutants) {
                databaseInteraction.executeUpdate(statement);
            }

            // store the current insertMutantRecord inside of the mutationReport
            synchronized (mutationReport) {
                mutationReport.addMutantReport(currentMutantReport);
            }
        }
    }
}
