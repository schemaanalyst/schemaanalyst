package org.schemaanalyst.representation.expression;

public class AndExpression implements Expression {
	
	private static final long serialVersionUID = -4192182179363866488L;
	
	private Expression[] subExpressions;
	
	public AndExpression(Expression... subExpressions) {
		this.subExpressions = subExpressions;
	}
	
	public Expression[] getSubExpressions() {
		return subExpressions;
	}
	
	public void accept(ExpressionVisitor visitor) {
		//visitor.visit(this);
	}	
}
