/*
 */
package org.schemaanalyst.mutation.equivalence;

import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;

/**
 * <p>
 * An {@link EquivalenceChecker} that compares two {@link NotNullConstraint}
 *  objects to determine if they are equivalent.
 * </p>
 * 
 * <p>
 * Two {@link NotNullConstraint} objects are equivalent if they are the same 
 * object, or:
 * <ol>
 * <li>
 * They have matching identifiers;
 * </li>
 * <li>
 * They belong to tables with matching identifiers; and
 * </li>
 * <li>
 * They refer to the same column in those tables.
 * </li>
 * </ol>
 * </p>
 * 
 * @author Chris J. Wright
 */
public class NotNullEquivalenceChecker extends EquivalenceChecker<NotNullConstraint> {
    
    protected boolean ignoreName;

    public NotNullEquivalenceChecker() {
        this.ignoreName = false;
    }

    public NotNullEquivalenceChecker(boolean ignoreName) {
        this.ignoreName = ignoreName;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public boolean areEquivalent(NotNullConstraint a, NotNullConstraint b) {
        if (super.areEquivalent(a, b)) {
            return true;
        } else if (!ignoreName && !a.getIdentifier().equals(b.getIdentifier())) {
            return false;
        } else if (!a.getTable().getIdentifier().equals(b.getTable().getIdentifier())) {
            return false;
        } else return a.getColumn().getIdentifier().equals(b.getColumn().getIdentifier());
    }
    
}
