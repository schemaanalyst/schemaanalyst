package org.schemaanalyst.util.exit;

public class ExceptionExit implements ExitManager {
    
    public void exit(int code) {
        throw new ExitException(code);
    }
}
