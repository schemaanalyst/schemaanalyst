package org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.OrPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;

/**
 * Created by phil on 13/10/2014.
 */
public class OrPredicateChecker extends ComposedPredicateChecker {

    private OrPredicate orPredicate;

    public OrPredicateChecker(OrPredicate orPredicate, boolean allowNull, Data data, Data state) {
        super(orPredicate, allowNull, data, state);
        this.orPredicate = orPredicate;
    }

    @Override
    public Predicate getPredicate() {
        return orPredicate;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public boolean check() {
        boolean result = false;
        for (PredicateChecker predicateChecker : predicateCheckers) {
            if (predicateChecker.check()) {
                result = true;
            }
        }
        return result;
    }
}
