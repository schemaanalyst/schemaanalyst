package org.schemaanalyst.mutation;

import org.schemaanalyst.mutation.pipeline.MutantRemover;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Mutant} is a wrapper around a mutated object with a string description
 * of the mutation.
 *
 * @author Phil McMinn
 *
 * @param <A> The class of the mutated object.
 */
public class Mutant<A> {

    private final A artefact;
    private Integer identifier;
    private final String description;
    private String simpleDescription;
    private MutantProducer mutantProducer;
    private final List<MutantRemover> removersApplied;
    private MutantType mutantType;

    /**
     * Constructor
     *
     * @param artefact the mutated object.
     * @param description a string describing the mutation.
     */
    public Mutant(A artefact, String description) {
        this.artefact = artefact;
        this.description = description;
        removersApplied = new LinkedList<>();
        mutantType = MutantType.NORMAL;
    }

    /**
     * Returns the mutated object.
     *
     * @return the mutated object.
     */
    public A getMutatedArtefact() {
        return artefact;
    }

    /**
     * Gets a description of the mutation.
     *
     * @return A string description of the mutation.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a copy of the list of {@link MutantRemover} classes applied to
     * this mutant that have modified it with respect to the original artefact.
     *
     * @return The copied list
     */
    public List<MutantRemover> getRemoversApplied() {
        return new LinkedList<>(removersApplied);
    }

    /**
     * Adds a remover that has been applied to this mutant and has modified it
     * with respect to the original artefact.
     *
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

    /**
     * @return the identifier
     */
    public Integer getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(Integer identifier) {
        if (this.identifier != null) {
            Logger l = Logger.getLogger(Mutant.class.getName());
            l.log(Level.SEVERE, "Altering identifier of existing mutant. This "
                    + "may adversely affect the uniqueness of mutant identifiers"
                    + " and therefore cause mistakes in analysis of results.");
        }
        this.identifier = identifier;
    }

    /**
     * @return the mutantType
     */
    public MutantType getMutantType() {
        return mutantType;
    }

    /**
     * @param mutantType the mutantType to set
     */
    public void setMutantType(MutantType mutantType) {
        this.mutantType = mutantType;
    }
}
