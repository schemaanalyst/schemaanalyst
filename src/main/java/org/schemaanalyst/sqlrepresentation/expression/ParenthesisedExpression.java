package org.schemaanalyst.sqlrepresentation.expression;

public class ParenthesisedExpression extends ExpressionTree {

    public static final int NUM_SUBEXPRESSIONS = 1, SUBEXPRESSION = 0;    
    private Expression subexpression;

    public ParenthesisedExpression(Expression subexpression) {
        this.subexpression = subexpression;
    }

    public Expression getSubexpression() {
        return subexpression;
    }

    public void setSubexpression(Expression subexpression) {
        this.subexpression = subexpression;
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
            default:
                throw new NonExistentSubexpressionException(this, index);
        }        
    }

    @Override
    public void setSubexpression(int index, Expression subexpression) {
        switch (index) {
            case SUBEXPRESSION:
                this.subexpression = subexpression;
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
    public ParenthesisedExpression duplicate() {
        return new ParenthesisedExpression(subexpression.duplicate());
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = ParenthesisedExpression.class.getName().hashCode();
        result = prime * result
                + ((subexpression == null) ? 0 : subexpression.hashCode());
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
        ParenthesisedExpression other = (ParenthesisedExpression) obj;
        if (subexpression == null) {
            if (other.subexpression != null)
                return false;
        } else if (!subexpression.equals(other.subexpression))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "(" + subexpression + ")";
    }
}
