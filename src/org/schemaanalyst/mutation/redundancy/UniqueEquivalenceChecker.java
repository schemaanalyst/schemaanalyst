/*
 */
package org.schemaanalyst.mutation.redundancy;

import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

/**
 *
 * @author Chris J. Wright
 */
public class UniqueEquivalenceChecker extends MultiColumnEquivalenceChecker<UniqueConstraint> {

    @Override
    public boolean areEquivalent(UniqueConstraint a, UniqueConstraint b) {
        return super.areEquivalent(a, b);
    }
    
}
