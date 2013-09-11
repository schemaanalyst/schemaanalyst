/*
 */
package org.schemaanalyst.mutation.redundancy;

import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;

/**
 *
 * @author Chris J. Wright
 */
public class PrimaryKeyEquivalenceChecker extends MultiColumnEquivalenceChecker<PrimaryKeyConstraint> {

    @Override
    public boolean areEquivalent(PrimaryKeyConstraint a, PrimaryKeyConstraint b) {
        return super.areEquivalent(a, b);
    }
    
}
