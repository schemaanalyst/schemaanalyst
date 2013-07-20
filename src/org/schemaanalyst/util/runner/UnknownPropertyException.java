package org.schemaanalyst.util.runner;

public class UnknownPropertyException extends RuntimeException {
    
    private static final long serialVersionUID = 5833039265775477601L;

    public UnknownPropertyException(String message) {
        super(message);
    }
}
