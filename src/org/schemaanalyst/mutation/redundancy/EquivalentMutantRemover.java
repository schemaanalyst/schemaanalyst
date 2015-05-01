package org.schemaanalyst.mutation.redundancy;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.equivalence.EquivalenceChecker;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import org.schemaanalyst.util.DataCapturer;
import java.util.Iterator;

/**
 * A {@link MutantRemover} that removes mutants equivalent to the original
 * artefact, according to a provided {@link EquivalenceChecker}.
 *
 * @author Chris J. Wright
 * @param <T> The type of the artefact being mutated.
 */
public class EquivalentMutantRemover<T> extends EquivalentMutantDetector<T> {

    /**
     * Constructor.
     *
     * @param checker The equivalence checker
     * @param originalArtefact The original artefact that was mutated
     */
    public EquivalentMutantRemover(EquivalenceChecker<T> checker, T originalArtefact) {
        super(checker, originalArtefact);
    }

    @Override
    public void process(Mutant<T> mutant, Iterator<Mutant<T>> it) {
        DataCapturer.capture("removedmutants", "equivalent", mutant.getMutatedArtefact() + "-" + mutant.getSimpleDescription());
        it.remove();
    }

}
