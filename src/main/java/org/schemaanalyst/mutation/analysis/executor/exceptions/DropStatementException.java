package org.schemaanalyst.mutation.analysis.executor.exceptions;

/**
 * <p> A {@link RuntimeException} encountered when executing a {@code DROP} 
 * statement during a 
 * {@link org.schemaanalyst.testgeneration.TestCase}.</p>
 * 
 * @author Chris J. Wright
 */
public class DropStatementException extends StatementException {

    public DropStatementException(String message, String statement) {
        super(message, statement);
    }

}
