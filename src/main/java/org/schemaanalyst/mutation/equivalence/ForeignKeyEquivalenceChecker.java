/*
 */
package org.schemaanalyst.mutation.equivalence;

import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;

/**
 * <p>
 * An {@link EquivalenceChecker} that compares two {@link ForeignKeyConstraint}
 *  objects to determine if they are equivalent.
 * </p>
 * 
 * <p>
 * Two {@link ForeignKeyConstraint} objects are equivalent if they are the same 
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
 * <li>
 * They refer to foreign tables with matching identifiers;
 * </li>
 * <li>
 * They refer to the same number of columns in those foreign tables; and
 * </li>
 * <li>
 * They refer to the same columns in those tables.
 * </li>
 * </ol>
 * </p>
 * 
 * @author Chris J. Wright
 */
public class ForeignKeyEquivalenceChecker extends MultiColumnEquivalenceChecker<ForeignKeyConstraint> {

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean areEquivalent(ForeignKeyConstraint a, ForeignKeyConstraint b) {
        if (!super.areEquivalent(a, b)) {
            return false;
        } else if (!a.getReferenceTable().getIdentifier().equals(b.getReferenceTable().getIdentifier())) {
            return false;
        } else if (a.getReferenceColumns().size() != b.getReferenceColumns().size()) {
            return false;
        } else {
            return a.getReferenceColumns().containsAll(b.getReferenceColumns());
        }
    }
    
}
