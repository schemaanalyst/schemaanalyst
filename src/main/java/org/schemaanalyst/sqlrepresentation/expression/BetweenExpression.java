package org.schemaanalyst.sqlrepresentation.expression;

public class BetweenExpression extends ExpressionTree {

    public static final int NUM_SUBEXPRESSIONS = 3, SUBJECT = 0, LHS = 1, RHS = 2;
    private Expression subject, lhs, rhs;
    private boolean notBetween, symmetric;

    public BetweenExpression(Expression subject, Expression lhs,
            Expression rhs, boolean notBetween, boolean symmetric) {
        this.subject = subject;
        this.lhs = lhs;
        this.rhs = rhs;
        this.notBetween = notBetween;
        this.symmetric = symmetric;
    }

    public Expression getSubject() {
        return subject;
    }

    public void setSubject(Expression subject) {
        this.subject = subject;
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

    public boolean isNotBetween() {
        return notBetween;
    }

    public void setNotBetween(boolean notBetween) {
        this.notBetween = notBetween;
    }
    
    public boolean isSymmetric() {
        return symmetric;
    }

    public void setSymmetric(boolean symmetric) {
        this.symmetric = symmetric;
    }
    
    @Override
    public int getNumSubexpressions() {
        return NUM_SUBEXPRESSIONS;
    }

    @Override
    public Expression getSubexpression(int index) {
        switch (index) {
            case SUBJECT:
                return subject;
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
            case SUBJECT:
                subject = subexpression;
                break;                
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
    public BetweenExpression duplicate() {
        return new BetweenExpression(
                subject.duplicate(), lhs.duplicate(), rhs.duplicate(), 
                notBetween, symmetric);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = BetweenExpression.class.getName().hashCode();
        result = prime * result + ((lhs == null) ? 0 : lhs.hashCode());
        result = prime * result + (notBetween ? 1231 : 1237);
        result = prime * result + ((rhs == null) ? 0 : rhs.hashCode());
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        result = prime * result + (symmetric ? 1231 : 1237);
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
        BetweenExpression other = (BetweenExpression) obj;
        if (lhs == null) {
            if (other.lhs != null)
                return false;
        } else if (!lhs.equals(other.lhs))
            return false;
        if (notBetween != other.notBetween)
            return false;
        if (rhs == null) {
            if (other.rhs != null)
                return false;
        } else if (!rhs.equals(other.rhs))
            return false;
        if (subject == null) {
            if (other.subject != null)
                return false;
        } else if (!subject.equals(other.subject))
            return false;
        return symmetric == other.symmetric;
    }

    @Override
    public String toString() {
        return subject + " " + (notBetween ? "NOT " : "") + "BETWEEN " + lhs
                + " AND " + rhs;
    }
}
