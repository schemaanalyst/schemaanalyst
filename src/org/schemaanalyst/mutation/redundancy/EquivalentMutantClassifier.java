package org.schemaanalyst.mutation.redundancy;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.equivalence.EquivalenceChecker;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import java.util.Iterator;
import org.schemaanalyst.mutation.MutantType;
import org.schemaanalyst.util.DataCapturer;

/**
 * A {@link MutantRemover} that classifies mutants equivalent to the original
 * artefact, according to a provided {@link EquivalenceChecker}, but does not 
 * actually remove them.
 *
 * @author Chris J. Wright
 * @param <T> The type of the artefact being mutated.
 */
public class EquivalentMutantClassifier<T> extends EquivalentMutantDetector<T> {

    /**
     * Constructor.
     *
     * @param checker The equivalence checker
     * @param originalArtefact The original artefact that was mutated
     */
    public EquivalentMutantClassifier(EquivalenceChecker<T> checker, T originalArtefact) {
        super(checker, originalArtefact);
    }

    @Override
    public void process(Mutant<T> mutant, Iterator<Mutant<T>> it) {
        if (mutant.getMutantType() == MutantType.NORMAL) {
            DataCapturer.capture("classifiedmutants", "equivalent", mutant.getMutatedArtefact() + "-" + mutant.getSimpleDescription());
            mutant.setMutantType(MutantType.EQUIVALENT);
        }
    }

}
