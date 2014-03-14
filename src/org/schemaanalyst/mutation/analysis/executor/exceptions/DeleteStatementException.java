package org.schemaanalyst.mutation.analysis.executor.exceptions;

/**
 * <p>A {@link RuntimeException} encountered when executing a {@code DELETE} 
 * statement during a 
 * {@link org.schemaanalyst.testgeneration.TestCase}.</p>
 * 
 * @author Chris J. Wright
 */
public class DeleteStatementException extends StatementException {

    public DeleteStatementException(String message, String statement) {
        super(message, statement);
    }

}
