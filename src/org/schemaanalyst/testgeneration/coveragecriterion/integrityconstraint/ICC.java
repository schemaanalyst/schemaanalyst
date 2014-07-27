package org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;

/**
 * Created by phil on 18/07/2014.
 */
public class ICC extends IntegrityConstraintCriterion {

    public ICC(Schema schema,
               TestRequirementIDGenerator testRequirementIDGenerator,
               ConstraintSupplier constraintSupplier) {
        super(schema, testRequirementIDGenerator, constraintSupplier);
    }

    public TestRequirements generateRequirements() {
        testRequirements = new TestRequirements();
        testRequirementIDGenerator.reset(schema.getName(), "schema");

        for (Table table : schema.getTables()) {
            testRequirementIDGenerator.reset(table.getName(), "table");

            for (Constraint constraint : getConstraints(table)) {
                generateRequirements(constraint, true);
                generateRequirements(constraint, false);
            }
        }
        return testRequirements;
    }

    protected void generateRequirements(Constraint constraint, boolean truthValue) {
        testRequirements.addTestRequirement(
                testRequirementIDGenerator.nextID(),
                generateMsg(constraint) + " is " + truthValue,
                PredicateGenerator.generateConditionPredicate(constraint, truthValue));
    }

    protected String generateMsg(Constraint constraint) {
        return constraint + " for " + constraint.getTable();
    }
}
