package org.schemaanalyst.mutation;

import org.schemaanalyst.mutation.pipeline.MutantRemover;

import java.util.LinkedList;
import java.util.List;

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
        private String simpleDescription;
    private MutantProducer mutantProducer;
    private List<MutantRemover> removersApplied;
	
	/**
	 * Constructor
	 * @param artefact the mutated object.
	 * @param description a string describing the mutation.
	 */
	public Mutant(A artefact, String description) {
		this.artefact = artefact;
		this.description = description;
        removersApplied = new LinkedList<>();
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
     * Returns a copy of the list of {@link MutantRemover} classes applied to 
     * this mutant that have modified it with respect to the original artefact.
     * @return The copied list
     */
    public List<MutantRemover> getRemoversApplied() {
        return new LinkedList<>(removersApplied);
    }
    
    /**
     * Adds a remover that has been applied to this mutant and has modified it 
     * with respect to the original artefact.
     * @param remover The remover
     */
    public void addRemoverApplied(MutantRemover<A> remover) {
        removersApplied.add(remover);
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

    /**
     * @return the mutantProducer
     */
    public MutantProducer getMutantProducer() {
        return mutantProducer;
    }

    /**
     * @param mutantProducer the mutantProducer to set
     */
    public void setMutantProducer(MutantProducer mutantProducer) {
        this.mutantProducer = mutantProducer;
    }

    public String getSimpleDescription() {
        return simpleDescription;
    }

    public void setSimpleDescription(String simpleDescription) {
        this.simpleDescription = simpleDescription;
    }
}
