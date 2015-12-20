package org.schemaanalyst.dbms.sqlite;

import org.schemaanalyst.mutation.normalisation.NormaliseCheckIsNotNullToNotNull;
import org.schemaanalyst.mutation.normalisation.NormalisePrimaryKeyToUnique;
import org.schemaanalyst.mutation.normalisation.NormaliseUniquesToMostConstrainedUniques;
import org.schemaanalyst.mutation.normalisation.SchemaNormaliser;
import org.schemaanalyst.sqlrepresentation.Schema;

/**
 * Normalises a schema according to the behaviour of the SQLite DBMS.
 * 
 * @author Chris J. Wright
 */
public class SQLiteSchemaNormaliser extends SchemaNormaliser {

    private static final SchemaNormaliser PRIMARY_KEY_TO_UNIQUE = 
            new NormalisePrimaryKeyToUnique();
    private static final SchemaNormaliser UNIQUES_TO_MOST_CONSTRAINED_UNIQUES =
            new NormaliseUniquesToMostConstrainedUniques();
    private static final SchemaNormaliser CHECK_IS_NOT_NULL_TO_NOT_NULL =
            new NormaliseCheckIsNotNullToNotNull();
    
    @Override
    public Schema normalise(Schema schema) {
        Schema duplicate = schema.duplicate();
        PRIMARY_KEY_TO_UNIQUE.normalise(duplicate);
        UNIQUES_TO_MOST_CONSTRAINED_UNIQUES.normalise(duplicate);
        CHECK_IS_NOT_NULL_TO_NOT_NULL.normalise(duplicate);
        return duplicate;
    }

}
