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

/**
 * Created by phil on 18/07/2014.
 */
public class APC extends IntegrityConstraintCriterion {

    public APC(Schema schema,
               TestRequirementIDGenerator testRequirementIDGenerator,
               ConstraintSupplier constraintSupplier) {
        super(schema, testRequirementIDGenerator, constraintSupplier);
    }

    public String getName() {
        return "APC";
    }

    public TestRequirements generateRequirements() {
        testRequirements = new TestRequirements();
        testRequirementIDGenerator.reset(SCHEMA, schema.getName());

        for (Table table : schema.getTables()) {
            if (schema.getNumConstraints(table) > 0) {
                testRequirementIDGenerator.reset(TABLE, table.getName());
                generateRequirements(table, true);
                generateRequirements(table, false);
            }
        }

        return testRequirements;
    }

    private void generateRequirements(Table table, boolean truthValue) {
        testRequirements.addTestRequirement(
                new TestRequirement(
                        new TestRequirementDescriptor(
                                testRequirementIDGenerator.nextID(),
                                "Acceptance predicate for " + table + " is " + truthValue
                        ),
                        PredicateGenerator.generatePredicate(getConstraints(table), truthValue),
                        truthValue,
                        doesRequirementRequiresComparisonRow(table)
                )
        );
    }

    protected boolean doesRequirementRequiresComparisonRow(Table table) {
        for (Constraint constraint : schema.getConstraints(table)) {
            if (constraint instanceof PrimaryKeyConstraint || constraint instanceof UniqueConstraint) {
                return true;
            }
        }
        return false;
    }

}
