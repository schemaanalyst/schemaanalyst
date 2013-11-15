/*
 */
package org.schemaanalyst.mutation.equivalence;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.datatype.DataType;

/**
 * <p>
 * An {@link EquivalenceChecker} that compares two {@link Column} objects to 
 * determine if they are equivalent.
 * </p>
 * 
 * <p>
 * Two {@link Column} objects are equivalent if they are the same 
 * object, or:
 * <ol>
 * <li>
 * They have matching identifiers; and
 * </li>
 * <li>
 * They have equal {@link DataType}s.
 * </li>
 * </ol>
 * </p>
 * 
 * @author Chris J. Wright
 */
public class ColumnEquivalenceChecker extends EquivalenceChecker<Column> {

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean areEquivalent(Column a, Column b) {
        if (super.areEquivalent(a, b)) {
            return true;
        } else if (!a.getIdentifier().equals(b.getIdentifier())) {
            return false;
        } else return a.getDataType().equals(b.getDataType());
    }
    
}
