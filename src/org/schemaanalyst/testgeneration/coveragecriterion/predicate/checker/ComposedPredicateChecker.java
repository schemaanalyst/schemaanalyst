package org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.ComposedPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 13/10/2014.
 */
public abstract class ComposedPredicateChecker extends PredicateChecker {

    protected List<PredicateChecker> predicateCheckers;

    public ComposedPredicateChecker(ComposedPredicate composedPredicate, boolean allowNull, Data data) {
        this(composedPredicate, allowNull, data, new Data());
    }

    public ComposedPredicateChecker(ComposedPredicate composedPredicate, boolean allowNull, Data data, Data state) {
        predicateCheckers = new ArrayList<>();
        for (Predicate predicate : composedPredicate.getSubPredicates()) {
            predicateCheckers.add(PredicateCheckerFactory.instantiate(predicate, allowNull, data, state));
        }
    }

    public List<PredicateChecker> getPredicateCheckers() {
        return new ArrayList<>(predicateCheckers);
    }
}
