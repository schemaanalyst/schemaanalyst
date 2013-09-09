/*
 */
package org.schemaanalyst.mutation.redundancy;

import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;

/**
 *
 * @author Chris J. Wright
 */
public class ForeignKeyEquivalenceTester extends MultiColumnEquivalenceTester<ForeignKeyConstraint> {

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
