package org.schemaanalyst.sqlrepresentation.expression;

public class NullExpression extends ExpressionTree {

    public static final int NUM_SUBEXPRESSIONS = 1, SUBEXPRESSION = 0;
    private Expression subexpression;
    private boolean notNull;

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

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
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
    public NullExpression duplicate() {
        return new NullExpression(subexpression.duplicate(), notNull);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = NullExpression.class.getName().hashCode();
        result = prime * result + (notNull ? 1231 : 1237);
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
        NullExpression other = (NullExpression) obj;
        if (notNull != other.notNull)
            return false;
        if (subexpression == null) {
            if (other.subexpression != null)
                return false;
        } else if (!subexpression.equals(other.subexpression))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return subexpression + " IS " + (notNull ? "NOT" : "") + " NULL";
    }
}
