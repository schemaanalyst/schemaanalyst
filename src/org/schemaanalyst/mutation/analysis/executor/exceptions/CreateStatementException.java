package org.schemaanalyst.mutation.analysis.executor.exceptions;

/**
 * <p>A {@link RuntimeException} encountered when executing a {@code CREATE} 
 * statement during a 
 * {@link org.schemaanalyst.testgeneration.TestCase}.</p>
 * 
 * @author Chris J. Wright
 */
public class CreateStatementException extends StatementException {

    public CreateStatementException(String message, String statement) {
        super(message, statement);
    }

}
