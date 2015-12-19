package org.schemaanalyst.dbms.postgres;

import org.schemaanalyst.mutation.normalisation.NormaliseCheckIsNotNullToNotNull;
import org.schemaanalyst.mutation.normalisation.NormalisePrimaryKeyToUniqueAndNotNull;
import org.schemaanalyst.mutation.normalisation.SchemaNormaliser;
import org.schemaanalyst.sqlrepresentation.Schema;

/**
 * Normalises a schema according to the behaviour of the Postgres DBMS.
 * 
 * @author Chris J. Wright
 */
public class PostgresSchemaNormaliser extends SchemaNormaliser {

    private static final SchemaNormaliser PRIMARY_KEY_TO_UNIQUE_AND_NOT_NULL = 
            new NormalisePrimaryKeyToUniqueAndNotNull();
    private static final SchemaNormaliser CHECK_IS_NOT_NULL_TO_NOT_NULL =
            new NormaliseCheckIsNotNullToNotNull();
    
    @Override
    public Schema normalise(Schema schema) {
        Schema duplicate = schema.duplicate();
        PRIMARY_KEY_TO_UNIQUE_AND_NOT_NULL.normalise(duplicate);
        CHECK_IS_NOT_NULL_TO_NOT_NULL.normalise(duplicate);
        return duplicate;
    }

}
