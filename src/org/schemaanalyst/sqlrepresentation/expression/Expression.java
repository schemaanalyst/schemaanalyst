package org.schemaanalyst.sqlrepresentation.expression;

public interface Expression {
	
	public void accept(ExpressionVisitor visitor);

}
