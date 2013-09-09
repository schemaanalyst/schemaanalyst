/*
 */
package org.schemaanalyst.mutation.redundancy;

import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;

/**
 *
 * @author Chris J. Wright
 */
public class PrimaryKeyEquivalenceTester extends MultiColumnEquivalenceTester<PrimaryKeyConstraint> {

    @Override
    public boolean areEquivalent(PrimaryKeyConstraint a, PrimaryKeyConstraint b) {
        return super.areEquivalent(a, b);
    }
    
}
