package paper.datagenerationjv;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;

/**
 *
 * Expects a clean coverage_criteria_stats table.
 *
 * Created by phil on 13/08/2014.
 */
public class ComputeCoverageCriteriaStats extends ComputeUsing {

    @Override
    protected void computeUsing(String schemaName, Schema schema, String coverageCriterionName, CoverageCriterion coverageCriterion) {
        TestRequirements tr = coverageCriterion.generateRequirements();
        int numReqs = tr.size();

        tr.filterInfeasible();
        int numReqsMinusInfeasible = tr.size();

        tr.reduce();
        int numReqsMinusDuplicates = tr.size();

        String data = "\"" + schemaName + "\", \"" + coverageCriterionName + "\", " +  numReqs + ", " +  numReqsMinusInfeasible + ", " +  numReqsMinusDuplicates;

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
