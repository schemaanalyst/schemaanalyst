/*
 */
package org.schemaanalyst.mutation.equivalence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * Abstract base class for classes that compare two objects of the same class
 * for equivalence.
 * </p>
 *
 * @author Chris J. Wright
 * @param <T> The type of object to test
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
        return (subtract(iterableA, iterableB).isEmpty()) && (subtract(iterableB, iterableA).isEmpty());
    }

    /**
     * Returns a list of those elements in A that do not have equivalent
     * elements in B.
     *
     * @param iterableA The first iterable
     * @param iterableB The second iterable
     * @return A list of elements in A-B, according to equivalence
     */
    public List<T> subtract(Iterable<? extends T> iterableA, Iterable<? extends T> iterableB) {
        List<T> result = new ArrayList<>();
        for (Iterator<? extends T> iterA = iterableA.iterator(); iterA.hasNext();) {
            T a = iterA.next();
            boolean found = false;
            Iterator<? extends T> iterB = iterableB.iterator();
            while (!found && iterB.hasNext()) {
                T b = iterB.next();
                if (areEquivalent(a, b)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                result.add(a);
            }
        }
        return result;
    }
}
