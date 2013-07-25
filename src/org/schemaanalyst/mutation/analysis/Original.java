/*
 */
package org.schemaanalyst.mutation.analysis;

import org.schemaanalyst.mutation.mutators.ConstraintMutatorWithoutFK;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.util.runner.Runner;
import org.schemaanalyst.mutation.SQLExecutionReport;
import org.schemaanalyst.mutation.SQLInsertRecord;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.csv.CSVResult;
import org.schemaanalyst.util.csv.CSVWriter;
import org.schemaanalyst.util.runner.Description;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.xml.XMLSerialiser;

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
    @Parameter(value="Whether to submit drop statements prior to running.", valueAsSwitch = "true")
    protected boolean dropfirst = false;

    @Override
    public void task() {
        // Start results file
        CSVResult result = new CSVResult();
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
        DatabaseInteractor databaseInteractor = dbms.getDatabaseInteractor();

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

        // Create the mutant schemas
        ConstraintMutatorWithoutFK cm = new ConstraintMutatorWithoutFK();
        List<Schema> mutants = cm.produceMutants(schema);

        // Begin mutation analysis
        int killed = 0;
        for (int id = 0; id < mutants.size(); id++) {
            Schema mutant = mutants.get(id);

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
                databaseInteractor.executeUpdate(stmt);
            }

            // Insert the test data
            List<SQLInsertRecord> insertStmts = originalReport.getInsertStatements();
            for (SQLInsertRecord insertRecord : insertStmts) {
                int returnCount = databaseInteractor.executeUpdate(insertRecord.getStatement());
                if (returnCount != insertRecord.getReturnCode()) {
                    killed++;
                    break; // Stop once killed
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
        if (inputfolder == null) {
            inputfolder = locationsConfiguration.getResultsDir() + File.separator + "generatedresults" + File.separator;
        }
        if (outputfolder == null) {
            outputfolder = locationsConfiguration.getResultsDir() + File.separator;
        }
    }

    public static void main(String[] args) {
        new Original().run(args);
    }
}
