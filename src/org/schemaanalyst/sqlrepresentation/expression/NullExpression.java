package org.schemaanalyst.sqlrepresentation.expression;

import java.util.List;

public class NullExpression extends ExpressionTree {

    public static final int NUM_SUBEXPRESSIONS = 1,
            SUBEXPRESSION = 0;
    protected Expression subexpression;
    protected boolean notNull;

    public NullExpression(Expression subexpression, boolean notNull) {
        this.subexpression = subexpression;
        this.notNull = notNull;
    }

    public Expression getSubexpression() {
        return subexpression;
    }

    public boolean isNotNull() {
        return notNull;
    }

    @Override
    public int getNumSubexpressions() {
        return NUM_SUBEXPRESSIONS;
    }
    
    @Override
    public void setSubexpressions(List<Expression> subExpressions) {
        if (subExpressions.size() == 1) {
            subexpression = subExpressions.get(0);
        } else {
            throw new UnsupportedOperationException("NullExpression requires "
                    + "a list of 1 expression (subexpression).");
        }
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
        return subexpression + " IS " + (notNull ? "NOT" : "") + " NULL";
    }
}
