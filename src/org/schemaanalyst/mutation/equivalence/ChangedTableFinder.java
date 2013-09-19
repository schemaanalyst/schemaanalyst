/*
 */
package org.schemaanalyst.mutation.equivalence;

import java.util.List;
import org.schemaanalyst.sqlrepresentation.*;
import org.schemaanalyst.sqlrepresentation.constraint.*;

/**
 * <p>
 * Class that can determine which table has been affected by a change between
 * two versions of a schema.
 * </p>
 *
 * <p>
 * Note: This does not support differences in foreign key constraints.
 * </p>
 *
 * @author Chris J. Wright
 */
public class ChangedTableFinder {

    /**
     * Gets the table which is different between two schemas (assuming only one
     * table has changed).
     *
     * @param first The first schema
     * @param second The second schema
     * @return The table changed, or null if there is not one
     */
    public static Table getDifferentTable(Schema first, Schema second) {
        Table diff = findDifferent(new TableEquivalenceChecker(new ColumnEquivalenceChecker()), first.getTablesInOrder(), second.getTablesInOrder());
        if (diff != null) {
            return diff;
        }

        Constraint diffC = findDifferent(new PrimaryKeyEquivalenceChecker(true), first.getPrimaryKeyConstraints(), second.getPrimaryKeyConstraints());
        if (diffC != null) {
            return diffC.getTable();
        }

        diffC = findDifferent(new UniqueEquivalenceChecker(true), first.getUniqueConstraints(), second.getUniqueConstraints());
        if (diffC != null) {
            return diffC.getTable();
        }

        diffC = findDifferent(new CheckEquivalenceChecker(true), first.getCheckConstraints(), second.getCheckConstraints());
        if (diffC != null) {
            return diffC.getTable();
        }

        diffC = findDifferent(new NotNullEquivalenceChecker(true), first.getNotNullConstraints(), second.getNotNullConstraints());
        if (diffC != null) {
            return diffC.getTable();
        }
        return null;
    }

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
