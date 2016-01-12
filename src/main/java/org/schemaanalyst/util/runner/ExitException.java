package org.schemaanalyst.util.runner;

/**
 * To be used instead of System.exit() 
 * @author phil
 *
 */
public class ExitException extends RuntimeException {

    private static final long serialVersionUID = -3354718491536312974L;
    protected int code;
    
    public ExitException(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
}
