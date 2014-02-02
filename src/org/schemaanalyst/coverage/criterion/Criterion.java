package org.schemaanalyst.coverage.criterion;

import org.schemaanalyst.coverage.criterion.requirements.Requirements;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

/**
 * Created by phil on 31/01/2014.
 */
public abstract class Criterion {

    public abstract Predicate generateInitialTablePredicate(Schema schema, Table table);

    public abstract Requirements generateRemainingRequirements(Schema schema, Table table);
}
