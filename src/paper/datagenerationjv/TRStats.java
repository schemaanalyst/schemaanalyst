package paper.datagenerationjv;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterionFactory;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;

import static paper.datagenerationjv.Config.*;

/**
 * Created by phil on 25/07/2014.
 */
public class TRStats {

    public static void main(String[] args) {

        final String SEP = ", ";
        final String EOL = "\n ";

        String data = "";

        for (Schema schema : SCHEMAS) {

            data = schema.getName();

            for (String criterionName : COVERAGE_CRITERIA) {

                data += SEP;

                CoverageCriterion cc = CoverageCriterionFactory.integrityConstraintCoverageCriterion(criterionName, schema);

                TestRequirements tr = cc.generateRequirements();

                data += tr.size();

                tr.filterInfeasible();

                data += " (" + tr.size() + ")";

                tr.reduce();

                data += " (" + tr.size() + ")";

            }

            data += EOL;
        }

        System.out.println(data);
    }
}
