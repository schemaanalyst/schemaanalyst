package org.schemaanalyst.sqlrepresentation.expression;

import java.util.List;
import org.schemaanalyst.logic.RelationalOperator;

public class RelationalExpression extends ExpressionTree {

    public static final int NUM_SUBEXPRESSIONS = 2, LHS = 0, RHS = 1;
    protected Expression lhs, rhs;
    protected RelationalOperator op;

    public RelationalExpression(Expression lhs, RelationalOperator op,
            Expression rhs) {
        this.lhs = lhs;
        this.op = op;
        this.rhs = rhs;
    }

    public Expression getLHS() {
        return lhs;
    }

    public RelationalOperator getRelationalOperator() {
        return op;
    }

    public Expression getRHS() {
        return rhs;
    }

    @Override
    public int getNumSubexpressions() {
        return NUM_SUBEXPRESSIONS;
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
    public void setSubexpressions(List<Expression> subExpressions) {
        if (subExpressions.size() == 1) {
            lhs = subExpressions.get(0);
            rhs = subExpressions.get(1);
        } else {
            throw new UnsupportedOperationException("RelationalExpression requires "
                    + "a list of 2 expressions (lhs, rhs).");
        }
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return lhs + " " + op + " " + rhs;
    }
}
