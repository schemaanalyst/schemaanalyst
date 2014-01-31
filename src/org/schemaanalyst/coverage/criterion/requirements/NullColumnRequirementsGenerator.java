package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.List;

import static org.schemaanalyst.coverage.criterion.clause.ClauseFactory.isNull;

/**
 * Created by phil on 21/01/2014.
 */
public class NullColumnRequirementsGenerator extends RequirementsGenerator {

    public NullColumnRequirementsGenerator(Schema schema, Table table) {
        super(schema, table);
    }

    public Requirements generateRequirements() {
        Requirements requirements = new Requirements();

        List<Column> columns = table.getColumns();
        for (Column column : columns) {
            Predicate predicate = generatePredicate("-- Testing " + column + " as null");
            predicate.addClause(isNull(table, column));
            requirements.add(predicate);
        }

        return requirements;
    }
}
