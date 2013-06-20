package org.schemaanalyst.sqlrepresentation.expression;

public class OrExpression extends ComposedExpression {

	public OrExpression(Expression... subexpressions) {
		super(subexpressions);
	}
	
	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}	
}
