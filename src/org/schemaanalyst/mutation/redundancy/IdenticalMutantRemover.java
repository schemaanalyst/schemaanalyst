/*
 */
package org.schemaanalyst.mutation.redundancy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutantRemover;

/**
 * The {@link IdenticalMutantRemover} reduces the mutant list by removing
 * mutants that are identical to each other, according to their equals method.
 *
 * @param <A> The class of the artefact being mutated.
 */
public class IdenticalMutantRemover<A> extends MutantRemover<A> {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Mutant<A>> removeMutants(List<Mutant<A>> mutants) {
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
