package org.schemaanalyst.mutation.normalisation;

import org.schemaanalyst.sqlrepresentation.Schema;

/**
 * Reduces a schema to a normalised form, where constraints have been reduced 
 * to a simplified form.
 * 
 * @author Chris J. Wright
 */
public class SchemaNormaliser {
    
    /**
     * Produce a normalised copy of the schema.
     * @param schema The schema
     * @return The normalised schema
     */
    public Schema normalise(Schema schema) {
        return schema.duplicate();
    }
}
