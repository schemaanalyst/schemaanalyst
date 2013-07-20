package org.schemaanalyst.util.exit;

/**
 * Manage system exits with objects so that they can be mocked
 * for testing  
 * @author phil
 *
 */

public interface ExitManager {
    
    public void exit(int code);
    
}
