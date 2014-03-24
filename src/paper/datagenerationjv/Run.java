package paper.datagenerationjv;

import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.DataGeneratorFactory;
import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.testgeneration.TestSuiteGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterionFactory;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;

import java.util.List;

/**
 * Created by phil on 24/03/2014.
 */
@RequiredParameters("schema criterion datagenerator")
public class Run extends Runner {

    @Parameter("The name of the schema to use.")
    protected String schema;

    @Parameter("The name of the coverage criterion to use.")
    protected String criterion;

    @Parameter("The name of the data generator to use.")
    protected String datagenerator;

    @Override
    protected void task() {

        // instantiate objects for parameters
        Schema schemaObject = instantiateSchema();
        CoverageCriterion criterionObject = CoverageCriterionFactory.instantiate(criterion);
        DataGenerator dataGeneratorObject = DataGeneratorFactory.instantiate(datagenerator, 0L, 10000, schemaObject);

        // generate the test suites
        TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator(
                schemaObject,
                criterionObject,
                new ValueFactory(),
                dataGeneratorObject);
        TestSuite testSuite = testSuiteGenerator.generate();
        List<TestCase> failedTestCases = testSuiteGenerator.getFailedTestCases();

        int numReqs = criterionObject.generateRequirements(schemaObject).size();

        System.out.println("---------------------------------------------");
        System.out.println(schema);
        System.out.println("Number of successful test cases: " + testSuite.getNumTestCases());
        System.out.println("Number of failed test cases:     " + (numReqs - testSuite.getNumTestCases()));
        System.out.println("Number of evaluations:           " + testSuite.getNumEvaluations());
        System.out.println("---------------------------------------------");

        // successful test cases
        //for (TestCase testCase : testSuite.getTestCases()) {
        //    System.out.println(testCase + "\n");
        //}

        for (TestCase testCase : failedTestCases) {
            for (Predicate predicate : testCase.getPredicates()) {
                System.out.println(predicate);
            }
            System.out.println();
        }
    }

    private Schema instantiateSchema() {
        try {
            return (Schema) Class.forName(schema).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void validateParameters() {
        // no params to validate
    }

    public static void main(String... args) {
        new Run().run(args);
    }
}
