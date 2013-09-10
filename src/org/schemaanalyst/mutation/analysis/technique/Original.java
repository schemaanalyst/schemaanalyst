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
import org.schemaanalyst.util.csv.CSVWriter;
import org.schemaanalyst.util.runner.Description;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.xml.XMLSerialiser;

import org.schemaanalyst.mutation.analysis.result.SQLExecutionReport;
import org.schemaanalyst.mutation.analysis.result.SQLInsertRecord;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.pipeline.MutationPipelineFactory;

/**
 * Run the 'Original' style of mutation analysis. This requires that the result
 * generation tool has been run, as it bases the mutation analysis on the
 * results produced by it.
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
        result.addValue("technique", this.getClass().getName());
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
        long startTime = System.currentTimeMillis();
        
        // Get the mutation pipeline and generate mutants
        MutationPipeline<Schema> pipeline;
        try {
            pipeline = MutationPipelineFactory.<Schema>instantiate(mutationPipeline, schema);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        List<Mutant<Schema>> mutants = pipeline.mutate();

        // Begin mutation analysis
        int killed = 0;
        int quasi = 0;
        for (int id = 0; id < mutants.size(); id++) {
            boolean quasiMutant = false;
            Schema mutant = mutants.get(id).getMutatedArtefact();

            LOGGER.log(Level.INFO, "Mutant {0}", id);

            // Drop existing tables
            List<String> dropStmts = sqlWriter.writeDropTableStatements(mutant, true);
            if (dropfirst) {
                for (String stmt : dropStmts) {
                    databaseInteractor.executeUpdate(stmt);
                }
            }

            // Create the schema in the database
            List<String> createStmts = sqlWriter.writeCreateTableStatements(mutant);
            for (String stmt : createStmts) {
                Integer res = databaseInteractor.executeUpdate(stmt);
                if (res.intValue() == -1) {
                    quasiMutant = true;
                }
            }
            
            // Don't continue if mutant is quasi
            if (quasiMutant) {
                quasi++;
                killed++;
            }

            // Insert the test data
            if (!quasiMutant) {
                List<SQLInsertRecord> insertStmts = originalReport.getInsertStatements();
                for (SQLInsertRecord insertRecord : insertStmts) {
                    int returnCount = databaseInteractor.executeUpdate(insertRecord.getStatement());
                    if (returnCount != insertRecord.getReturnCode()) {
                        killed++;
                        break; // Stop once killed
                    }
                }
            }

            // Drop tables
            for (String stmt : dropStmts) {
                databaseInteractor.executeUpdate(stmt);
            }
        }
        
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        result.addValue("mutationtime", totalTime);
        result.addValue("mutationscore_numerator", killed);
        result.addValue("mutationscore_denominator", mutants.size());

        new CSVWriter(outputfolder + casestudy + ".dat").write(result);
    }

    @Override
    protected void validateParameters() {
        //TODO: Validate parameters
    }

    public static void main(String[] args) {
        new Original().run(args);
    }
}