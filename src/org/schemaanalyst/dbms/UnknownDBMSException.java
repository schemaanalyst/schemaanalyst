package org.schemaanalyst.dbms;

public class UnknownDBMSException extends RuntimeException {
    
    private static final long serialVersionUID = 5574371215668834818L;

    public UnknownDBMSException(String message) {
        super(message);
    }
}
