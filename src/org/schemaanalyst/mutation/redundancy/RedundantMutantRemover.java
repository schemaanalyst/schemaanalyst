/*
 */
package org.schemaanalyst.mutation.redundancy;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.equivalence.EquivalenceChecker;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import java.util.Iterator;
import org.schemaanalyst.util.DataCapturer;

/**
 * A {@link MutantRemover} that removes mutants equivalent to other mutants,
 * according to a provided {@link EquivalenceChecker}.
 *
 * @author Chris J. Wright
 * @param <T> The type of the artefact being mutated.
 */
public class RedundantMutantRemover<T> extends RedundantMutantDetector<T> {

    /**
     * Constructor.
     *
     * @param checker The equivalence checker
     */
    public RedundantMutantRemover(EquivalenceChecker<T> checker) {
        super(checker);
    }

    @Override
    public void process(Mutant<T> mutant, Iterator<Mutant<T>> it) {
        DataCapturer.capture("removedmutants", "redundant", mutant.getMutatedArtefact() + "-" + mutant.getSimpleDescription());
        it.remove();
    }
}
