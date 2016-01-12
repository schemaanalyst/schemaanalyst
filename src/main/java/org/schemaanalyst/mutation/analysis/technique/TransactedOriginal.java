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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * {@link Runner} for the 'Original' style of mutation analysis. This requires
 * that the result generation tool has been run, as it bases the mutation
 * analysis on the results produced by it.
 * </p>
 *
 * @author Chris J. Wright
 */
@Description("Runs the 'Original' style of mutation analysis. This requires that"
        + " the result generation tool has been run, as it bases the mutation "
        + "analysis on the results produced by it.")
@RequiredParameters("casestudy trial")
public class TransactedOriginal extends Runner {

    private final static Logger LOGGER = Logger.getLogger(TransactedOriginal.class.getName());
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
    protected String inputfolder; // Default in validate
    /**
     * The folder to write the results.
     */
    @Parameter("The folder to write the results.")
    protected String outputfolder; // Default in validate
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

        // Begin mutation analysis
        int killed = 0;
        int quasi = 0;
        for (int id = 0; id < mutants.size(); id++) {
            boolean quasiMutant = false;
            Schema mutant = mutants.get(id).getMutatedArtefact();

            LOGGER.log(Level.INFO, "Mutant {0}", id);

            // Drop existing tables
            timer.start(ExperimentTimer.TimingPoint.DROPS_TIME);
            List<String> dropStmts = sqlWriter.writeDropTableStatements(mutant, true);
            if (dropfirst) {
                for (String stmt : dropStmts) {
                    databaseInteractor.executeUpdate(stmt);
                }
            }
            timer.stop(ExperimentTimer.TimingPoint.DROPS_TIME);

            // Create the schema in the database
            timer.start(ExperimentTimer.TimingPoint.CREATES_TIME);
            List<String> createStmts = sqlWriter.writeCreateTableStatements(mutant);
            for (String stmt : createStmts) {
                Integer res = databaseInteractor.executeUpdate(stmt);
                if (res.intValue() == -1) {
                    quasiMutant = true;
                }
            }
            timer.stop(ExperimentTimer.TimingPoint.CREATES_TIME);

            // Insert the test data
            timer.start(ExperimentTimer.TimingPoint.INSERTS_TIME);
            if (!quasiMutant) {
                List<SQLInsertRecord> insertStmts = originalReport.getInsertStatements();
                ArrayList<String> groupedStatements = new ArrayList<>();
                for (SQLInsertRecord insertRecord : insertStmts) {
                    if (insertRecord.isSatisfying()) {
                        groupedStatements.add(insertRecord.getStatement());
                    } else {
                        if (!groupedStatements.isEmpty()) {
                            int returnCount = databaseInteractor.executeUpdatesAsTransaction(groupedStatements);
                            groupedStatements.clear();
                            if (returnCount == 0) {
                                killed++;
                                break;
                            }
                        }
                        int returnCount = databaseInteractor.executeUpdate(insertRecord.getStatement());
                        if (returnCount != insertRecord.getReturnCode()) {
                            killed++;
                            break; // Stop once killed
                        }
                    }
                }
                if (!groupedStatements.isEmpty()) {
                    int returnCount = databaseInteractor.executeUpdatesAsTransaction(groupedStatements);
                    groupedStatements.clear();
                    if (returnCount == 0) {
                        killed++;
                    }
                }
            } else {
                // Don't continue if mutant is quasi
                quasi++;
                killed++;
            }
            timer.stop(ExperimentTimer.TimingPoint.INSERTS_TIME);

            // Drop tables
            timer.start(ExperimentTimer.TimingPoint.DROPS_TIME);
            for (String stmt : dropStmts) {
                databaseInteractor.executeUpdate(stmt);
            }
            timer.stop(ExperimentTimer.TimingPoint.DROPS_TIME);
        }

        timer.stopAll();
        timer.finalise();

        result.addValue("scorenumerator", killed);
        result.addValue("scoredenominator", mutants.size());
        result.addValue("mutationpipeline", mutationPipeline.replaceAll(",", "|"));
        result.addValue("threads", 1);
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

    @Override
    protected void validateParameters() {
        //TODO: Validate parameters
    }

    public static void main(String[] args) {
        new TransactedOriginal().run(args);
    }
}
