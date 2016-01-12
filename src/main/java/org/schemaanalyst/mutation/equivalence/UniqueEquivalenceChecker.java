/*
 */
package org.schemaanalyst.mutation.equivalence;

import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

/**
 * <p>
 * An {@link EquivalenceChecker} that compares two {@link UniqueConstraint}
 *  objects to determine if they are equivalent.
 * </p>
 * 
 * <p>
 * Two {@link UniqueConstraint} objects are equivalent if they are the same 
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
public class UniqueEquivalenceChecker extends MultiColumnEquivalenceChecker<UniqueConstraint> {

    public UniqueEquivalenceChecker(boolean ignoreName) {
        super(ignoreName);
    }

    public UniqueEquivalenceChecker() {
        this.ignoreName = false;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public boolean areEquivalent(UniqueConstraint a, UniqueConstraint b) {
        return super.areEquivalent(a, b);
    }
    
}
