/*
 */
package org.schemaanalyst.mutation.equivalence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.schemaanalyst.mutation.Mutant;

/**
 * The {@link RedundantMutantReducer} reduces the mutant list by removing
 * mutants that are identical to each other, according to their equals method.
 *
 * @param <A> The class of the artefact being mutated.
 */
public class RedundantMutantReducer<A> extends EquivalenceReducer<A> {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Mutant<A>> reduce(List<Mutant<A>> mutants) {
        List<Mutant<A>> result = new ArrayList<>();
        Set<A> mutantSet = new HashSet<>();
        for (Mutant<A> mutant : mutants) {
            A mutantArtifact = mutant.getMutatedArtefact();
            if (mutantSet.add(mutantArtifact)) {
                result.add(mutant);
            }
        }
        return result;
    }
}
