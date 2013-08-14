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

    public Expression getLHS() {
        return lhs;
    }

    public Expression getRHS() {
        return rhs;
    }

    public boolean isNotBetween() {
        return notBetween;
    }

    public boolean isSymmetric() {
        return symmetric;
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
        }
        throw new NonExistentSubexpressionException(this, index);
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
    public String toString() {
        return subject + " " + (notBetween ? "NOT " : "") + "BETWEEN " + lhs
                + " AND " + rhs;
    }
}
