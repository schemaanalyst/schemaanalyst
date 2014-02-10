
package org.schemaanalyst.mutation.analysis.executor.exceptions;

/**
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
