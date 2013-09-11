/*
 */
package org.schemaanalyst.mutation.redundancy;

import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;

/**
 *
 * @author Chris J. Wright
 */
public class NotNullEquivalenceChecker extends EquivalenceChecker<NotNullConstraint> {
    
    @Override
    public boolean areEquivalent(NotNullConstraint a, NotNullConstraint b) {
        if (super.areEquivalent(a, b)) {
            return true;
        } else if (!a.getIdentifier().equals(b.getIdentifier())) {
            return false;
        } else if (!a.getColumn().getIdentifier().equals(b.getColumn().getIdentifier())) {
            return false;
        } else {
            return true;
        }
    }
    
}
