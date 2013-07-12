package org.schemaanalyst.sqlrepresentation.expression;

public class InExpression extends ExpressionTree {

    public static final int NUM_SUBEXPRESSIONS = 2,
            LHS = 0,
            RHS = 1;
    protected Expression lhs, rhs;
    protected boolean notIn;

    public InExpression(Expression lhs, Expression rhs, boolean notIn) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.notIn = notIn;
    }

    public Expression getLHS() {
        return lhs;
    }

    public boolean isNotIn() {
        return notIn;
    }

    public Expression getRHS() {
        return rhs;
    }

    public int getNumSubexpressions() {
        return NUM_SUBEXPRESSIONS;
    }

    public Expression getSubexpression(int index) {
        switch (index) {
            case LHS:
                return lhs;
            case RHS:
                return rhs;
        }
        throw new NonExistentSubexpressionException(this, index);
    }

    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    public String toString() {
        return lhs + (notIn ? "NOT " : "") + "IN " + rhs;
    }
}
