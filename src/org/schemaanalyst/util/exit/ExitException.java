package org.schemaanalyst.util.exit;

public class ExitException extends RuntimeException {

    private static final long serialVersionUID = 3767277591131712122L;

    protected int code;
    
    public ExitException(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
}
