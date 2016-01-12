/*
 */
package org.schemaanalyst.mutation.redundancy;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.equivalence.EquivalenceChecker;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import java.util.Iterator;
import org.schemaanalyst.mutation.MutantType;
import org.schemaanalyst.util.DataCapturer;

/**
 * A {@link MutantRemover} that classifies mutants equivalent to other mutants,
 * according to a provided {@link EquivalenceChecker}, but does not actually
 * remove them.
 *
 * @author Chris J. Wright
 * @param <T> The type of the artefact being mutated.
 */
public class RedundantMutantClassifier<T> extends RedundantMutantDetector<T> {

    /**
     * Constructor.
     *
     * @param checker The equivalence checker
     */
    public RedundantMutantClassifier(EquivalenceChecker<T> checker) {
        super(checker);
    }

    @Override
    public void process(Mutant<T> mutant, Iterator<Mutant<T>> it) {
        if (mutant.getMutantType() == MutantType.NORMAL) {
            DataCapturer.capture("classifiedmutants", "redundant", mutant.getMutatedArtefact() + "-" + mutant.getSimpleDescription());
            mutant.setMutantType(MutantType.DUPLICATE);
        }
    }
}
