package org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;

/**
 * Created by phil on 18/07/2014.
 */
public class APC extends IntegrityConstraintCriterion {

    public APC(Schema schema,
               TestRequirementIDGenerator testRequirementIDGenerator,
               ConstraintSupplier constraintSupplier) {
        super(schema, testRequirementIDGenerator, constraintSupplier);
    }

    public TestRequirements generateRequirements() {
        testRequirements = new TestRequirements();
        testRequirementIDGenerator.reset(schema.getName(), "schema");

        for (Table table : schema.getTables()) {
            testRequirementIDGenerator.reset(table.getName(), "table");
            generateRequirements(table, true);
            generateRequirements(table, false);
        }

        return testRequirements;
    }

    private void generateRequirements(Table table, boolean truthValue) {
        testRequirements.addTestRequirement(
                testRequirementIDGenerator.nextID(),
                "Acceptance predicate for " + table + " is " + truthValue,
                PredicateGenerator.generatePredicate(getConstraints(table), truthValue));
    }

}
