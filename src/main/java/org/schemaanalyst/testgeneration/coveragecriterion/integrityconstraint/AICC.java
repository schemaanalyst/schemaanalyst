package org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementDescriptor;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.ComposedPredicate;

/**
 * Created by phil on 18/07/2014.
 */
public class AICC extends ICC {

    public AICC(Schema schema,
                TestRequirementIDGenerator testRequirementIDGenerator,
                ConstraintSupplier constraintSupplier) {
        super(schema, testRequirementIDGenerator, constraintSupplier);
    }

    public String getName() {
        return "AICC";
    }

    protected void generateRequirements(Constraint constraint, boolean truthValue) {
        ComposedPredicate topLevelPredicate = PredicateGenerator.generatePredicate(getConstraints(constraint.getTable()), constraint);
        topLevelPredicate.addPredicate(PredicateGenerator.generateConditionPredicate(constraint, truthValue));

        testRequirements.addTestRequirement(
                new TestRequirement(
                        new TestRequirementDescriptor(
                            testRequirementIDGenerator.nextID(),
                            generateMsg(constraint) + " is " + truthValue
                        ),
                        topLevelPredicate,
                        truthValue,
                        doesRequirementRequiresComparisonRow(constraint)
                )
        );
    }
}
