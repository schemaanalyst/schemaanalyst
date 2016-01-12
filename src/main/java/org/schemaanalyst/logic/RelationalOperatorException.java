package org.schemaanalyst.logic;

/**
 * <p>
 * An exception caused when an error is encountered with a 
 * {@link RelationalOperator}.
 * </p>
 */
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