package paper.datagenerationjv;

import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.DataGeneratorFactory;
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
public class CompareDefaults extends Runner {

    final long SEED = 1000L;

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

        DataGenerator normalDataGeneratorObject = DataGeneratorFactory.instantiate(datagenerator, SEED, 10000, schemaObject);
        DataGenerator defaultsDataGeneratorObject = DataGeneratorFactory.instantiate(datagenerator + "Defaults", SEED, 10000, schemaObject);

        // generate the test suites
        TestSuiteGenerator normalTestSuiteGenerator = new TestSuiteGenerator(
                schemaObject,
                criterionObject,
                new ValueFactory(),
                normalDataGeneratorObject);
        TestSuite normalTestSuite = normalTestSuiteGenerator.generate();

        TestSuiteGenerator defaultsTestSuiteGenerator = new TestSuiteGenerator(
                schemaObject,
                criterionObject,
                new ValueFactory(),
                defaultsDataGeneratorObject);
        TestSuite defaultsTestSuite = defaultsTestSuiteGenerator.generate();

        int numReqs = criterionObject.generateRequirements(schemaObject).size();

        System.out.println("---------------------------------------------");
        System.out.println(schema);
        System.out.println("                                 N\tD");

        System.out.println("Number of successful test cases: " + normalTestSuite.getNumTestCases() + "\t" + defaultsTestSuite.getNumTestCases());
        System.out.println("Number of failed test cases:     " + (numReqs - normalTestSuite.getNumTestCases()) + "\t" + (numReqs - defaultsTestSuite.getNumTestCases()));
        System.out.println("Number of evaluations:           " + normalTestSuite.getNumEvaluations() + "\t" + defaultsTestSuite.getNumEvaluations());
        System.out.println("Av. no of evaluations:           " + (normalTestSuite.getNumEvaluations() / normalTestSuite.getNumTestCases()) + "\t" + (defaultsTestSuite.getNumEvaluations() / defaultsTestSuite.getNumTestCases()));
        System.out.println("---------------------------------------------");
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
        new CompareDefaults().run(args);
    }
}
