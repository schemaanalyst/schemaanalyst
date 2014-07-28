package org.schemaanalyst.data.generation.search.objective.predicate;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.MultiObjectiveValue;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.OrPredicate;

/**
 * Created by phil on 24/07/2014.
 */
public class OrPredicateObjectiveFunction extends ComposedPredicateObjectiveFunction {

    public OrPredicateObjectiveFunction(OrPredicate predicate, Data state) {
        super(predicate, state);
    }

    @Override
    protected MultiObjectiveValue createObjectiveValue(String description) {
        return new BestOfMultiObjectiveValue(description);
    }
}
