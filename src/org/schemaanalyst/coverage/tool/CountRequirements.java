package org.schemaanalyst.coverage.tool;

import com.sun.org.apache.regexp.internal.recompile;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.coverage.criterion.requirements.Requirements;
import org.schemaanalyst.coverage.criterion.types.CriterionFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;
import parsedcasestudy.*;

import java.util.List;

/**
 * Created by phil on 07/03/2014.
 */

@RequiredParameters("criterion")
public class CountRequirements extends Runner {

    @Parameter("The coverage criterion to use to generate data.")
    protected String criterion;

    @Parameter("Prints more detailed info about duplicated predicates")
    protected boolean verbose;

    Schema[] schemas = new Schema[]{
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

    protected void task() {

        System.out.println("Counting requirements for " + criterion);

        for (Schema schema : schemas) {
            if (verbose) {
                System.out.println("Checking " + schema);
            }
            Requirements requirements =
                    CriterionFactory.instantiate(criterion)
                            .generateRequirements(schema);

            int unreducedCount = 0;
            for (Predicate predicate : requirements.getPredicates()) {
                List<String> purposes = predicate.getPurposes();
                int numPurposes = purposes.size();
                unreducedCount += numPurposes;
                if (verbose && numPurposes > 1) {
                    System.out.println("Duplicated predicate: " + predicate);
                    for (String purpose : purposes) {
                        System.out.println("\t* " + purpose);
                    }
                }
            }

            int reducedCount = requirements.size();
            int difference = reducedCount - unreducedCount;

            if (verbose) {
                System.out.println("Reduced count:\t" + reducedCount);
                System.out.println("Unreduced count:\t" + unreducedCount);
                System.out.println("Difference:\t" + difference);
            } else {
                System.out.println(schema + "\t" + reducedCount + "\t" + unreducedCount + "\t" + difference);
            }
        }
    }

    @Override
    protected void validateParameters() {
        // no params to validate
    }

    public static void main(String... args) {
        new CountRequirements().run(args);
    }


}
