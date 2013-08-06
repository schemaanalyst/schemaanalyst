package org.schemaanalyst.sqlrepresentation.expression;

import java.util.List;

public class InExpression extends ExpressionTree {

    public static final int NUM_SUBEXPRESSIONS = 2, LHS = 0, RHS = 1;
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

    @Override
    public int getNumSubexpressions() {
        return NUM_SUBEXPRESSIONS;
    }

    @Override
    public void setSubexpressions(List<Expression> subExpressions) {
        if (subExpressions.size() == 2) {
            lhs = subExpressions.get(0);
            rhs = subExpressions.get(1);
        } else {
            throw new UnsupportedOperationException("InExpression requires "
                    + "a list of 2 expressions (lhs, rhs).");
        }
    }
    
    @Override
    public Expression getSubexpression(int index) {
        switch (index) {
            case LHS:
                return lhs;
            case RHS:
                return rhs;
        }
        throw new NonExistentSubexpressionException(this, index);
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return lhs + (notIn ? " NOT" : "") + " IN " + rhs;
    }
}
