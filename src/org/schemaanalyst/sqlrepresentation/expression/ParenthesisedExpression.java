package org.schemaanalyst.sqlrepresentation.expression;

public class ParenthesisedExpression extends ExpressionTree {

    public static final int NUM_SUBEXPRESSIONS = 1,
            SUBEXPRESSION = 0;
    protected Expression subexpression;

    public ParenthesisedExpression(Expression subexpression) {
        this.subexpression = subexpression;
    }

    public Expression getSubexpression() {
        return subexpression;
    }

    @Override
    public int getNumSubexpressions() {
        return NUM_SUBEXPRESSIONS;
    }

    @Override
    public Expression getSubexpression(int index) {
        switch (index) {
            case SUBEXPRESSION:
                return subexpression;
        }
        throw new NonExistentSubexpressionException(this, index);
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "(" + subexpression + ")";
    }
}
