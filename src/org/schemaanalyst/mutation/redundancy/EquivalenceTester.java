/*
 */
package org.schemaanalyst.mutation.redundancy;

import java.util.Iterator;

/**
 *
 * @author Chris J. Wright
 */
public abstract class EquivalenceTester<T> {
    /**
     * Tests if two elements are equivalent, according to some equivalence test.
     * The basic case simply tests object identity, as a simple positive case 
     * for subclasses to use cheaply.
     * 
     * @param a The first element
     * @param b The second element
     * @return 
     */
    public boolean areEquivalent(T a, T b) {
        return (a == b);
    }
    
    public boolean areEquivalent(Iterable<? extends T> iterableA, Iterable<? extends T> iterableB) {
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
