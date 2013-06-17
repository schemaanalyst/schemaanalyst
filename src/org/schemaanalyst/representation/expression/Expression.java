package org.schemaanalyst.representation.expression;

public interface Expression {

	public void accept(ExpressionVisitor visitor);
	
}
