package org.schemaanalyst.mutation.normalisation;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

/**
 * Replace each Primary Key with a Unique and not null.
 * 
 * @author Chris J. Wright
 */
public class NormalisePrimaryKeyToUniqueAndNotNull extends SchemaNormaliser {

    @Override
    public Schema normalise(Schema schema) {
        for (PrimaryKeyConstraint pk : schema.getPrimaryKeyConstraints()) {
            // Add UNIQUE if does not exist
            UniqueConstraint uc = new UniqueConstraint(pk.getTable(), pk.getColumns());
            if (!schema.getUniqueConstraints(pk.getTable()).contains(uc)) {
                schema.addUniqueConstraint(uc);
            }
            // Add NOT NULL if does not exist
            for (Column col : pk.getColumns()) {
                NotNullConstraint nn = new NotNullConstraint(pk.getTable(), col);
                if (!schema.getNotNullConstraints(pk.getTable()).contains(nn)) {
                    schema.addNotNullConstraint(nn);
                }
            }
            schema.removePrimaryKeyConstraint(pk.getTable());
        }
        return schema;
    }
}
