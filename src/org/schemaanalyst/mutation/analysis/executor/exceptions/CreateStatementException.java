package org.schemaanalyst.mutation.analysis.executor.exceptions;

public class CreateStatementException extends StatementException {

    public CreateStatementException(String message, String statement) {
        super(message, statement);
    }

}
