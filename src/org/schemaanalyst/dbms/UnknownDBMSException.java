package org.schemaanalyst.dbms;

/**
 * <p>
 * An UnknownDBMSException is thrown when a {@link DBMS} class cannot be found 
 * matching a provided name.
 * </p>
 */
public class UnknownDBMSException extends RuntimeException {
    
    private static final long serialVersionUID = 5574371215668834818L;

    /**
     * Constructor.
     * 
     * @param message The descriptive message
     */
    public UnknownDBMSException(String message) {
        super(message);
    }
}
