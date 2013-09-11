/*
 */
package org.schemaanalyst.mutation.redundancy;

import org.schemaanalyst.sqlrepresentation.Column;

/**
 *
 * @author Chris J. Wright
 */
public class ColumnEquivalenceChecker extends EquivalenceChecker<Column> {

    @Override
    public boolean areEquivalent(Column a, Column b) {
        if (super.areEquivalent(a, b)) {
            return true;
        } else if (!a.getIdentifier().equals(b.getIdentifier())) {
            return false;
        } else if (!a.getDataType().equals(b.getDataType())) {
            return false;
        } else {
            return true;
        }
    }
    
}
