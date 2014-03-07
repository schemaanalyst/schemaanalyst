
package org.schemaanalyst.mutation.equivalence;

import java.util.List;

/**
 * <p> Parent class for finding differences between two lists of elements using 
 * an {@link EquivalenceChecker}.</p>
 * 
 * @author Chris J. Wright
 */
public abstract class ChangedElementFinder {
    /**
     * Gets the first element from second that is not equivalent to the element
     * in the same index in first.
     *
     * @param <T> The type of element
     * @param checker The equivalence checker
     * @param first The first list of elements
     * @param second The second list of elements
     * @return The different element, or null if there is not one
     */
    protected static <T> T findDifferent(EquivalenceChecker<T> checker, List<T> first, List<T> second) {
        List<T> secondNotFirst = checker.subtract(second, first);
        if (secondNotFirst.size() > 0) {
            return secondNotFirst.get(0);
        }

        List<T> firstNotSecond = checker.subtract(first, second);
        if (firstNotSecond.size() > 0) {
            return firstNotSecond.get(0);
        }

        return null;
    }
}
