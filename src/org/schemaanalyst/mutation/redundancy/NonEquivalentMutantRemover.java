package org.schemaanalyst.mutation.redundancy;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.equivalence.EquivalenceChecker;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import org.schemaanalyst.util.DataCapturer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Retains only those mutants that are equivalent (even if some are also redundant)
 * @author Chris J. Wright
 */
public class NonEquivalentMutantRemover<T> extends EquivalenceTesterMutantRemover<T> {

    final private T originalArtefact;

    public NonEquivalentMutantRemover(T originalArtefact, EquivalenceChecker<T> checker) {
        super(checker);
        this.originalArtefact = originalArtefact;
    }

    @Override
    public List<Mutant<T>> removeMutants(List<Mutant<T>> mutants) {
        List<Mutant<T>> result = new ArrayList<>(mutants.size());
        // Remove redundant mutants (if they're not also equivalent)
        for (int i = 0; i < mutants.size(); i++) {
            Mutant<T> outer = mutants.get(i);
            boolean found = false;
            for (int j = i + 1; j < mutants.size(); j++) {
                Mutant<T> inner = mutants.get(j);
                if (checker.areEquivalent(outer.getMutatedArtefact(), inner.getMutatedArtefact())) {
                    if (!isEquivalent(outer)) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                result.add(outer);
            }
        }
        // Remove non-equivalent mutants
        for (Iterator<Mutant<T>> it = result.iterator(); it.hasNext();) {
            Mutant<T> mutant = it.next();
            if (!isEquivalent(mutant)) {
                it.remove();
            }
        }
        return result;
    }

    private boolean isEquivalent(Mutant<T> mutant) {
        if (checker.areEquivalent(originalArtefact, mutant.getMutatedArtefact())) {
            DataCapturer.capture("removedmutants", "equivalent", mutant.getMutatedArtefact() + "-" + mutant.getSimpleDescription());
            return true;
        } else if (hasDuplicateMethod(originalArtefact.getClass())) {
            if (checker.areEquivalent(applyRemoversToOriginal(originalArtefact, mutant), mutant.getMutatedArtefact())) {
                DataCapturer.capture("removedmutants", "equivalent", mutant.getMutatedArtefact() + "-" + mutant.getSimpleDescription());
                return true;
            }
        }
        return false;
    }

    private boolean hasDuplicateMethod(Class c) {
        return MethodUtils.getAccessibleMethod(c, "duplicate") != null;
    }

    private T applyRemoversToOriginal(T original, Mutant<T> mutant) {
        try {
            T modifiedOriginal = (T) MethodUtils.invokeMethod(original, "duplicate");
            List<Mutant<T>> list = Arrays.asList(new Mutant<>(modifiedOriginal, ""));
            for (MutantRemover mutantRemover : mutant.getRemoversApplied()) {
                list = mutantRemover.removeMutants(list);
            }
            if (list.size() != 1) {
                throw new RuntimeException("Applying the MutantRemovers used for a "
                        + "mutant on the original schema did not produce only 1 "
                        + "schema (expected: 1, actual: " + list.size() + ")");
            }
            return list.get(0).getMutatedArtefact();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException("Unable to execute the 'duplicate' "
                    + "method in a class that appears to have an accessible "
                    + "duplicate method to call", ex);
        }
    }

}
