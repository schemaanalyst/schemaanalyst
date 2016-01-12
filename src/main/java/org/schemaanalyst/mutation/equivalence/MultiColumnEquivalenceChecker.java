/*
 */
package org.schemaanalyst.mutation.equivalence;

import org.schemaanalyst.sqlrepresentation.constraint.MultiColumnConstraint;

/**
 * <p>
 * An {@link EquivalenceChecker} that compares two {@link MultiColumnConstraint}
 *  objects to determine if they are equivalent.
 * </p>
 * 
 * <p>
 * Two {@link MultiColumnConstraint} objects are equivalent if they are the same 
 * object, or:
 * <ol>
 * <li>
 * They have matching identifiers;
 * </li>
 * <li>
 * They belong to tables with matching identifiers;
 * </li>
 * <li>
 * They refer to the same number of columns in those tables;
 * </li>
 * <li>
 * They refer to the same columns in those tables;
 * </li>
 * </p>
 * 
 * @author Chris J. Wright
 */
public class MultiColumnEquivalenceChecker<T extends MultiColumnConstraint> extends EquivalenceChecker<T> {

    protected boolean ignoreName;

    public MultiColumnEquivalenceChecker() {
        this.ignoreName = false;
    }

    public MultiColumnEquivalenceChecker(boolean ignoreName) {
        this.ignoreName = ignoreName;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public boolean areEquivalent(T a, T b) {
        if (super.areEquivalent(a, b)) {
            return true;
        } else if (!ignoreName && !a.getIdentifier().equals(b.getIdentifier())) {
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
