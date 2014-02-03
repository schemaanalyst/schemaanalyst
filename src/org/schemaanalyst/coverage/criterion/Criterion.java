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

    public Predicate generateInitialTablePredicate(Schema schema, Table table) {

        Predicate predicate = new ConstraintPredicateGenerator(schema, table).generate(
                "Test valid data for table " + table);

        List<Column> columns = table.getColumns();
        for (Column column : columns) {
            predicate.addClause(isNotNull(table, column));
        }

        return predicate;
    }

    public abstract List<Predicate> generateRequirements(Schema schema, Table table);
}
