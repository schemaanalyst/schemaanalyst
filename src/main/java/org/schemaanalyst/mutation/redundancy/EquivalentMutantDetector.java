package org.schemaanalyst.mutation.redundancy;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.equivalence.EquivalenceChecker;
import org.schemaanalyst.mutation.pipeline.MutantRemover;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A {@link MutantRemover} that detects mutants equivalent to the original 
 * artefact, according to a provided {@link EquivalenceChecker}.
 * 
 * @author Chris J. Wright
 * @param <T> The type of the artefact being mutated.
 */
public abstract class EquivalentMutantDetector<T> extends EquivalenceTesterMutantRemover<T> {
    
    private T originalArtefact;
    
    private static final Logger LOGGER = Logger.getLogger(EquivalentMutantDetector.class.getName());

    /**
     * Constructor.
     * 
     * @param checker The equivalence checker
     * @param originalArtefact The original artefact that was mutated
     */
    public EquivalentMutantDetector(EquivalenceChecker<T> checker, T originalArtefact) {
        super(checker);
        this.originalArtefact = originalArtefact;
    }
    
    public abstract void process(Mutant<T> mutant, Iterator<Mutant<T>> it);
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public List<Mutant<T>> removeMutants(List<Mutant<T>> mutants) {
        for (Iterator<Mutant<T>> it = mutants.iterator(); it.hasNext();) {
            Mutant<T> mutant = it.next();
            if (checker.areEquivalent(originalArtefact, mutant.getMutatedArtefact())) {
                LOGGER.log(Level.FINE, "Equivalent mutant:\n{0}\n", new Object[]{mutant.getDescription()});
                process(mutant, it);
            } else if (hasDuplicateMethod(originalArtefact.getClass())) {
                if (checker.areEquivalent(applyRemoversToOriginal(originalArtefact, mutant), mutant.getMutatedArtefact())) {
                    LOGGER.log(Level.FINE, "Equivalent mutant:\n{0}\n", new Object[]{mutant.getDescription()});
                    process(mutant, it);
                }
            }
        }
        return mutants;
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
