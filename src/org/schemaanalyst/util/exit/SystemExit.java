package org.schemaanalyst.util.exit;

public class SystemExit implements ExitManager {
    
    public void exit(int code) {
        System.exit(code);
    }
}
