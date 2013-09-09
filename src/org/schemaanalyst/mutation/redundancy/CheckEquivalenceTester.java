/*
 */
package org.schemaanalyst.mutation.redundancy;

import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;

/**
 *
 * @author Chris J. Wright
 */
public class CheckEquivalenceTester extends EquivalenceTester<CheckConstraint>{

    @Override
    public boolean areEquivalent(CheckConstraint a, CheckConstraint b) {
        if (super.areEquivalent(a, b)) {
            return true;
        } else if (!a.getIdentifier().equals(b.getIdentifier())) {
            return false;
        } else if (!a.getTable().getIdentifier().equals(b.getTable().getIdentifier())) {
            return false;
        } else {
            return a.getExpression().equals(b.getExpression());
        }
    }
    
}
