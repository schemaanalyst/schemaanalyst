/*
 */
package org.schemaanalyst.mutation.mutationanalysis;

import org.schemaanalyst.mutation.mutators.ConstraintMutatorWithoutFK;
import java.io.File;
import java.util.List;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.SQLExecutionReport;
import org.schemaanalyst.mutation.SQLInsertRecord;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.csv.CSVResult;
import org.schemaanalyst.util.csv.CSVWriter;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;
import org.schemaanalyst.util.xml.XMLSerialiser;

/**
 *
 * @author Chris J. Wright
 */
@RequiredParameters("casestudy trial")
public class OriginalTechnique extends Runner {

    /**
     * The name of the schema to use.
     */
    @Parameter
    protected String casestudy;
    /**
     * The number of the trial.
     */
    @Parameter
    protected int trial;
    /**
     * The folder to retrieve the generated results.
     */
    @Parameter
    protected String inputfolder; // Default in validate
    /**
     * The folder to write the results.
     */
    @Parameter
    protected  String outputFolder; // Default in validate
    /**
     * Whether to submit drop statements prior to running.
     */
    @Parameter
    protected boolean dropFirst = false;

    @Override
    public void run(String... args) {
        // Parse arguments
        initialise(args);

        // Start results file
        CSVResult result = new CSVResult();
        result.addValue("dbms", databaseConfiguration.getDbDbms());
        result.addValue("casestudy", casestudy);
        result.addValue("trial", trial);

        // Instantiate the DBMS and related objects
        DBMS dbms;
        try {
            dbms = DBMSFactory.instantiate(databaseConfiguration.getDbDbms());
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

            System.out.println("Mutant " + id);

            // Drop existing tables
            List<String> dropStmts = sqlWriter.writeDropTableStatements(mutant, true);
            if (dropFirst) {
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
        
        new CSVWriter(outputFolder + casestudy + ".dat").write(result);
    }

    @Override
    protected void validateParameters() {
        //TODO: Validate parameters
        if (inputfolder == null) {
            inputfolder = folderConfiguration.getResultsDir() + File.separator + "generatedresults" + File.separator;
        }
        if (outputFolder == null) {
            outputFolder = folderConfiguration.getResultsDir() + File.separator;
        }
    }
}
