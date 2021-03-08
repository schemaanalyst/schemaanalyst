package org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementDescriptor;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;

import static org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator.IDType.SCHEMA;
import static org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator.IDType.TABLE;

;

/**
 * Created by phil on 18/07/2014.
 */
public class ICC extends IntegrityConstraintCriterion {

    public ICC(Schema schema,
               TestRequirementIDGenerator testRequirementIDGenerator,
               ConstraintSupplier constraintSupplier) {
        super(schema, testRequirementIDGenerator, constraintSupplier);
    }

    public String getName() {
        return "ICC";
    }

    public TestRequirements generateRequirements() {
        testRequirements = new TestRequirements();
        testRequirementIDGenerator.reset(SCHEMA, schema.getName());

        for (Table table : schema.getTables()) {
            testRequirementIDGenerator.reset(TABLE, table.getName());

            for (Constraint constraint : getConstraints(table)) {
                generateRequirements(constraint, true);
                generateRequirements(constraint, false);
            }
        }
        return testRequirements;
    }

    protected void generateRequirements(Constraint constraint, boolean truthValue) {
        testRequirements.addTestRequirement(
                new TestRequirement(
                    new TestRequirementDescriptor(
                            testRequirementIDGenerator.nextID(),
                            generateMsg(constraint) + " is " + truthValue
                    ),
                    PredicateGenerator.generateConditionPredicate(constraint, truthValue),
                    truthValue,
                    doesRequirementRequiresComparisonRow(constraint)
                )
        );
    }

    protected String generateMsg(Constraint constraint) {
        return constraint + " for " + constraint.getTable();
    }

    protected boolean doesRequirementRequiresComparisonRow(Constraint constraint) {
        return constraint instanceof PrimaryKeyConstraint ||
                constraint instanceof UniqueConstraint;
    }
}
