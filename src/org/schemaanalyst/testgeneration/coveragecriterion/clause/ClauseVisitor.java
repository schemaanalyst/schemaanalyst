package org.schemaanalyst.testgeneration.coveragecriterion.clause;

/**
 * Created by phil on 31/01/2014.
 */
public interface ClauseVisitor {

    public void visit(ExpressionClause clause);

    public void visit(MatchClause clause);

    public void visit(NullClause clause);
}
