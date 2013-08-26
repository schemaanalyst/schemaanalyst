package org.schemaanalyst.mutation;

/**
 * {@link Mutant} is a wrapper around a mutated object with a string 
 * description of the mutation.
 * 
 * @author Phil McMinn
 *
 * @param <A> The class of the mutated object.
 */
public class Mutant<A> {

	private A artefact;
	private String description;
	
	/**
	 * Constructor
	 * @param artefact the mutated object.
	 * @param description a string describing the mutation.
	 */
	public Mutant(A artefact, String description) {
		this.artefact = artefact;
		this.description = description;
	}
	
	/**
	 * Returns the mutated object.
	 * @return the mutated object.
	 */
	public A getMutatedArtefact() {
		return artefact;
	}
	
	/**
	 * Gets a description of the mutation.
	 * @return A string description of the mutation.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override	
	public boolean equals(Object obj) {
		return artefact.equals(obj);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return artefact.hashCode();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
	    return description + " on " + artefact;
	}
}
