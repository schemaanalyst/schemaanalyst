package org.schemaanalyst.representation.expression;

public class InExpression implements Expression {

	protected Expression lhs, rhs;
	protected boolean notIn;
	
	public InExpression(Expression lhs, Expression rhs, boolean notIn) {
		this.lhs = lhs;
		this.rhs = rhs;
		this.notIn = notIn;
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
