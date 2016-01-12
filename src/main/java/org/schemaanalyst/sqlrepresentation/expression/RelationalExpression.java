package org.schemaanalyst.sqlrepresentation.expression;

import org.schemaanalyst.logic.RelationalOperator;

public class RelationalExpression extends ExpressionTree {

    public static final int NUM_SUBEXPRESSIONS = 2, LHS = 0, RHS = 1;
    private Expression lhs, rhs;
    private RelationalOperator op;

    public RelationalExpression(Expression lhs, RelationalOperator op,
            Expression rhs) {
        this.lhs = lhs;
        this.op = op;
        this.rhs = rhs;
    }

    public Expression getLHS() {
        return lhs;
    }
    
    public void setLHS(Expression lhs) {
        this.lhs = lhs;
    }

    public RelationalOperator getRelationalOperator() {
        return op;
    }
    
    public void setRelationalOperator(RelationalOperator op) {
        this.op = op;
    }

    public Expression getRHS() {
        return rhs;
    }
    
    public void setRHS(Expression rhs) {
        this.rhs = rhs;
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
    public RelationalExpression duplicate() {
        return new RelationalExpression(lhs.duplicate(), op, rhs.duplicate());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = RelationalExpression.class.getName().hashCode();
        result = prime * result + ((lhs == null) ? 0 : lhs.hashCode());
        result = prime * result + ((op == null) ? 0 : op.toString().hashCode());
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
        RelationalExpression other = (RelationalExpression) obj;
        if (lhs == null) {
            if (other.lhs != null)
                return false;
        } else if (!lhs.equals(other.lhs))
            return false;
        if (op != other.op)
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
        return lhs + " " + op + " " + rhs;
    }
}
