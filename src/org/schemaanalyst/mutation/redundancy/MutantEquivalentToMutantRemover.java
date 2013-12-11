/*
 */
package org.schemaanalyst.mutation.redundancy;

import org.schemaanalyst.mutation.equivalence.EquivalenceChecker;
import java.util.ArrayList;
import java.util.List;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import org.schemaanalyst.util.DataCapturer;

/**
 * A {@link MutantRemover} that removes mutants equivalent to other mutants,
 * according to a provided {@link EquivalenceChecker}.
 *
 * @author Chris J. Wright
 * @param <T> The type of the artefact being mutated.
 */
public class MutantEquivalentToMutantRemover<T> extends EquivalenceTesterMutantRemover<T> {

    /**
     * Constructor.
     *
     * @param checker The equivalence checker
     */
    public MutantEquivalentToMutantRemover(EquivalenceChecker<T> checker) {
        super(checker);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Mutant<T>> removeMutants(List<Mutant<T>> mutants) {
        List<Mutant<T>> result = new ArrayList<>(mutants.size());
        for (int i = 0; i < mutants.size(); i++) {
            Mutant<T> outer = mutants.get(i);
            boolean found = false;
            for (int j = i + 1; j < mutants.size(); j++) {
                Mutant<T> inner = mutants.get(j);
                if (checker.areEquivalent(outer.getMutatedArtefact(), inner.getMutatedArtefact())) {
                    DataCapturer.capture("removedmutants", "redundant", outer.getMutatedArtefact() + "-" + outer.getSimpleDescription());
                    found = true;
                    break;
                }
            }
            if (!found) {
                result.add(outer);
            }
        }
        return result;
    }
}
