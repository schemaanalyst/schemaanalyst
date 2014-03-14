
package org.schemaanalyst.mutation.analysis.executor.exceptions;

/**
 * <p> A {@link RuntimeException} encountered when executing an {@code SQL} 
 * statement during a 
 * {@link org.schemaanalyst.testgeneration.TestCase}.</p>
 * 
 * @author Chris J. Wright
 */
public class StatementException extends TestCaseException {
    protected final String statement;

    public StatementException(String message, String statement) {
        super(message);
        this.statement = statement;
    }

    public String getStatement() {
        return statement;
    }
    
}
