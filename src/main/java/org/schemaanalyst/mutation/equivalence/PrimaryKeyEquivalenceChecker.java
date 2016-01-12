/*
 */
package org.schemaanalyst.mutation.equivalence;

import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;

/**
 * <p>
 * An {@link EquivalenceChecker} that compares two {@link PrimaryKeyConstraint}
 *  objects to determine if they are equivalent.
 * </p>
 * 
 * <p>
 * Two {@link PrimaryKeyConstraint} objects are equivalent if they are the same 
 * object, or:
 * <ol>
 * <li>
 * They have matching identifiers;
 * </li>
 * <li>
 * They belong to tables with matching identifiers;
 * </li>
 * <li>
 * They refer to the same number of columns in those tables; and
 * </li>
 * <li>
 * They refer to the same columns in those tables.
 * </li>
 * </p>
 * 
 * @author Chris J. Wright
 */
public class PrimaryKeyEquivalenceChecker extends MultiColumnEquivalenceChecker<PrimaryKeyConstraint> {

    public PrimaryKeyEquivalenceChecker(boolean ignoreName) {
        super(ignoreName);
    }

    public PrimaryKeyEquivalenceChecker() {
        this.ignoreName = false;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public boolean areEquivalent(PrimaryKeyConstraint a, PrimaryKeyConstraint b) {
        return super.areEquivalent(a, b);
    }
}
