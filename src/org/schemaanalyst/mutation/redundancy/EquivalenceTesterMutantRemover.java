/*
 */
package org.schemaanalyst.mutation.redundancy;

import org.schemaanalyst.mutation.equivalence.EquivalenceChecker;

/**
 * <p>
 * A parent class for any {@link AbstractEquivalentMutantRemover} that uses an 
 * {@link EquivalenceChecker} to determine which mutants to remove.
 * </p>
 * 
 * @author Chris J. Wright
 */
public abstract class EquivalenceTesterMutantRemover<T> extends AbstractEquivalentMutantRemover<T> {

    EquivalenceChecker<T> checker;

    public EquivalenceTesterMutantRemover(EquivalenceChecker<T> checker) {
        this.checker = checker;
    }
}
