package org.schemaanalyst.testgeneration.tool;

import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.coveragecriterion.CriterionFactory;
import org.schemaanalyst.testgeneration.coveragecriterion.requirements.Requirements;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;

/**
 * Created by phil on 24/02/2014.
 */
@RequiredParameters("schema criterion")
public class PrintRequirements extends Runner {

    @Parameter("The name of the schema to use.")
    protected String schema;

    @Parameter("The coverage criterion to use to generate data.")
    protected String criterion;

    @Override
    protected void task() {
        Requirements requirements =
                CriterionFactory.instantiate(criterion)
                        .generateRequirements(instantiateSchema(schema));

        System.out.println("Number of requirements: " + requirements.size());

        int num = 1;
        for (Predicate predicate : requirements.getPredicates()) {
            System.out.println(num + ") " + predicate.getPurposes());
            System.out.println(predicate);
            System.out.println();

            num++;
        }
    }

    protected Schema instantiateSchema(String casestudy) {
        Schema schema;
        try {
            schema = (Schema) Class.forName(casestudy).newInstance();
            return schema;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void validateParameters() {
        // no params to validate
    }

    public static void main(String... args) {
        new PrintRequirements().run(args);
    }

}
