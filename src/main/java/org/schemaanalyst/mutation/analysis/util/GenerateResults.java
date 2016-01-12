/*
 */
package org.schemaanalyst.mutation.analysis.util;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.analysis.result.SQLExecutionRecord;
import org.schemaanalyst.mutation.analysis.result.SQLExecutionReport;
import org.schemaanalyst.mutation.analysis.result.SQLInsertRecord;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;
import org.schemaanalyst.util.tuple.MixedPair;
import org.schemaanalyst.util.xml.XMLSerialiser;

import java.io.File;
import java.util.List;

/**
 * <p>
 * An abstract {@link Runner} for acquiring data from some implemented source, 
 * testing it against a non-mutant schema and persisting the results to file.
 * </p>
 * 
 * @author Chris J. Wright
 */
@RequiredParameters("casestudy")
public abstract class GenerateResults extends Runner {

    /**
     * The name of the schema to use.
     */
    @Parameter
    protected String casestudy;
    /**
     * The folder to store the generated results.
     */
    @Parameter
    protected String outputfolder;

    protected Schema schema;
    protected DBMS dbms;
    protected SQLWriter sqlWriter;
    
    @Override
    public void task() {
        // Setup
        if (outputfolder == null) {
            outputfolder = locationsConfiguration.getResultsDir() + File.separator + "generatedresults" + File.separator;
        }

        // Instantiate the DBMS and related objects
        dbms = DBMSFactory.instantiate(databaseConfiguration.getDbms());
        sqlWriter = dbms.getSQLWriter();
        DatabaseInteractor databaseInteractor = dbms.getDatabaseInteractor(casestudy, databaseConfiguration, locationsConfiguration);

        // Get the required schema class
        try {
            schema = (Schema) Class.forName(casestudy).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }

        // Create the report
        SQLExecutionReport sqlReport = new SQLExecutionReport();

        // Drop existing tables
        List<String> dropStmts = sqlWriter.writeDropTableStatements(schema, true);
        for (String stmt : dropStmts) {
            databaseInteractor.executeUpdate(stmt);
        }

        // Create the schema in the database and store result
        List<String> createStmts = sqlWriter.writeCreateTableStatements(schema);
        for (String stmt : createStmts) {
            int returnCount = databaseInteractor.executeUpdate(stmt);
            sqlReport.addCreateTableStatement(new SQLExecutionRecord(stmt, returnCount));
        }

        // Generate the test data
        List<MixedPair<String,Boolean>> insertStmts = getInserts();

        // Insert the test data
        for (MixedPair<String,Boolean> pair : insertStmts) {
            String stmt = pair.getFirst();
            Boolean satisfying = pair.getSecond();
            SQLInsertRecord record = new SQLInsertRecord(stmt, databaseInteractor.executeUpdate(stmt), satisfying);
            sqlReport.addInsertStatement(record);
        }

        // Drop tables
        for (String stmt : dropStmts) {
            databaseInteractor.executeUpdate(stmt);
        }

        // Store results
        XMLSerialiser.save(sqlReport, outputfolder + casestudy + ".xml");
    }

    public abstract List<MixedPair<String,Boolean>> getInserts();
}
