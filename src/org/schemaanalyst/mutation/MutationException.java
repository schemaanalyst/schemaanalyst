package org.schemaanalyst.mutation;

public class MutationException extends RuntimeException {

	private static final long serialVersionUID = -8531222484286356117L;

	public MutationException(String message) {
		super(message);
	}
	
	public MutationException(Exception e) {
		super(e);
	}	

}
