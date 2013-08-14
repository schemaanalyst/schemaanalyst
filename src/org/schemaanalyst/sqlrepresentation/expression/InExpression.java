package org.schemaanalyst.sqlrepresentation.expression;

public class InExpression extends ExpressionTree {

    public static final int NUM_SUBEXPRESSIONS = 2, LHS = 0, RHS = 1;
    private Expression lhs, rhs;
    private boolean notIn;

    public InExpression(Expression lhs, Expression rhs, boolean notIn) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.notIn = notIn;
    }

    public Expression getLHS() {
        return lhs;
    }

    public void setLHS(Expression lhs) {
        this.lhs = lhs;
    }
    
    public Expression getRHS() {
        return rhs;
    }

    public void setRHS(Expression rhs) {
        this.rhs = rhs;
    }
    
    public boolean isNotIn() {
        return notIn;
    }
    
    public void setNotIn(boolean notIn) {
        this.notIn = notIn;
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
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public InExpression duplicate() {
        return new InExpression(lhs.duplicate(), rhs.duplicate(), notIn);
    }
    
    @Override
    public String toString() {
        return lhs + (notIn ? " NOT" : "") + " IN " + rhs;
    }
}
