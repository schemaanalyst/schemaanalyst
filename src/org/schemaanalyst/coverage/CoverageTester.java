package org.schemaanalyst.coverage;

import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.types.CriterionFactory;
import org.schemaanalyst.coverage.search.SearchBasedTestCaseGenerationAlgorithm;
import org.schemaanalyst.coverage.testgeneration.TestSuite;
import org.schemaanalyst.coverage.testgeneration.TestSuiteGenerator;
import org.schemaanalyst.datageneration.search.SearchFactory;
import org.schemaanalyst.dbms.sqlite.SQLiteDBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.csv.CSVFileWriter;
import org.schemaanalyst.util.csv.CSVResult;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;

import java.io.File;

@RequiredParameters("casestudy criterion")
public class CoverageTester extends Runner {

    @Parameter("The name of the schema to use.")
    protected String casestudy;

    @Parameter("Whether to re-use test cases.")
    protected boolean reuse = true;

    @Parameter("The coverage criterion to use to generate data.")
    protected String criterion;

    @Override
    protected void task() {
        final Schema schema = instantiateSchema(casestudy);
        final Criterion chosenCriterion = CriterionFactory.instantiate(criterion);
        final Criterion amplifiedConstraintCACCoverage = CriterionFactory.amplifiedConstraintCACWithNullAndUniqueColumnCoverage();

        // Initialise test case generator
        final SearchBasedTestCaseGenerationAlgorithm testCaseGenerator
                = new SearchBasedTestCaseGenerationAlgorithm(
                SearchFactory.avsDefaults(0L, 100000));

        // Generate tests
        TestSuiteGenerator generator = new TestSuiteGenerator(
                schema,
                chosenCriterion,
                new SQLiteDBMS(),
                testCaseGenerator,
                reuse
        );
        TestSuite testSuite = generator.generate();

        // Write results
        CSVResult result = new CSVResult();
        result.addValue("casestudy", casestudy);
        result.addValue("criterion", criterion);
        result.addValue("reuse", reuse);
        result.addValue("tests", testSuite.getTestCases().size());
        result.addValue("inserts", testSuite.getNumInserts());
        result.addValue("coverage", testCaseGenerator.computeCoverage(testSuite, chosenCriterion.generateRequirements(schema)));
        result.addValue("amplifiedcaccoverage", testCaseGenerator.computeCoverage(testSuite, amplifiedConstraintCACCoverage.generateRequirements(schema)));
        CSVFileWriter writer = new CSVFileWriter(locationsConfiguration.getResultsDir() + File.separator + "coveragetester.dat");
        writer.write(result);
    }

    protected Schema instantiateSchema(String casestudy) {
        Schema schema;
        try {
            schema = (Schema) Class.forName(casestudy).newInstance();
            return schema;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void validateParameters() {
        // no params to validate
    }

    public static void main(String... args) {
        new CoverageTester().run(args);
    }
}
