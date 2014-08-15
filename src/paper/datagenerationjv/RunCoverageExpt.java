package paper.datagenerationjv;

import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.DataGeneratorFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.sqlite.SQLiteDBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.TestSuiteGenerationReport;
import org.schemaanalyst.testgeneration.TestSuiteGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;

import java.util.List;
import java.util.Map;

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

    protected void expt(String schemaName,
                        String coverageCriterionName,
                        String dataGeneratorName,
                        String dbmsName,
                        int runNo) {

        Schema schema = instantiateSchema(schemaName);
        CoverageCriterion coverageCriterion = instantiateCoverageCriterion(coverageCriterionName, schema);
        DBMS dbms = instantiateDBMS(dbmsName);
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

        testSuiteGenerator.generate();

        // get the stats
        TestSuiteGenerationReport report = testSuiteGenerator.getTestSuiteGenerationReport();

        int numReqsCovered = report.getNumTestRequirementsCovered();
        int successfulEvaluations = report.getNumEvaluations(true);
        int allEvaluations = report.getNumEvaluations(false);

        String data = "\"" + schemaName + "\", \"" + coverageCriterionName + "\", \"" + dataGeneratorName + "\", "
                    + "\"" + dbmsName + "\", " + runNo + ", " + numReqsCovered + ", " + successfulEvaluations
                    + ", " + allEvaluations;

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

        // schemaName,
        // coverageCriterionName,
        // dataGeneratorName,
        // dbmsName,
        // runNo
        rce.expt("BrowserCookies", "ClauseAICC", "avsDefaults", "SQLite", 1);
    }
}
