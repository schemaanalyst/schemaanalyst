package org.schemaanalyst.mutation.normalisation;

import java.util.List;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
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
                if (isMoreConstrainedUnique(uc, ucs)) {
                    schema.removeUniqueConstraint(uc);
                }
            }
        }
        return schema;
    }
    
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
    
}
