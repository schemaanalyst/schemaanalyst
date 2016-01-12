package org.schemaanalyst.sqlrepresentation.expression;

public interface ExpressionVisitor {

    public void visit(AndExpression expression);

    public void visit(BetweenExpression expression);

    public void visit(ColumnExpression expression);

    public void visit(ConstantExpression expression);

    public void visit(InExpression expression);

    public void visit(ListExpression expression);

    public void visit(NullExpression expression);

    public void visit(OrExpression expression);

    public void visit(ParenthesisedExpression expression);

    public void visit(RelationalExpression expression);
}
