package org.schemaanalyst.sqlrepresentation.expression;

@SuppressWarnings("serial")
public class NonExistentSubexpressionException extends RuntimeException {

	public NonExistentSubexpressionException(Expression expression, int index) {
		super("No subexpression at index " + index + " for expression " + expression);
	}
}
