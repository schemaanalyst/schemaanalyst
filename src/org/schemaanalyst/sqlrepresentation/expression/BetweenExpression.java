package org.schemaanalyst.sqlrepresentation.expression;

public class BetweenExpression extends ExpressionTree {

    public static final int NUM_SUBEXPRESSIONS = 3,
            SUBJECT = 0,
            LHS = 1,
            RHS = 2;
    protected Expression subject, lhs, rhs;
    protected boolean notBetween;

    public BetweenExpression(Expression subject, Expression lhs, Expression rhs, boolean notBetween) {
        this.subject = subject;
        this.lhs = lhs;
        this.rhs = rhs;
        this.notBetween = notBetween;
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

    public int getNumSubexpressions() {
        return NUM_SUBEXPRESSIONS;
    }

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

    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    public String toString() {
        return subject + " " + (notBetween ? "NOT " : "") + "BETWEEN " + lhs + " AND " + rhs;
    }
}
