package org.schemaanalyst.mutation.normalisation;

import java.util.List;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

/**
 * Removes any Unique constraint that is superfluous because of another Unique constraint.
 * 
 * @author Chris J. Wright
 */
public class NormaliseUniquesToMostConstrainedUniques extends SchemaNormaliser {

    @Override
    public Schema normalise(Schema schema) {
        for (Table table : schema.getTables()) {
            List<UniqueConstraint> ucs = schema.getUniqueConstraints(table);
            for (UniqueConstraint uc : ucs) {
                if (isMoreConstrainedUnique(uc, ucs) && !hasDependentForeignKey(uc, schema)) {
                    schema.removeUniqueConstraint(uc);
                }
            }
        }
        return schema;
    }
    
    /**
     * Detects whether a unique constraint is subsumed by another unique with fewer columns.
     * 
     * @param uc The unique constraint
     * @param ucs All unique constraints defined for the table
     * @return Whether it is subsumed
     */
    private boolean isMoreConstrainedUnique(UniqueConstraint uc, List<UniqueConstraint> ucs) {
        List<Column> ucCols = uc.getColumns();
        for (UniqueConstraint other : ucs) {
            List<Column> otherCols = other.getColumns();
            if (uc != other && ucCols.size() > otherCols.size() && ucCols.containsAll(otherCols)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Detects whether a unique constraint is required by a foreign key constraint in another table.
     * 
     * @param unique The unique constraint
     * @param schema The schema
     * @return Whether the unique is required by a foreign key
     */
    private boolean hasDependentForeignKey(UniqueConstraint unique, Schema schema) {
        for (Table table : schema.getTables()) {
            // Only check FKs in other tables
            if (!table.equals(unique.getTable())) {
                for (ForeignKeyConstraint fk : schema.getForeignKeyConstraints(table)) {
                    // Only check the columns of FKs that reference the table
                    if (fk.getReferenceTable().equals(unique.getTable())) {
                        List<Column> uniqueCols = unique.getColumns();
                        List<Column> refCols = fk.getReferenceColumns();
                        if (uniqueCols.size() == refCols.size() && uniqueCols.containsAll(refCols)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
}
