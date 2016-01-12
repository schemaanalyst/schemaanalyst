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
            default:
                throw new NonExistentSubexpressionException(this, index);                
        }
    }

    @Override
    public void setSubexpression(int index, Expression subexpression) {
        switch (index) {
            case LHS:
                lhs = subexpression;
                break;
            case RHS:
                rhs = subexpression;
                break;
            default:
                throw new NonExistentSubexpressionException(this, index);                
        }        
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
    public int hashCode() {
        final int prime = 31;
        int result = InExpression.class.getName().hashCode();
        result = prime * result + ((lhs == null) ? 0 : lhs.hashCode());
        result = prime * result + (notIn ? 1231 : 1237);
        result = prime * result + ((rhs == null) ? 0 : rhs.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        InExpression other = (InExpression) obj;
        if (lhs == null) {
            if (other.lhs != null)
                return false;
        } else if (!lhs.equals(other.lhs))
            return false;
        if (notIn != other.notIn)
            return false;
        if (rhs == null) {
            if (other.rhs != null)
                return false;
        } else if (!rhs.equals(other.rhs))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return lhs + (notIn ? " NOT" : "") + " IN " + rhs;
    }
}
