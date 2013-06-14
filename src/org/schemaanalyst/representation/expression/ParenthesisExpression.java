package org.schemaanalyst.representation.expression;

public class ParenthesisExpression implements Expression {

	private static final long serialVersionUID = 8915820082142134422L;

	private Expression expression;
	
	public ParenthesisExpression(Expression expression) {
		this.expression = expression;
	}
	
	public Expression getExpression() {
		return expression;
	}
	
	public void accept(ExpressionVisitor visitor) {
		//visitor.visit(this);
	}
	
}
