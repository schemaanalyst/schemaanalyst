package org.schemaanalyst.mutation.equivalence;

import java.util.Iterator;
import java.util.List;

import org.schemaanalyst.mutation.Mutant;

/**
 * The {@link OriginalEquivalenceReducer} reduces the mutant list by removing
 * mutants that are identical to the original artefact, according to their 
 * equals method.
 *
 * @param <A> The class of the artefact being mutated.
 */
public class OriginalEquivalenceReducer<A> extends EquivalenceReducer<A> {

    private A originalArtefact;

    /**
     * Constructor.
     *
     * @param originalArtefact the original artefact that was mutated.
     */
    public OriginalEquivalenceReducer(A originalArtefact) {
        this.originalArtefact = originalArtefact;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Mutant<A>> reduce(List<Mutant<A>> mutants) {
        for (Iterator<Mutant<A>> it = mutants.iterator(); it.hasNext();) {
            Mutant<A> mutant = it.next();
            if (mutant.getMutatedArtefact().equals(originalArtefact)) {
                it.remove();
            }
        }
        return mutants;
    }
}
