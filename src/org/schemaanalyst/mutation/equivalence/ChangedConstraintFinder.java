
package org.schemaanalyst.mutation.equivalence;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

/**
 * <p>
 * Class that can determine which constraint has been affected by a change 
 * between two versions of a schema.
 * </p>
 * 
 * <p>
 * Note: This does not support differences in foreign key constraints.
 * </p>
 * 
 * @author Chris J. Wright
 */
public class ChangedConstraintFinder extends ChangedElementFinder {
    public static Constraint getDifferentConstraint(Schema first, Schema second) {
        Constraint diffC = findDifferent(new PrimaryKeyEquivalenceChecker(true), first.getPrimaryKeyConstraints(), second.getPrimaryKeyConstraints());
        if (diffC != null) {
            return diffC;
        }

        diffC = findDifferent(new UniqueEquivalenceChecker(true), first.getUniqueConstraints(), second.getUniqueConstraints());
        if (diffC != null) {
            return diffC;
        }

        diffC = findDifferent(new CheckEquivalenceChecker(true), first.getCheckConstraints(), second.getCheckConstraints());
        if (diffC != null) {
            return diffC;
        }

        diffC = findDifferent(new NotNullEquivalenceChecker(true), first.getNotNullConstraints(), second.getNotNullConstraints());
        if (diffC != null) {
            return diffC;
        }
        
        diffC = findDifferent(new ForeignKeyEquivalenceChecker(), first.getForeignKeyConstraints(), second.getForeignKeyConstraints());
        if (diffC != null) {
            return diffC;
        }
        
        return null;
    }
}
