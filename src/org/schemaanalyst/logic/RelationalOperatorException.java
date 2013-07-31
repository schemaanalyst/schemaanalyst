package org.schemaanalyst.logic;

public class RelationalOperatorException extends RuntimeException {

    private static final long serialVersionUID = 7123508705675558167L;

    /**
     * Constructs the exception.
     *
     * @param message A message explaining the problem.
     */
    public RelationalOperatorException(String message) {
        super(message);
    }
}