/*
 */
package org.schemaanalyst.mutation.redundancy;

import org.schemaanalyst.sqlrepresentation.constraint.MultiColumnConstraint;

/**
 *
 * @author Chris J. Wright
 */
public class MultiColumnEquivalenceChecker<T extends MultiColumnConstraint> extends EquivalenceChecker<T> {

    @Override
    public boolean areEquivalent(T a, T b) {
        if (super.areEquivalent(a, b)) {
            return true;
        } else if (!a.getIdentifier().equals(b.getIdentifier())) {
            return false;
        } else if (!a.getTable().getIdentifier().equals(b.getTable().getIdentifier())) {
            return false;
        } else if (a.getNumColumns() != b.getNumColumns()) {
            return false;
        } else {
            return a.getColumns().containsAll(b.getColumns());
        }
    }
    
}
