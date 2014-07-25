package org.schemaanalyst.testgeneration.coveragecriterion.predicate;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by phil on 18/07/2014.
 */
public class AndPredicate extends ComposedPredicate {

    @Override
    public AndPredicate shallowDuplicate() {
        AndPredicate duplicate = new AndPredicate();
        duplicate.subPredicates = subPredicates;
        return duplicate;
    }

    @Override
    public boolean isInfeasible() {

        for (Predicate subPredicate : subPredicates) {

            if (subPredicate instanceof NullPredicate) {
                NullPredicate nullPredicate = (NullPredicate) subPredicate;

                NullPredicate inverseNullPredicate =
                        new NullPredicate(
                                nullPredicate.getTable(),
                                nullPredicate.getColumn(),
                                !nullPredicate.getTruthValue());

                if (subPredicates.contains(inverseNullPredicate)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void accept(PredicateVisitor predicateVisitor) {
        predicateVisitor.visit(this);
    }

    @Override
    public String toString() {
        return "(" + StringUtils.join(subPredicates, " \u2227 ") + ")";
    }
}
