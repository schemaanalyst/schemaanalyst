package org.schemaanalyst.sqlparser;

@SuppressWarnings("serial")
public class SQLParseException extends RuntimeException {
	
	public SQLParseException(String message) {
		super(message);
	}
}
