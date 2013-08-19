package org.schemaanalyst.mutation;

public class Mutant<A> {

	private A artefact;
	private String description;
	
	public Mutant(A artefact, String description) {
		this.artefact = artefact;
		this.description = description;
	}
	
	public A getMutatedArtefact() {
		return artefact;
	}

	public String getDescription() {
		return description;
	}
	
	public String toString() {
	    return description + " " + artefact;
	}
}
