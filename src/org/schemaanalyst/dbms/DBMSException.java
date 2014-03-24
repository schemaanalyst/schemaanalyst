package org.schemaanalyst.dbms;

/**
 * <p>
 * An DBMSException is thrown when a {@link DBMS} class cannot be found
 * matching a provided name.
 * </p>
 */
public class DBMSException extends RuntimeException {
    
    private static final long serialVersionUID = 5574371215668834818L;

    /**
     * Constructor.
     * 
     * @param message The descriptive message
     */
    public DBMSException(String message) {
        super(message);
    }
}
