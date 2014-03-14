package org.schemaanalyst.mutation.analysis.executor.exceptions;

/**
 * <p> A {@link RuntimeException} encountered when executing an {@code INSERT} 
 * statement during a 
 * {@link org.schemaanalyst.testgeneration.TestCase}.</p>
 * 
 * @author Chris J. Wright
 */
public class InsertStatementException extends StatementException {

    public InsertStatementException(String message, String statement) {
        super(message, statement);
    }

}
