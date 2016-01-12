package org.schemaanalyst.data.generation.search.objective.predicate;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.search.objective.MultiObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.AndPredicate;

/**
 * Created by phil on 24/07/2014.
 */
public class AndPredicateObjectiveFunction extends ComposedPredicateObjectiveFunction {

    public AndPredicateObjectiveFunction(AndPredicate predicate, Data state) {
        super(predicate, state);
    }

    @Override
    protected MultiObjectiveValue createObjectiveValue(String description) {
        return new SumOfMultiObjectiveValue(description);
    }
}
