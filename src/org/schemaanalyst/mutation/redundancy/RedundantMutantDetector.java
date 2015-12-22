/*
 */
package org.schemaanalyst.mutation.redundancy;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.equivalence.EquivalenceChecker;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.schemaanalyst.mutation.MutantType;

/**
 * A {@link MutantRemover} that detects mutants equivalent to other mutants,
 * according to a provided {@link EquivalenceChecker}.
 *
 * @author Chris J. Wright
 * @param <T> The type of the artefact being mutated.
 */
public abstract class RedundantMutantDetector<T> extends EquivalenceTesterMutantRemover<T> {

    /**
     * Constructor.
     *
     * @param checker The equivalence checker
     */
    public RedundantMutantDetector(EquivalenceChecker<T> checker) {
        super(checker);
    }
    
    private static final Logger LOGGER = Logger.getLogger(RedundantMutantDetector.class.getName());
    
    public abstract void process(Mutant<T> mutant, Iterator<Mutant<T>> it);

    /**
     * {@inheritDoc}
     */
    @Override    
    public List<Mutant<T>> removeMutants(List<Mutant<T>> mutants) {
        for (ListIterator<Mutant<T>> outerIter = mutants.listIterator(); outerIter.hasNext();) {
            Mutant<T> outer = outerIter.next();
            for (ListIterator<Mutant<T>> innerIter = mutants.listIterator(outerIter.nextIndex()); innerIter.hasNext();) {
                Mutant<T> inner = innerIter.next();
                if (inner.getMutantType().equals(MutantType.NORMAL) && checker.areEquivalent(outer.getMutatedArtefact(), inner.getMutatedArtefact())) {
                    LOGGER.log(Level.FINE, "Redundant mutant pair:\n{0}\n{1}\n", new Object[]{outer.getDescription(), inner.getDescription()});
                    process(outer, outerIter);
                    break;
                }
            }
        }
        return mutants;
    }
}
