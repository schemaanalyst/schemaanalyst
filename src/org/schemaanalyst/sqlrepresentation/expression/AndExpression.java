package org.schemaanalyst.sqlrepresentation.expression;

public class AndExpression extends ComposedExpression {

	public AndExpression(Expression... subexpressions) {
		super(subexpressions);
	}
	
	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}
}
