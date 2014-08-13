package org.schemaanalyst.testgeneration.coveragecriterion.predicate;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

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
    public boolean isTriviallyInfeasible() {

        // reduce the predicate first
        Predicate reducedPredicate = reduce();

        if (reducedPredicate instanceof AndPredicate) {

            AndPredicate andPredicate = (AndPredicate) reducedPredicate;
            List<Predicate> subPredicates = andPredicate.getSubPredicates();

            for (Predicate subPredicate : subPredicates) {

                // the case where a sub-predicate is a NullPredicate that also exists in inverse form in the AndPredicate
                // e.g. ¬Null(c) ∧ Null(c)
                if (subPredicate instanceof NullPredicate) {
                    NullPredicate nullPredicate = (NullPredicate) subPredicate;
                    if (checkIfInverseNullPredicateExists(nullPredicate)) {
                        return true;
                    }
                }

                // the case where a sub-predicate is an OrPredicate and all the clauses of the Or
                // are NullPredicate that exist in inverse form in the main predicate
                // e.g. ¬Null(c1) ∧ ¬Null(c2) ∧ (Null(c1) ∨ Null(c2))

                if (subPredicate instanceof OrPredicate) {
                    OrPredicate orPredicate = (OrPredicate) subPredicate;

                    boolean allSubPredicatesInfeasible = true;
                    for (Predicate orPredicateSubPredicate : orPredicate.getSubPredicates()) {

                        if (orPredicateSubPredicate instanceof NullPredicate) {
                            NullPredicate nullPredicate = (NullPredicate) orPredicateSubPredicate;
                            if (!checkIfInverseNullPredicateExists(nullPredicate)) {
                                allSubPredicatesInfeasible = false;
                            }
                        } else {
                            allSubPredicatesInfeasible = false;
                        }
                    }

                    if (allSubPredicatesInfeasible) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean checkIfInverseNullPredicateExists(NullPredicate nullPredicate) {
        NullPredicate inverseNullPredicate =
                new NullPredicate(
                        nullPredicate.getTable(),
                        nullPredicate.getColumn(),
                        !nullPredicate.getTruthValue());

        if (subPredicates.contains(inverseNullPredicate)) {
            return true;
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
