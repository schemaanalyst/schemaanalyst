/*
 */
package org.schemaanalyst.mutation.mutationanalysis;

import java.io.File;
import java.util.List;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.datageneration.CoverageReport;
import org.schemaanalyst.datageneration.DataGenerator;
import org.schemaanalyst.datageneration.DataGeneratorFactory;
import org.schemaanalyst.datageneration.GoalReport;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomisationFactory;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.deprecated.Configuration;
import org.schemaanalyst.mutation.SQLExecutionRecord;
import org.schemaanalyst.mutation.SQLExecutionReport;
import org.schemaanalyst.mutation.SQLInsertRecord;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.random.Random;
import org.schemaanalyst.util.random.SimpleRandom;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;
import org.schemaanalyst.util.xml.XMLSerialiser;

/**
 *
 * @author Chris J. Wright
 */
@RequiredParameters("casestudy")
public class GenerateResults extends Runner {

    /**
     * The name of the schema to use.
     */
    @Parameter
    protected String casestudy;
    /**
     * The folder to store the generated results.
     */
    @Parameter
    protected String outputfolder = folderConfiguration.getResultsDir();
    /**
     * The number of max evaluations.
     */
    @Parameter
    protected int maxEvaluations = 100000;
    /**
     * The number of satisfy rows.
     */
    @Parameter
    protected int satisfyRows = 2;
    /**
     * The number of negate rows.
     */
    @Parameter
    protected int negateRows = 1;
    /**
     * The random seed.
     */
    @Parameter
    protected long randomseed = 0;
    /**
     * The random profile.
     */
    @Parameter
    protected String randomprofile = "small";
    /**
     * The data generator.
     */
    @Parameter
    protected String datagenerator = "alternatingValue";

    @Override
    public void run() {
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
        ValueFactory valueFactory = dbms.getValueFactory();
        DataGenerator dataGenerator = constructDataGenerator(schema, valueFactory);
        CoverageReport covReport = dataGenerator.generate();
        
        // Insert the test data
        for (GoalReport goalReport : covReport.getSuccessfulGoalReports()) {
            List<String> insertStmts = sqlWriter.writeInsertStatements(goalReport.getData());
            for (String stmt : insertStmts) {
                SQLInsertRecord record = new SQLInsertRecord(stmt, databaseInteractor.executeUpdate(stmt));
                sqlReport.addInsertStatement(record);
            }
        }
        
        // Drop tables
        for (String stmt : dropStmts) {
            databaseInteractor.executeUpdate(stmt);
        }
        
        // Store results
        XMLSerialiser.save(sqlReport, outputfolder + File.separator + casestudy + ".xml");
    }

    /**
     * Creates an AVM data generator for the given Schema and ValueFactory.
     *
     * @param schema The schema in use.
     * @param valueFactory The value factory to use.
     * @return The data generator.
     */
    private DataGenerator constructDataGenerator(Schema schema, ValueFactory valueFactory) {
        Random random = new SimpleRandom(randomseed);
        return DataGeneratorFactory.instantiate(datagenerator, schema, valueFactory, random, constructCellRandomizationProfile(random));
    }
    
    /**
     * Creates a randomizer for the configured random profile.
     *
     * @param random The random number generator.
     * @return The randomizer.
     */
    protected CellRandomiser constructCellRandomizationProfile(Random random) {
        switch (randomprofile) {
            case "small":
                return CellRandomisationFactory.small(random);
            case "large":
                return CellRandomisationFactory.large(random);
            default:
                throw new RuntimeException("Unknown random profile \"" + Configuration.randomprofile + "\"");
        }
    }

    public GenerateResults(String... args) {
        super(args);
    }

    public static void main(String[] args) {
        new GenerateResults(args).run();
    }
}
