package org.schemaanalyst.representation.expression;

public class InExpression implements Expression {

	protected Expression lhs, rhs;
	protected boolean notIn;
	
	public InExpression(Expression lhs, boolean notIn, Expression rhs) {
		this.lhs = lhs;
		this.notIn = notIn;
		this.rhs = rhs;
	}	
	
	public Expression getLHS() {
		return lhs;
	}
	
	public boolean isNotIn() {
		return notIn;
	}
	
	public Expression getRHS() {
		return rhs;
	}	

	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}	
	
}
