package org.schemaanalyst.mutation.redundancy;

import java.util.Iterator;
import java.util.List;

import org.schemaanalyst.mutation.Mutant;

/**
 * The {@link EquivalentMutantRemover} reduces the mutant list by removing
 * mutants that are identical to the original artefact, according to their 
 * equals method.
 *
 * @param <A> The class of the artefact being mutated.
 */
public class EquivalentMutantRemover<A> extends RedundantMutantRemover<A> {

    private A originalArtefact;

    /**
     * Constructor.
     *
     * @param originalArtefact the original artefact that was mutated.
     */
    public EquivalentMutantRemover(A originalArtefact) {
        this.originalArtefact = originalArtefact;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Mutant<A>> removeMutants(List<Mutant<A>> mutants) {
        for (Iterator<Mutant<A>> it = mutants.iterator(); it.hasNext();) {
            Mutant<A> mutant = it.next();
            if (mutant.getMutatedArtefact().equals(originalArtefact)) {
                it.remove();
            }
        }
        return mutants;
    }
}
