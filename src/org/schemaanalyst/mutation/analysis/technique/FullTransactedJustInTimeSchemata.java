/*
 */
package org.schemaanalyst.mutation.analysis.technique;

import org.schemaanalyst.configuration.ExperimentConfiguration;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.result.SQLExecutionReport;
import org.schemaanalyst.mutation.analysis.result.SQLInsertRecord;
import org.schemaanalyst.mutation.analysis.util.ExperimentTimer;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.pipeline.MutationPipelineFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.csv.CSVDatabaseWriter;
import org.schemaanalyst.util.csv.CSVFileWriter;
import org.schemaanalyst.util.csv.CSVResult;
import org.schemaanalyst.util.runner.Description;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;
import org.schemaanalyst.util.xml.XMLSerialiser;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * {@link Runner} for the 'Just-in-Time Schemata' style of mutation analysis.
 * This requires that the result generation tool has been run, as it bases the
 * mutation analysis on the results produced by it.
 * </p>
 *
 * @author Chris J. Wright
 */
@Description("Runs the 'Just-in-Time Schemata' style of mutation analysis. This "
        + "requires that the result generation tool has been run, as it bases "
        + "the mutation analysis on the results produced by it.")
@RequiredParameters("casestudy trial")
public class FullTransactedJustInTimeSchemata extends Runner {

    private final static Logger LOGGER = Logger.getLogger(FullTransactedJustInTimeSchemata.class.getName());
    /**
     * The name of the schema to use.
     */
    @Parameter("The name of the schema to use.")
    protected String casestudy;
    /**
     * The number of the trial.
     */
    @Parameter("The number of the trial.")
    protected int trial;
    /**
     * The folder to retrieve the generated results.
     */
    @Parameter("The folder to retrieve the generated results.")
    protected String inputfolder;
    /**
     * The folder to write the results.
     */
    @Parameter("The folder to write the results.")
    protected String outputfolder;
    /**
     * Whether to submit drop statements prior to running.
     */
    @Parameter(value = "Whether to submit drop statements prior to running.", valueAsSwitch = "true")
    protected boolean dropfirst = false;
    /**
     * The mutation pipeline to use to generate mutants.
     */
    @Parameter(value = "The mutation pipeline to use to generate mutants.",
            choicesMethod = "org.schemaanalyst.mutation.pipeline.MutationPipelineFactory.getPipelineChoices")
    protected String mutationPipeline = "ICST2013";
    /**
     * How many threads to use for parallel execution.
     */
    @Parameter("How many threads to use for parallel execution.")
    protected int threads = 8;
    /**
     * Whether to write the results to a CSV file.
     */
    @Parameter(value = "Whether to write the results to a CSV file.")
    protected boolean resultsToFile = true;
    /**
     * Whether to write the results to a database.
     */
    @Parameter(value = "Whether to write the results to a database.")
    protected boolean resultsToDatabase = false;
    /**
     * Whether to write results to one CSV file.
     */
    @Parameter(value = "Whether to write results to one CSV file.", valueAsSwitch = "true")
    protected boolean resultsToOneFile = false;

    @Override
    public void task() {
        // Setup
        if (inputfolder == null) {
            inputfolder = locationsConfiguration.getResultsDir() + File.separator + "generatedresults" + File.separator;
        }
        if (outputfolder == null) {
            outputfolder = locationsConfiguration.getResultsDir() + File.separator;
        }

        // Start results file
        CSVResult result = new CSVResult();
        result.addValue("technique", this.getClass().getSimpleName());
        result.addValue("dbms", databaseConfiguration.getDbms());
        result.addValue("casestudy", casestudy);
        result.addValue("trial", trial);

        // Instantiate the DBMS and related objects
        DBMS dbms = DBMSFactory.instantiate(databaseConfiguration.getDbms());
        SQLWriter sqlWriter = dbms.getSQLWriter();
        DatabaseInteractor databaseInteractor = dbms.getDatabaseInteractor(casestudy, databaseConfiguration, locationsConfiguration);

        if (databaseInteractor.getTableCount() != 0) {
            LOGGER.log(Level.SEVERE, "Potential dirty database detected: technique={0}, casestudy={1}, trial={2}", new Object[]{this.getClass().getSimpleName(), casestudy, trial});
        }

        // Get the required schema class
        Schema schema;
        try {
            schema = (Schema) Class.forName(casestudy).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }

        // Load the SQLExecutionReport for the non-mutated schema
        String reportPath = inputfolder + casestudy + ".xml";
        SQLExecutionReport originalReport = XMLSerialiser.load(reportPath);

        // Start mutation timing
        ExperimentTimer timer = new ExperimentTimer();
        timer.start(ExperimentTimer.TimingPoint.TOTAL_TIME);

        // Get the mutation pipeline and generate mutants
        timer.start(ExperimentTimer.TimingPoint.MUTATION_TIME);
        MutationPipeline<Schema> pipeline;
        try {
            pipeline = MutationPipelineFactory.<Schema>instantiate(mutationPipeline, schema, databaseConfiguration.getDbms());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        List<Mutant<Schema>> mutants = pipeline.mutate();
        timer.stop(ExperimentTimer.TimingPoint.MUTATION_TIME);

        // Schemata step: Rename the mutants
        renameMutants(mutants);

        // Begin mutation analysis
        timer.start(ExperimentTimer.TimingPoint.PARALLEL_TIME);
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        int killed = 0;
        Set<Future<Boolean>> callResults = new HashSet<>();
        for (int id = 0; id < mutants.size(); id++) {
            MutationAnalysisCallable callable = new MutationAnalysisCallable(id, mutants.get(id).getMutatedArtefact(), sqlWriter, databaseInteractor, originalReport, dropfirst);
            Future<Boolean> callResult = executor.submit(callable);
            callResults.add(callResult);
        }
        for (Future<Boolean> future : callResults) {
            try {
                if (future.get()) {
                    killed++;
                }
            } catch (ExecutionException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        executor.shutdown();
        timer.stop(ExperimentTimer.TimingPoint.PARALLEL_TIME);

        timer.stopAll();
        timer.finalise();

        result.addValue("scorenumerator", killed);
        result.addValue("scoredenominator", mutants.size());
        result.addValue("mutationpipeline", mutationPipeline.replaceAll(",", "|"));
        result.addValue("threads", threads);
        result.addValue("totaltime", timer.getTime(ExperimentTimer.TimingPoint.TOTAL_TIME));
        result.addValue("dropstime", timer.getTime(ExperimentTimer.TimingPoint.DROPS_TIME));
        result.addValue("createstime", timer.getTime(ExperimentTimer.TimingPoint.CREATES_TIME));
        result.addValue("insertstime", timer.getTime(ExperimentTimer.TimingPoint.INSERTS_TIME));
        result.addValue("mutationtime", timer.getTime(ExperimentTimer.TimingPoint.MUTATION_TIME));
        result.addValue("paralleltime", timer.getTime(ExperimentTimer.TimingPoint.PARALLEL_TIME));

        if (resultsToFile) {
            if (resultsToOneFile) {
                new CSVFileWriter(outputfolder + "mutationanalysis.dat").write(result);
            } else {
                new CSVFileWriter(outputfolder + casestudy + ".dat").write(result);
            }
        }
        if (resultsToDatabase) {
            new CSVDatabaseWriter(databaseConfiguration, new ExperimentConfiguration()).write(result);
        }
    }

    /**
     * Prepends each mutant with the relevant mutation number
     *
     * @param mutants
     */
    private static void renameMutants(List<Mutant<Schema>> mutants) {
        for (int i = 0; i < mutants.size(); i++) {
            Schema mutantSchema = mutants.get(i).getMutatedArtefact();
            for (Table table : mutantSchema.getTablesInOrder()) {
                table.setName("mutant_" + (i + 1) + "_" + table.getName());
            }
            for (Constraint constraint : mutantSchema.getConstraints()) {
                if (constraint.hasIdentifier() && constraint.getIdentifier().get() != null) {
                    String name = constraint.getIdentifier().get();
                    constraint.setName("mutant_" + (i + 1) + "_" + name);
                }
            }
        }
    }

    @Override
    protected void validateParameters() {
        //TODO: Validate parameters
    }

    public static void main(String[] args) {
        new FullTransactedJustInTimeSchemata().run(args);
    }

    private class MutationAnalysisCallable implements Callable<Boolean> {

        int id;
        Schema schema;
        SQLWriter sqlWriter;
        DatabaseInteractor databaseInteractor;
        SQLExecutionReport originalReport;
        boolean dropFirst;

        public MutationAnalysisCallable(int id, Schema schema, SQLWriter sqlWriter, DatabaseInteractor databaseInteractor, SQLExecutionReport originalReport, boolean dropFirst) {
            this.id = id;
            this.schema = schema;
            this.sqlWriter = sqlWriter;
            this.databaseInteractor = databaseInteractor;
            this.originalReport = originalReport;
            this.dropFirst = dropFirst;
        }

        @Override
        public Boolean call() throws Exception {
            boolean killed = false;

            LOGGER.log(Level.INFO, "Mutant {0}", id);

            // Schemata step: Generate insert prefix string
            String schemataPrefix = "INSERT INTO mutant_" + (id + 1) + "_";

            // Drop existing tables
            List<String> dropStmts = sqlWriter.writeDropTableStatements(schema, true);
            if (dropfirst) {
                databaseInteractor.executeDropsAsTransaction(dropStmts, 500);
            }

            // Create the schema in the database
            List<String> createStmts = sqlWriter.writeCreateTableStatements(schema);
            databaseInteractor.executeCreatesAsTransaction(createStmts, 500);

            // Insert the test data
            List<SQLInsertRecord> insertStmts = originalReport.getInsertStatements();
            ArrayList<String> groupedStatements = new ArrayList<>();
            for (SQLInsertRecord insertRecord : insertStmts) {

                // Schemata step: Rewrite insert for mutant ID
                String insertStmt = insertRecord.getStatement().replaceAll("INSERT INTO ", schemataPrefix);

                if (insertRecord.isSatisfying()) {
                    groupedStatements.add(insertStmt);
                } else {
                    if (!groupedStatements.isEmpty()) {
                        int returnCount = databaseInteractor.executeUpdatesAsTransaction(groupedStatements);
                        groupedStatements.clear();
                        if (returnCount == 0) {
                            killed = true;
                            break;
                        }
                    }
                    int returnCount = databaseInteractor.executeUpdate(insertStmt);
                    if (returnCount != insertRecord.getReturnCode()) {
                        killed = true;
                        break; // Stop once killed
                    }
                }
            }
            if (!groupedStatements.isEmpty()) {
                int returnCount = databaseInteractor.executeUpdatesAsTransaction(groupedStatements);
                groupedStatements.clear();
                if (returnCount == 0) {
                    killed = true;
                }
            }

            // Drop tables
            databaseInteractor.executeDropsAsTransaction(dropStmts, 500);

            return killed;

        }
    }
}
