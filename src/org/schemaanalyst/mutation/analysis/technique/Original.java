/*
 */
package org.schemaanalyst.mutation.analysis.technique;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.util.runner.Runner;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.csv.CSVResult;
import org.schemaanalyst.util.csv.CSVFileWriter;
import org.schemaanalyst.util.runner.Description;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.xml.XMLSerialiser;

import org.schemaanalyst.mutation.analysis.result.SQLExecutionReport;
import org.schemaanalyst.mutation.analysis.result.SQLInsertRecord;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import org.apache.commons.lang3.time.StopWatch;
import org.schemaanalyst.configuration.ExperimentConfiguration;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.pipeline.MutationPipelineFactory;
import org.schemaanalyst.util.csv.CSVDatabaseWriter;

/**
 * <p> {@link Runner} for the 'Original' style of mutation analysis. This
 * requires that the result generation tool has been run, as it bases the
 * mutation analysis on the results produced by it.
 * </p>
 *
 * @author Chris J. Wright
 */
@Description("Runs the 'Original' style of mutation analysis. This requires that"
        + " the result generation tool has been run, as it bases the mutation "
        + "analysis on the results produced by it.")
@RequiredParameters("casestudy trial")
public class Original extends Runner {

    private final static Logger LOGGER = Logger.getLogger(Original.class.getName());
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
        DBMS dbms;
        try {
            dbms = DBMSFactory.instantiate(databaseConfiguration.getDbms());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
        SQLWriter sqlWriter = dbms.getSQLWriter();
        DatabaseInteractor databaseInteractor = dbms.getDatabaseInteractor(casestudy, databaseConfiguration, locationsConfiguration);

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
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        StopWatch mutantGenerationStopWatch = constructSuspendedStopWatch();
        StopWatch dropsStopWatch = constructSuspendedStopWatch();
        StopWatch createsStopWatch = constructSuspendedStopWatch();
        StopWatch insertsStopWatch = constructSuspendedStopWatch();

        // Get the mutation pipeline and generate mutants
        mutantGenerationStopWatch.resume();
        MutationPipeline<Schema> pipeline;
        try {
            pipeline = MutationPipelineFactory.<Schema>instantiate(mutationPipeline, schema);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        List<Mutant<Schema>> mutants = pipeline.mutate();
        mutantGenerationStopWatch.stop();

        // Begin mutation analysis
        int killed = 0;
        int quasi = 0;
        for (int id = 0; id < mutants.size(); id++) {
            boolean quasiMutant = false;
            Schema mutant = mutants.get(id).getMutatedArtefact();

            LOGGER.log(Level.INFO, "Mutant {0}", id);

            // Drop existing tables
            dropsStopWatch.resume();
            List<String> dropStmts = sqlWriter.writeDropTableStatements(mutant, true);
            if (dropfirst) {
                for (String stmt : dropStmts) {
                    databaseInteractor.executeUpdate(stmt);
                }
            }
            dropsStopWatch.suspend();

            // Create the schema in the database
            createsStopWatch.resume();
            List<String> createStmts = sqlWriter.writeCreateTableStatements(mutant);
            for (String stmt : createStmts) {
                Integer res = databaseInteractor.executeUpdate(stmt);
                if (res.intValue() == -1) {
                    quasiMutant = true;
                }
            }
            createsStopWatch.suspend();

            // Insert the test data
            insertsStopWatch.resume();
            if (!quasiMutant) {
                List<SQLInsertRecord> insertStmts = originalReport.getInsertStatements();
                for (SQLInsertRecord insertRecord : insertStmts) {
                    int returnCount = databaseInteractor.executeUpdate(insertRecord.getStatement());
                    if (returnCount != insertRecord.getReturnCode()) {
                        killed++;
                        break; // Stop once killed
                    }
                }
            } else {
                // Don't continue if mutant is quasi
                quasi++;
                killed++;
            }
            insertsStopWatch.suspend();

            // Drop tables
            dropsStopWatch.resume();
            for (String stmt : dropStmts) {
                databaseInteractor.executeUpdate(stmt);
            }
            dropsStopWatch.suspend();
        }
        
        stopWatch.stop();
        dropsStopWatch.stop();
        createsStopWatch.stop();
        insertsStopWatch.stop();

        result.addValue("mutationtime", stopWatch.getTime());
        result.addValue("mutationscore_numerator", killed);
        result.addValue("mutationscore_denominator", mutants.size());
        result.addValue("mutationpipeline", mutationPipeline);
        result.addValue("dropstime", dropsStopWatch.getTime());
        result.addValue("createstime", createsStopWatch.getTime());
        result.addValue("insertstime", insertsStopWatch.getTime());
        result.addValue("mutantgenerationtime", mutantGenerationStopWatch.getTime());

        if (resultsToFile) {
            new CSVFileWriter(outputfolder + casestudy + ".dat").write(result);
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
        new Original().run(args);
    }

    private StopWatch constructSuspendedStopWatch() {
        StopWatch dropsStopwatch = new StopWatch();
        dropsStopwatch.start();
        dropsStopwatch.suspend();
        return dropsStopwatch;
    }
}
