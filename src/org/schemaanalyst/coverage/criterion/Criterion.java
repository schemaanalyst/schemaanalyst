package org.schemaanalyst.coverage.criterion;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.List;

import static org.schemaanalyst.coverage.criterion.clause.ClauseFactory.isNotNull;

/**
 * Created by phil on 31/01/2014.
 */
public abstract class Criterion {

    public abstract List<Predicate> generateRequirements(Schema schema, Table table);
}
