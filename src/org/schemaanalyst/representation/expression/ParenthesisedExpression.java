package org.schemaanalyst.representation.expression;

public class ParenthesisedExpression implements Expression {

	protected Expression subexpression;
	
	public ParenthesisedExpression(Expression subexpression) {
		this.subexpression = subexpression;
	}
	
	public Expression getSubexpression() {
		return subexpression;
	}

	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}	
}
