/*
 */
package org.schemaanalyst.mutation.equivalence;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

/**
 * <p>
 * An {@link EquivalenceChecker} that compares two {@link Table} objects to 
 * determine if they are equivalent.
 * </p>
 * 
 * <p>
 * Two {@link Table} objects are equivalent if they are the same object, or:
 * <ol>
 * <li>
 * They have matching identifiers;
 * </li>
 * <li>
 * They have the same number of columns; and
 * </li>
 * <li>
 * Each column in one table is equivalent to a column in the other table, and 
 * vice-versa.
 * </li>
 * </p>
 * 
 * @author Chris J. Wright
 */
public class TableEquivalenceChecker extends EquivalenceChecker<Table> {
    
    EquivalenceChecker<Column> columnEquivalenceChecker;
    
    public TableEquivalenceChecker(EquivalenceChecker<Column> columnEquivalenceChecker) {
        this.columnEquivalenceChecker = columnEquivalenceChecker;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean areEquivalent(Table a, Table b) {
        if (super.areEquivalent(a, b)) {
            return true;
        } else if (!a.getIdentifier().equals(b.getIdentifier())) {
            return false;
        } else if (a.getColumns().size() != b.getColumns().size()) {
            return false;
        } else {
            return columnEquivalenceChecker.areEquivalent(a.getColumns(), b.getColumns());
        }
    }
    
}
