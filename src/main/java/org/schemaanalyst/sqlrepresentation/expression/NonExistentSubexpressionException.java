package org.schemaanalyst.sqlrepresentation.expression;

@SuppressWarnings("serial")
public class NonExistentSubexpressionException extends RuntimeException {

    public NonExistentSubexpressionException(Expression expression, int index) {
        super("Expression " + expression + " does not allow subexpression at index " + index);
    }
}
