
package org.schemaanalyst.util.monitoring;

import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.Callable;

/**
 * Utility functions for timing operations.
 * 
 * @author Chris J. Wright
 */
public class Timing {
    
    /**
     * Times the duration it takes for a callable to be executed using a StopWatch.
     * 
     * @param <T> The callable return type
     * @param callable The callable
     * @param watch The stopwatch
     * @return The callable return
     */
    public static <T> T timedTask(Callable<T> callable, StopWatch watch) {
        try {
            watch.start();
            T result = callable.call();
            watch.stop();
            return result;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Times the duration it takes for a runnable to be executed using a StopWatch.
     * 
     * @param runnable The runnable
     * @param watch The stopwatch
     */
    public static void timedTask(Runnable runnable, StopWatch watch) {
        try {
            watch.start();
            runnable.run();
            watch.stop();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
}
