
package org.schemaanalyst.mutation.analysis.executor.exceptions;

/**
 * <p>A {@link RuntimeException} encountered when executing a 
 * {@link org.schemaanalyst.testgeneration.TestCase}.</p>
 * 
 * @author Chris J. Wright
 */
public class TestCaseException extends RuntimeException {

    public TestCaseException(String message) {
        super(message);
    }
    
}
