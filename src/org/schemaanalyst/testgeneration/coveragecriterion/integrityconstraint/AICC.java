package org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.ComposedPredicate;

/**
 * Created by phil on 18/07/2014.
 */
public class AICC extends ICC {

    public AICC(Schema schema, TestRequirementIDGenerator trIDGenerator) {
        super(schema, trIDGenerator);
    }

    protected void generateRequirements(Constraint constraint, boolean truthValue) {
        ComposedPredicate topLevelPredicate = PredicateGenerator.generateAcceptancePredicate(schema, constraint.getTable(), true, constraint);
        topLevelPredicate.addPredicate(PredicateGenerator.generateConditionPredicate(constraint, truthValue));

        tr.addTestRequirement(
                trIDGenerator.nextID(),
                generateMsg(constraint) + " is " + truthValue,
                topLevelPredicate);
    }
}
