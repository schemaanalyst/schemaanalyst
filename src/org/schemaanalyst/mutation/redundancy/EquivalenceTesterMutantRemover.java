/*
 */
package org.schemaanalyst.mutation.redundancy;

/**
 * <p>
 * A parent class for any {@link RedundantMutantRemover} that uses an 
 * {@link EquivalenceChecker} to determine which mutants to remove.
 * </p>
 * 
 * @author Chris J. Wright
 */
public abstract class EquivalenceTesterMutantRemover<T> extends RedundantMutantRemover<T> {

    EquivalenceChecker<T> checker;

    public EquivalenceTesterMutantRemover(EquivalenceChecker<T> checker) {
        this.checker = checker;
    }
}
