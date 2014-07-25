package org.schemaanalyst.testgeneration.coveragecriterion.predicate;

/**
 * Created by phil on 23/07/2014.
 */
public interface PredicateVisitor {

    public void visit(AndPredicate predicate);

    public void visit(ExpressionPredicate predicate);

    public void visit(MatchPredicate predicate);

    public void visit(NullPredicate predicate);

    public void visit(OrPredicate predicate);
}
