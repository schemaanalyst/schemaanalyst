package org.schemaanalyst.sqlrepresentation.expression;

public class NullExpression implements Expression {
	
	protected Expression subexpression;
	protected boolean notNull;
	
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

	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}	
}
