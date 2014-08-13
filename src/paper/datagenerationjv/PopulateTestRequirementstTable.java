package paper.datagenerationjv;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;

import java.util.Map;

/**
 * Created by phil on 13/08/2014.
 */
public class PopulateTestRequirementstTable {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("No results database file name provided");
            System.exit(1);
        }

        ResultsDatabase db = new ResultsDatabase(args[0]);
        Map<String, Schema> schemas = db.getSchemas();

        for (String schemaClassName : schemas.keySet()) {

            Schema schema = schemas.get(schemaClassName);

            Map<String, CoverageCriterion> coverageCriteria = db.getCoverageCriteria(schema);

            for (String coverageCriterionClassName : coverageCriteria.keySet()) {

                CoverageCriterion coverageCriterion = coverageCriteria.get(coverageCriterionClassName);

                TestRequirements tr = coverageCriterion.generateRequirements();

                int total = tr.size();

                tr.filterInfeasible();

                int totalMinusInfeasible = tr.size();

                tr.reduce();

                int totalMinusDuplicates = tr.size();

                String data = "\"" + schemaClassName + "\", \"" + coverageCriterionClassName + "\", " +  total + ", " +  totalMinusInfeasible + ", " +  totalMinusDuplicates;

                String sql = "INSERT INTO test_requirement_stats VALUES(" + data + ")";

                System.out.println(sql);

                db.executeInsert(sql);

            }
        }
    }
}
