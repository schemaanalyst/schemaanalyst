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
	
	@Override
	public boolean equals(Object obj) {
		return artefact.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return artefact.hashCode();
	}
	
	public String toString() {
	    return description + " on " + artefact;
	}
}
