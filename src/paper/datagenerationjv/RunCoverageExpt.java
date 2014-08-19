package paper.datagenerationjv;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.DataGeneratorFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.*;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;

import java.util.List;

import static paper.datagenerationjv.Instantiator.instantiateCoverageCriterion;
import static paper.datagenerationjv.Instantiator.instantiateDBMS;
import static paper.datagenerationjv.Instantiator.instantiateSchema;

/**
 * Created by phil on 13/08/2014.
 */
public class RunCoverageExpt {

    protected ResultsDatabase resultsDatabase;
    protected int maxEvaluations = 100000;

    public RunCoverageExpt(String resultsDatabaseFileName) {
        resultsDatabase = new ResultsDatabase(resultsDatabaseFileName);
    }

    public void runExpts() {
        List<String> schemaNames = resultsDatabase.getNames("schemas");
        for (String schemaName : schemaNames) {
            List<String> coverageCriteriaNames = resultsDatabase.getNames("coverage_criteria");
            for (String coverageCriterionName : coverageCriteriaNames) {
                if (!schemaName.equals("DellStore") && !schemaName.equals("BrowserCookies"))
                expt(schemaName, coverageCriterionName, "avsDefaults", "HyperSQL", 1);
            }
        }
    }

    protected void expt(String schemaName,
                        String coverageCriterionName,
                        String dataGeneratorName,
                        String dbmsName,
                        int runNo) {

        Schema schema = instantiateSchema(schemaName);
        DBMS dbms = instantiateDBMS(dbmsName);
        CoverageCriterion coverageCriterion = instantiateCoverageCriterion(coverageCriterionName, schema, dbms);

        long seed = resultsDatabase.getSeed(runNo);

        TestRequirements testRequirements = coverageCriterion.generateRequirements();

        DataGenerator dataGeneratorObject = DataGeneratorFactory.instantiate(
                dataGeneratorName, seed, maxEvaluations, schema);

        // filter and reduce test requirements
        testRequirements.filterInfeasible();
        testRequirements.reduce();

        // generate the test suite
        TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator(
                schema,
                testRequirements,
                dbms.getValueFactory(),
                dataGeneratorObject);

        TestSuite testSuite = testSuiteGenerator.generate();

        // execute each test case to see what the DBMS result is for each row generated (accept / row)
        TestCaseExecutor executor = new TestCaseExecutor(
                schema,
                dbms,
                new DatabaseConfiguration(),
                new LocationsConfiguration());
        executor.execute(testSuite);

        // check the results
        int numWarnings = 0;
        for (TestCase testCase : testSuite.getTestCases()) {
            Boolean result = testCase.getTestRequirement().getResult();
            Boolean dbmsResult = testCase.getLastDBMSResult();
            if (result != null && result != dbmsResult) {
                numWarnings ++;
            }
        }

        // get the stats
        TestSuiteGenerationReport report = testSuiteGenerator.getTestSuiteGenerationReport();

        int numReqsCovered = report.getNumTestRequirementsCovered();
        int numReqsNotCovered = report.getNumTestRequirementsFailed();
        int successfulEvaluations = report.getNumEvaluations(true);
        int allEvaluations = report.getNumEvaluations(false);

        String data = "\"" + schemaName + "\", \"" + coverageCriterionName + "\", \"" + dataGeneratorName + "\", "
                    + "\"" + dbmsName + "\", " + runNo + ", " + numReqsCovered + ", " + numReqsNotCovered + ", "
                    + successfulEvaluations + ", " + allEvaluations + ", " + numWarnings;

        String sql = "INSERT INTO test_generation_run VALUES (NULL, " + data + ")";

        System.out.println(sql);

        resultsDatabase.executeInsert(sql);
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("No results database file name provided");
            System.exit(1);
        }

        RunCoverageExpt rce = new RunCoverageExpt(args[0]);
        rce.runExpts();
    }
}
