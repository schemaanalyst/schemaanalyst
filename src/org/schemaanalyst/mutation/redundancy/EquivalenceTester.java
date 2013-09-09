/*
 */
package org.schemaanalyst.mutation.redundancy;

/**
 *
 * @author Chris J. Wright
 */
public abstract class EquivalenceTester<T> {
    public boolean areEquivalent(T a, T b) {
        return (a == b);
    }
}
