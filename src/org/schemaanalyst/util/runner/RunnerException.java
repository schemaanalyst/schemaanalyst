package org.schemaanalyst.util.runner;

public class RunnerException extends Error {

    private static final long serialVersionUID = 4336258771607525426L;

    public RunnerException(String message) {
        super(message);
    }    
    
    public RunnerException(String message, Throwable e) {
        super(message, e);
    }    
}
