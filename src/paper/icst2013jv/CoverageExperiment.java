package paper.icst2013jv;

import org.schemaanalyst.coverage.CoverageReport;
import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.types.CriterionFactory;
import org.schemaanalyst.coverage.search.SearchBasedTestCaseGenerationAlgorithm;
import org.schemaanalyst.coverage.testgeneration.TestCaseGenerationAlgorithm;
import org.schemaanalyst.coverage.testgeneration.TestSuite;
import org.schemaanalyst.coverage.testgeneration.TestSuiteGenerator;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.datageneration.search.Search;
import org.schemaanalyst.datageneration.search.SearchFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.runner.Runner;
import parsedcasestudy.*;

/**
 * Created by phil on 25/02/2014.
 */
public class CoverageExperiment extends Runner {
    
    Schema[] schemas = {
            new BankAccount(),
            new BookTown(),
            new Cloc(),
            new CoffeeOrders(),
            new CustomerOrder(),
            new DellStore(),
            new Flights(),
            new FrenchTowns(),
            new Inventory(),
            new Iso3166(),
            new JWhoisServer(),
            new NistDML181(),
            new NistDML182(),
            new NistDML183(),
            new NistWeather(),
            new NistXTS748(),
            new NistXTS749(),
            new Person(),
            new Products(),
            new RiskIt(),
            new StudentResidence(),
            new UnixUsage(),
            new Usda()
    };

    Criterion[] generateCriteria = {
            CriterionFactory.amplifiedConstraintCACWithNullAndUniqueColumnCACCoverage(),
            CriterionFactory.amplifiedConstraintCACCoverage(),
            CriterionFactory.constraintCACWithNullAndUniqueColumnCACCoverage(),
            CriterionFactory.constraintCACCoverage(),
    };

    Criterion[] testCriteria = {
            CriterionFactory.constraintCoverage()
    };

    Boolean[] reuseOptions = {
            true, false
    };

    @Override
    protected void task() {
        for (Schema schema : schemas) {
            for (Criterion criterion : generateCriteria) {
                for (Boolean reuseTestCases : reuseOptions) {
                    doExpt(schema, criterion, reuseTestCases);
                }
            }
            System.out.println();
        }
    }

    void doExpt(Schema schema, Criterion criterion, boolean reuseTestCases) {

        Search<Data> search = SearchFactory.avsDefaults(0L, 100000);

        // instantiate the test case generation algorithm
        TestCaseGenerationAlgorithm testCaseGenerator =
                new SearchBasedTestCaseGenerationAlgorithm(search);

        // instantiate the test suite generator and generate the test suite
        TestSuiteGenerator dg = new TestSuiteGenerator(
                schema,
                criterion,
                new ValueFactory(),
                testCaseGenerator,
                reuseTestCases);

        TestSuite testSuite = dg.generate();

        System.out.print(schema.getName() + ", " +
                         criterion.getName() + ", " +
                         reuseTestCases + ", " +
                         criterion.generateRequirements(schema).size() + ", " +
                         testSuite.getNumTestCases() + ", " +
                         testSuite.getNumInserts() + ", ");

        for (Criterion checkCriteria : generateCriteria) {
            CoverageReport coverageReport =
                    testCaseGenerator.computeCoverage(testSuite, checkCriteria.generateRequirements(schema));
            System.out.print(", " + coverageReport.getCoverage());
        }

        System.out.print(", ");

        for (Criterion checkCriteria : testCriteria) {
            CoverageReport coverageReport =
                    testCaseGenerator.computeCoverage(testSuite, checkCriteria.generateRequirements(schema));
            System.out.print(", " + coverageReport.getCoverage());
        }

        System.out.println();
    }

    @Override
    protected void validateParameters() {
        // nothing to validate
    }

    public static void main(String[] args) {
        new CoverageExperiment().run(args);
    }
}
