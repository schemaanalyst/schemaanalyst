package org.schemaanalyst.util.runner;

/**
 * Thrown when the arguments passed to a Runner are not passed 
 * as expected as dictated by its required and optional properties
 * @author phil
 *
 */
public class ArgumentException extends RuntimeException {

    private static final long serialVersionUID = -9114017243585430680L;

    public ArgumentException(String message) {
        super(message);
    }
}
