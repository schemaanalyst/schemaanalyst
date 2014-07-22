package org.schemaanalyst.testgeneration.criterion.integrityconstraint;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.testgeneration.criterion.predicate.ComposedPredicate;

/**
 * Created by phil on 18/07/2014.
 */
public class AICC extends ICC {

    public AICC(Schema schema) {
        super(schema);
    }

    protected void generateRequirements(Constraint constraint, boolean truthValue) {
        ComposedPredicate topLevelPredicate = PredicateGenerator.generateAcceptancePredicate(schema, constraint.getTable(), true, constraint);
        topLevelPredicate.addPredicate(PredicateGenerator.generateConditionPredicate(constraint, truthValue));

        addRequirement(
                idGenerator.nextID(),
                generateMessage(constraint) + " is " + truthValue,
                topLevelPredicate);
    }
}
