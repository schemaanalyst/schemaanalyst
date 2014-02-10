package org.schemaanalyst.mutation.analysis.executor.exceptions;

public class DropStatementException extends StatementException {

    public DropStatementException(String message, String statement) {
        super(message, statement);
    }

}
