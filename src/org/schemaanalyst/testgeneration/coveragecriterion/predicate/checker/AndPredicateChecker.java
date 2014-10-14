package org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.AndPredicate;

/**
 * Created by phil on 13/10/2014.
 */
public class AndPredicateChecker extends ComposedPredicateChecker {

    private AndPredicate andPredicate;

    public AndPredicateChecker(AndPredicate andPredicate, boolean allowNull, Data data) {
        this(andPredicate, allowNull, data, new Data());
    }

    public AndPredicateChecker(AndPredicate andPredicate, boolean allowNull, Data data, Data state) {
        super(andPredicate, allowNull, data, state);
        this.andPredicate = andPredicate;
    }

    @Override
    public AndPredicate getPredicate() {
        return andPredicate;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public boolean check() {
        boolean result = true;
        for (PredicateChecker predicateChecker : predicateCheckers) {
            if (!predicateChecker.check()) {
                result = false;
            }
        }
        return result;
    }
}
