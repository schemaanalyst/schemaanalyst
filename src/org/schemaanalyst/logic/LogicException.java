package org.schemaanalyst.logic;

public class LogicException extends RuntimeException {
	
	private static final long serialVersionUID = 7123508705675558167L;

	/**
	 * Constructs the exception.
	 * @param message A message explaining the problem.
	 */
	public LogicException(String message) {
		super(message);
	}
}