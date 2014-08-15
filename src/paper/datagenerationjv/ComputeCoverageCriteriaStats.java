package paper.datagenerationjv;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;

import java.util.List;
import java.util.Map;

import static paper.datagenerationjv.Instantiator.instantiateCoverageCriterion;
import static paper.datagenerationjv.Instantiator.instantiateSchema;

/**
 *
 * Expects a clean coverage_criteria_stats table.
 *
 * Created by phil on 13/08/2014.
 */
public class ComputeCoverageCriteriaStats {

    protected ResultsDatabase resultsDatabase;

    public void compute(String resultsDatabaseFileName) {
        resultsDatabase = new ResultsDatabase(resultsDatabaseFileName);

        List<String> schemaNames = resultsDatabase.getNames("schemas");
        for (String schemaName : schemaNames) {
            List<String> coverageCriteriaNames = resultsDatabase.getNames("coverage_criteria");
            for (String coverageCriterionName : coverageCriteriaNames) {
                compute(schemaName, coverageCriterionName);
            }
        }
    }

    protected void compute(String schemaName, String coverageCriterionName) {
        Schema schema = instantiateSchema(schemaName);
        CoverageCriterion coverageCriterion = instantiateCoverageCriterion(coverageCriterionName, schema);

        TestRequirements tr = coverageCriterion.generateRequirements();
        int numReqs = tr.size();

        tr.reduce();
        int numReqsMinusDuplicates = tr.size();

        tr.filterInfeasible();
        int numReqsMinusInfeasible = tr.size();

        String data = "\"" + schemaName + "\", \"" + coverageCriterionName + "\", " +  numReqs + ", " +  numReqsMinusDuplicates + ", " +  numReqsMinusInfeasible;

        String sql = "INSERT INTO coverage_criteria_stats VALUES(" + data + ")";

        System.out.println(sql);

        resultsDatabase.executeInsert(sql);
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("No results database file name provided");
            System.exit(1);
        }

        new ComputeCoverageCriteriaStats().compute(args[0]);
    }
}
