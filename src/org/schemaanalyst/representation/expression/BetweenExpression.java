package org.schemaanalyst.representation.expression;

public class BetweenExpression implements Expression {
	
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
	
	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}	
}
