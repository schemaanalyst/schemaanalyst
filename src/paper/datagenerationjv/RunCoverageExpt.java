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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static paper.datagenerationjv.Instantiator.instantiateCoverageCriterion;
import static paper.datagenerationjv.Instantiator.instantiateDBMS;
import static paper.datagenerationjv.Instantiator.instantiateSchema;

/**
 * Created by phil on 13/08/2014.
 */
public class RunCoverageExpt {

    protected LocationsConfiguration locationsConfiguration;
    protected ResultsDatabase resultsDatabase;
    protected int maxEvaluations = 100000;

    public RunCoverageExpt(String resultsDatabaseFileName) {
        locationsConfiguration = new LocationsConfiguration();
        resultsDatabase = new ResultsDatabase(resultsDatabaseFileName);
    }

    public String[] coverageCriteria() {
        String[] coverageCriteria = {"UCC"};
        return coverageCriteria;
    }

    public void runExpts() {
        for (String schemaName : resultsDatabase.getNames("schemas")) {
            if (schemaName.equals("iTrust"))
            for (String coverageCriterionName : coverageCriteria()) {
                for (String dbmsName : resultsDatabase.getNames("dbmses")) {
                    if (dbmsName.equals("SQLite")) {
                        String dataGeneratorName="random";
                        for (int i = 16; i <= 30; i++) {
                            expt(schemaName, coverageCriterionName, dataGeneratorName, dbmsName, i);
                        }
                    }
                }
            }
        }
    }



    public void expt(String schemaName,
                        String coverageCriterionName,
                        String dataGeneratorName,
                        String dbmsName,
                        int runNo) {

        if (resultsDatabase.alreadyDoneExpt(schemaName, coverageCriterionName, dataGeneratorName, dbmsName, runNo)) {
            return;
        }

        System.out.println(schemaName + ", " + coverageCriterionName + ", " + dataGeneratorName + ", " + dbmsName + ", " + runNo);

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
        int numReqs = numReqsCovered + report.getNumTestRequirementsFailed();
        int successfulEvaluations = report.getNumEvaluations(true);
        int allEvaluations = report.getNumEvaluations(false);

        String data = "\"" + schemaName + "\", \"" + coverageCriterionName + "\", \"" + dataGeneratorName + "\", "
                    + "\"" + dbmsName + "\", " + runNo + ", " + numReqsCovered + ", " + numReqs + ", "
                    + successfulEvaluations + ", " + allEvaluations + ", " + numWarnings;

        String sql = "INSERT INTO test_generation_runs VALUES (" + data + ", NULL, NULL)";

        System.out.println(sql);

        resultsDatabase.executeInsert(sql);

        // serialize the test suite
        try {
            String fileName = locationsConfiguration.getResultsDir() + "/" + schemaName + "-" + coverageCriterionName
                    + "-" + dataGeneratorName + "-" + dbmsName + "-" + runNo + ".testsuite";
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(testSuite);
            out.close();
            fileOut.close();
            System.out.println("Test suite serialized to " + fileName);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
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
