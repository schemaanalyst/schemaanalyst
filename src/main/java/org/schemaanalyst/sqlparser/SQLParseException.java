package org.schemaanalyst.sqlparser;

/**
 * <p>
 * A runtime exception caused while attempting to parse SQL.
 * </p>
 */
@SuppressWarnings("serial")
public class SQLParseException extends RuntimeException {

    public SQLParseException(String message) {
        super(message);
    }
}
