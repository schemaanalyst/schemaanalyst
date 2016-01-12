package org.schemaanalyst.testgeneration.coveragecriterion.predicate;

/**
 * Created by phil on 24/07/2014.
 */
public class PredicateAdaptor implements PredicateVisitor {

    @Override
    public void visit(AndPredicate predicate) {
        for (Predicate subPredicate : predicate.getSubPredicates()) {
            subPredicate.accept(this);
        }
    }

    @Override
    public void visit(ExpressionPredicate predicate) {

    }

    @Override
    public void visit(MatchPredicate predicate) {

    }

    @Override
    public void visit(NullPredicate predicate) {

    }

    @Override
    public void visit(OrPredicate predicate) {
        for (Predicate subPredicate : predicate.getSubPredicates()) {
            subPredicate.accept(this);
        }
    }
}
