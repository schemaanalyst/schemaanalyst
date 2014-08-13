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

/**
 * Created by phil on 13/08/2014.
 */
public class ComputeCoverageStats extends ComputeUsing {

    @Override
    protected void computeUsing(String schemaName, Schema schema, String coverageCriterionName, CoverageCriterion coverageCriterion, String dataGeneratorName) {

        TestRequirements testRequirements = coverageCriterion.generateRequirements();
        DataGenerator dataGeneratorObject = DataGeneratorFactory.instantiate(dataGeneratorName, 0L, 100000, schema);

        // to do -- switch this around if necessary ??
        DBMS dbms = new SQLiteDBMS();

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

        String data = "\"" + schemaName + "\", \"" + coverageCriterionName + "\", \"" + dataGeneratorName + "\", " + numReqsCovered + ", " + successfulEvaluations + ", " + allEvaluations;

        String sql = "INSERT INTO coverage_stats VALUES(" + data + ")";

        System.out.println(sql);

        resultsDatabase.executeInsert(sql);
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("No results database file name provided");
            System.exit(1);
        }

        new ComputeCoverageStats().compute(args[0]);
    }
}
