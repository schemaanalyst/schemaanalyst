package org.schemaanalyst.mutation.redundancy;

import java.util.Iterator;
import java.util.List;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutantRemover;

/**
 * A {@link MutantRemover} that removes mutants equivalent to the original 
 * artefact, according to a provided {@link EquivalenceTester}.
 * 
 * @author Chris J. Wright
 * @param <T> The type of the artefact being mutated.
 */
public class MutantEquivalentToOriginalRemover<T> extends EquivalenceTesterMutantRemover<T> {
    
    private T originalArtefact;

    /**
     * Constructor.
     * 
     * @param tester The equivalence tester
     * @param originalArtefact The original artefact that was mutated
     */
    public MutantEquivalentToOriginalRemover(EquivalenceTester<T> tester, T originalArtefact) {
        super(tester);
        this.originalArtefact = originalArtefact;
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public List<Mutant<T>> removeMutants(List<Mutant<T>> mutants) {
        for (Iterator<Mutant<T>> it = mutants.iterator(); it.hasNext();) {
            Mutant<T> mutant = it.next();
            if (tester.areEquivalent(originalArtefact, mutant.getMutatedArtefact())) {
                it.remove();
            }
        }
        return mutants;
    }
    
}
