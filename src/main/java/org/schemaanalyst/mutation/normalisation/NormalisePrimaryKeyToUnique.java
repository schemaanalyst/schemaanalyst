package org.schemaanalyst.mutation.normalisation;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

/**
 * Replace each Primary Key with a Unique.
 * 
 * @author Chris J. Wright
 */
public class NormalisePrimaryKeyToUnique extends SchemaNormaliser {

    @Override
    public Schema normalise(Schema schema) {
        for (PrimaryKeyConstraint pk : schema.getPrimaryKeyConstraints()) {
            // Add UNIQUE if does not exist
            UniqueConstraint uc = new UniqueConstraint(pk.getTable(), pk.getColumns());
            if (!schema.getUniqueConstraints(pk.getTable()).contains(uc)) {
                schema.addUniqueConstraint(uc);
            }
            schema.removePrimaryKeyConstraint(pk.getTable());
        }
        return schema;
    }
    
}
