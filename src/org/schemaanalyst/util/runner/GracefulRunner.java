package org.schemaanalyst.util.runner;

import org.schemaanalyst.util.exit.ExceptionExit;

/**
 * A version of Runner that will not exit the JVM, allowing
 * several Runners to be chained together without fear of
 * later Runners in the process not running due to an
 * earlier crash.
 * @author phil
 *
 */
public abstract class GracefulRunner extends Runner {
    
    public GracefulRunner() {
        exitManager = new ExceptionExit();
    }
    
    public void run(String... args) {
        try {
            super.run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
