/*
 */
package org.schemaanalyst.mutation.redundancy;

import java.util.Iterator;

/**
 *
 * @author Chris J. Wright
 */
public abstract class EquivalenceChecker<T> {

    /**
     * Tests if two elements are equivalent, according to some equivalence test.
     * The basic case simply tests object identity, as a simple positive case
     * for subclasses to use cheaply.
     *
     * @param a The first element
     * @param b The second element
     * @return Whether they are equivalent
     */
    public boolean areEquivalent(T a, T b) {
        return (a == b);
    }

    /**
     * Tests if the iterable elements contain equivalent items, disregarding 
     * ordering of elements.
     * 
     * @param iterableA The first iterable
     * @param iterableB The second iterable
     * @return Whether they contain equivalent elements
     */
    public boolean areEquivalent(Iterable<? extends T> iterableA, Iterable<? extends T> iterableB) {
        return internalAreEquivalent(iterableA, iterableB) && internalAreEquivalent(iterableB, iterableA);
    }
    
    /**
     * Internal implementation for 
     * {@link #areEquivalent(java.lang.Iterable, java.lang.Iterable)} method, to
     * allow iteration across both methods (therefore checking {@code iterableA}
     *  is contained within {@code iterableB} and vice-versa).
     * 
     * @param iterableA
     * @param iterableB
     * @return 
     */
    private boolean internalAreEquivalent(Iterable<? extends T> iterableA, Iterable<? extends T> iterableB) {
        for (Iterator<? extends T> iterA = iterableA.iterator(); iterA.hasNext();) {
            T a = iterA.next();
            boolean found = false;
            Iterator<? extends T> iterB = iterableB.iterator();
            while (!found && iterB.hasNext()) {
                T b = iterB.next();
                if (areEquivalent(a, b)) {
                    iterB.remove();
                    found = true;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }
}
