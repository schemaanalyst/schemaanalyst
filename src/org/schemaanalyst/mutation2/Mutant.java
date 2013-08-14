package org.schemaanalyst.mutation2;

public class Mutant<A> {

	private A artefact;
	private String operatorUsed;
	private String description;
	
	public Mutant(A artefact, String operatorUsed, String description) {
		this.artefact = artefact;
		this.operatorUsed = operatorUsed;
		this.description = description;
	}
	
	public A getMutatedArtefact() {
		return artefact;
	}

	public String getOperatorUsed() {
		return operatorUsed;
	}

	public String getDescription() {
		return description;
	}
}
