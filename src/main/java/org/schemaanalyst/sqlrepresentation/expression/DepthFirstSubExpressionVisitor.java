package org.schemaanalyst.sqlrepresentation.expression;

/**
 * Created by phil on 14/08/2014.
 */
public class DepthFirstSubExpressionVisitor implements ExpressionVisitor {

    @Override
    public void visit(AndExpression expression) {
        visitSubexpressions(expression);
    }

    @Override
    public void visit(BetweenExpression expression) {
        visitSubexpressions(expression);
    }

    @Override
    public void visit(ColumnExpression expression) {
        visitSubexpressions(expression);
    }

    @Override
    public void visit(ConstantExpression expression) {
        visitSubexpressions(expression);
    }

    @Override
    public void visit(InExpression expression) {
        visitSubexpressions(expression);
    }

    @Override
    public void visit(ListExpression expression) {
        visitSubexpressions(expression);
    }

    @Override
    public void visit(NullExpression expression) {
        visitSubexpressions(expression);
    }

    @Override
    public void visit(OrExpression expression) {
        visitSubexpressions(expression);
    }

    @Override
    public void visit(ParenthesisedExpression expression) {
        visitSubexpressions(expression);
    }

    @Override
    public void visit(RelationalExpression expression) {
        visitSubexpressions(expression);
    }

    protected void visitSubexpressions(Expression expression) {
        for (Expression subexpression : expression.getSubexpressions()) {
            subexpression.accept(this);
        }
    }
}
