/*
 */
package org.schemaanalyst.mutation.redundancy;

/**
 * A parent class for a {@link RedundantMutantRemover} that uses an equivalence 
 * tester to determine which mutants to remove.
 * 
 * @author Chris J. Wright
 */
public abstract class EquivalenceTesterMutantRemover<T> extends RedundantMutantRemover<T> {

    EquivalenceTester<T> tester;

    public EquivalenceTesterMutantRemover(EquivalenceTester<T> tester) {
        this.tester = tester;
    }
}
