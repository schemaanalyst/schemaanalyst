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
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.util.csv.CSVDatabaseWriter;

/**
 * <p> {@link Runner} for the 'Full Schemata' style of mutation analysis. This
 * requires that the result generation tool has been run, as it bases the
 * mutation analysis on the results produced by it.
 * </p>
 *
 * @author Chris J. Wright
 */
@Description("Runs the 'Full Schemata' style of mutation analysis. This "
        + "requires that the result generation tool has been run, as it bases "
        + "the mutation analysis on the results produced by it.")
@RequiredParameters("casestudy trial")
public class FullSchemata extends Runner {

    private final static Logger LOGGER = Logger.getLogger(FullSchemata.class.getName());
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

        // Schemata step: Rename the mutants
        renameMutants(mutants);

        // Schemata step: Build single drop statement
        StringBuilder dropBuilder = new StringBuilder();
        for (Mutant<Schema> mutant : mutants) {
            Schema mutantSchema = mutant.getMutatedArtefact();
            for (String statement : sqlWriter.writeDropTableStatements(mutantSchema, true)) {
                dropBuilder.append(statement);
                dropBuilder.append("; ");
                dropBuilder.append(System.lineSeparator());
            }
        }
        String dropStmt = dropBuilder.toString();

        // Schemata step: Build single create statement
        StringBuilder createBuilder = new StringBuilder();
        for (Mutant<Schema> mutant : mutants) {
            Schema mutantSchema = mutant.getMutatedArtefact();
            for (String statement : sqlWriter.writeCreateTableStatements(mutantSchema)) {
                createBuilder.append(statement);
                createBuilder.append("; ");
                createBuilder.append(System.lineSeparator());
            }
        }
        String createStmt = createBuilder.toString();

        // Schemata step: Drop existing tables before iterating mutants
        if (dropfirst) {
            databaseInteractor.executeUpdate(dropStmt);
        }

        // Schemata step: Create table before iterating mutants
        Integer res = databaseInteractor.executeUpdate(createStmt);
        boolean quasiSchema = false;
        if (res.intValue() == -1) {
            quasiSchema = true;
        }

        // Only do mutation analysis if the schema is valid
        int killed = 0;
        if (!quasiSchema) {
            // Begin mutation analysis
            for (int id = 0; id < mutants.size(); id++) {
                Schema mutant = mutants.get(id).getMutatedArtefact();

                LOGGER.log(Level.INFO, "Mutant {0}", id);

                // Schemata step: Generate insert prefix string
                String schemataPrefix = "INSERT INTO mutant_" + (id + 1) + "_";

                // Insert the test data
                List<SQLInsertRecord> insertStmts = originalReport.getInsertStatements();
                for (SQLInsertRecord insertRecord : insertStmts) {

                    // Schemata step: Rewrite insert for mutant ID
                    String insertStmt = insertRecord.getStatement().replaceAll("INSERT INTO ", schemataPrefix);

                    int returnCount = databaseInteractor.executeUpdate(insertStmt);
                    if (returnCount != insertRecord.getReturnCode()) {
                        killed++;
                        break; // Stop once killed
                    }
                }
            }
        }

        // Schemata step: Drop tables after iterating mutants
        databaseInteractor.executeUpdate(dropStmt);

        stopWatch.stop();
        dropsStopWatch.stop();
        createsStopWatch.stop();
        insertsStopWatch.stop();

        result.addValue("mutationtime", stopWatch.getTime());
        result.addValue("mutationscore_numerator", (!quasiSchema) ? killed : mutants.size());
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
        new FullSchemata().run(args);
    }
    
    private StopWatch constructSuspendedStopWatch() {
        StopWatch dropsStopwatch = new StopWatch();
        dropsStopwatch.start();
        dropsStopwatch.suspend();
        return dropsStopwatch;
    }
}
