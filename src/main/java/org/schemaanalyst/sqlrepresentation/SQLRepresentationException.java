package org.schemaanalyst.sqlrepresentation;

/**
 * An exception thrown when SQL representation objects have been carelessly 
 * constructed (e.g. the number of foreign key columns in the original and reference table 
 * do not match).
 *
 * @author Phil McMinn
 *
 */
public class SQLRepresentationException extends RuntimeException {

    private static final long serialVersionUID = -1938527440146240474L;

    /**
     * Constructs the exception.
     * @param message A message explaining the problem.
     */
    public SQLRepresentationException(String message) {
        super(message);
    }
}
