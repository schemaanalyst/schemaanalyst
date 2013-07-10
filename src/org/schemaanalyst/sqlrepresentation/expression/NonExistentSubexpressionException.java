package org.schemaanalyst.sqlrepresentation.expression;

@SuppressWarnings("serial")
public class NonExistentSubexpressionException extends RuntimeException {

	public NonExistentSubexpressionException(String message) {
		super(message);
	}
	
	public NonExistentSubexpressionException(ExpressionTree expression, int index) {
		super("No subexpression at index " + index + " for expression " + expression);
	}
}
