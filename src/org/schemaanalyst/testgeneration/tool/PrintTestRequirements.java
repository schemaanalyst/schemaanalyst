package org.schemaanalyst.testgeneration.tool;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterionFactory;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;

/**
 * Created by phil on 24/02/2014.
 */
@RequiredParameters("schema criterion")
public class PrintTestRequirements extends Runner {

    @Parameter("The name of the schema to use.")
    protected String schema;

    @Parameter("The coverage criterion to use to generate data.")
    protected String criterion;

    @Parameter("Whether to filter out duplicate and infeasible test requirements")
    protected boolean filter = true;

    @Parameter("Whether to reduce the predicate of the test requirement")
    protected boolean reduce = true;

    @Override
    protected void task() {
        TestRequirements tr =
                CoverageCriterionFactory.integrityConstraintCoverageCriterion(criterion, instantiateSchema())
                        .generateRequirements();

        if (filter) {
            tr.filterInfeasible();
            tr.reduce();
        }

        for (TestRequirement req : tr.getTestRequirements()) {
            System.out.println(req.toString(reduce) + "\n");
        }

        System.out.println("Total number of test requirements: " + tr.size());
    }

    private Schema instantiateSchema() {
        try {
            return (Schema) Class.forName(schema).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void validateParameters() {
        // no params to validate
    }

    public static void main(String... args) {
        new PrintTestRequirements().run(args);
    }

}
