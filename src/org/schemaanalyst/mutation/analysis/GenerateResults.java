/*
 */
package org.schemaanalyst.mutation.analysis;

import java.io.File;
import java.util.List;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.ConstraintGoalReport;
import org.schemaanalyst.datageneration.CoverageReport;
import org.schemaanalyst.datageneration.DataGenerator;
import org.schemaanalyst.datageneration.GoalReport;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiserFactory;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.datageneration.search.AlternatingValueSearch;
import org.schemaanalyst.datageneration.search.Search;
import org.schemaanalyst.datageneration.search.SearchConstraintCoverer;
import org.schemaanalyst.datageneration.search.datainitialization.NoDataInitialization;
import org.schemaanalyst.datageneration.search.datainitialization.RandomDataInitializer;
import org.schemaanalyst.datageneration.search.termination.CombinedTerminationCriterion;
import org.schemaanalyst.datageneration.search.termination.CounterTerminationCriterion;
import org.schemaanalyst.datageneration.search.termination.OptimumTerminationCriterion;
import org.schemaanalyst.datageneration.search.termination.TerminationCriterion;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.util.runner.Runner;
import org.schemaanalyst.mutation.SQLExecutionRecord;
import org.schemaanalyst.mutation.SQLExecutionReport;
import org.schemaanalyst.mutation.SQLInsertRecord;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.random.Random;
import org.schemaanalyst.util.random.SimpleRandom;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
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
    protected String outputfolder;
    /**
     * The number of max evaluations.
     */
    @Parameter
    protected int maxevaluations = 100000;
    /**
     * The number of satisfy rows.
     */
    @Parameter
    protected int satisfyrows = 2;
    /**
     * The number of negate rows.
     */
    @Parameter
    protected int negaterows = 1;
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
    public void task() {
        // Setup
        if (outputfolder == null) {
            outputfolder = locationsConfiguration.getResultsDir() + File.separator + "generatedresults" + File.separator;
        }
        
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
        DataGenerator dataGenerator = constructDataGenerator(schema, dbms);
        CoverageReport covReport = dataGenerator.generate();

        // Insert the test data
        for (GoalReport goalReport : covReport.getSuccessfulGoalReports()) {
            List<String> insertStmts = sqlWriter.writeInsertStatements(goalReport.getData());
            boolean isSatisfying = true;
            if (goalReport instanceof ConstraintGoalReport) {
                isSatisfying = ((ConstraintGoalReport) goalReport).getConstraint() == null;
            }
            for (String stmt : insertStmts) {
                SQLInsertRecord record = new SQLInsertRecord(stmt, databaseInteractor.executeUpdate(stmt), isSatisfying);
                sqlReport.addInsertStatement(record);
            }
        }

        // Drop tables
        for (String stmt : dropStmts) {
            databaseInteractor.executeUpdate(stmt);
        }

        // Store results
        XMLSerialiser.save(sqlReport, outputfolder + casestudy + ".xml");
    }

    /**
     * Creates a data generator for the given Schema and ValueFactory.
     *
     * @param schema The schema in use.
     * @param dbms The DBMS in use.
     * @return The data generator.
     */
    private DataGenerator constructDataGenerator(Schema schema, DBMS dbms) {
        Random random = new SimpleRandom(randomseed);
        // TODO: Use DataGeneratorFactory
        // return DataGeneratorFactory.instantiate(datagenerator, schema, dbms, random, CellRandomisationFactory.instantiate(randomprofile, random));
        CellRandomiser cellRandomiser = CellRandomiserFactory.instantiate(randomprofile, random);
        Search<Data> search = new AlternatingValueSearch(random,
                new NoDataInitialization(),
                new RandomDataInitializer(cellRandomiser));

        TerminationCriterion terminationCriterion = new CombinedTerminationCriterion(
                new CounterTerminationCriterion(search.getEvaluationsCounter(),
                maxevaluations),
                new OptimumTerminationCriterion<>(search));

        search.setTerminationCriterion(terminationCriterion);

        return new SearchConstraintCoverer(search,
                schema,
                dbms,
                satisfyrows,
                negaterows);
    }

    public static void main(String[] args) {
        new GenerateResults().run(args);
    }

    @Override
    protected void validateParameters() {
        if (maxevaluations <= 0) {
            exitWithArgumentException("maxEvaluations must be > 0");
        }
        if (satisfyrows < 0) {
            exitWithArgumentException("satisfyRows must be >= 0");
        }
        if (negaterows < 0) {
            exitWithArgumentException("negateRows must be >= 0");
        }
        if (!randomprofile.equals("small") && !randomprofile.equals("large")) {
            exitWithArgumentException("randomProfile must be 'small' or 'large'");
        }
    }
}
