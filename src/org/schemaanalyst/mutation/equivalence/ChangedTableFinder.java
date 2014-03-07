/*
 */
package org.schemaanalyst.mutation.equivalence;

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
public class ChangedTableFinder extends ChangedElementFinder {

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
}
