package org.schemaanalyst.mutation.analysis.executor.exceptions;

public class InsertStatementException extends StatementException {

    public InsertStatementException(String message, String statement) {
        super(message, statement);
    }

}
