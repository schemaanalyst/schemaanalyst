package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.List;

import static org.schemaanalyst.coverage.criterion.clause.ClauseFactory.isNotNull;

/**
 * Created by phil on 31/01/2014.
 */
public class ValidNotNullRequirementsGenerator extends RequirementsGenerator {

    public ValidNotNullRequirementsGenerator(Schema schema, Table table) {
        super(schema, table);
    }

    @Override
    public Requirements generateRequirements() {
        Requirements requirements = new Requirements();

        Predicate predicate = generatePredicate("Test valid data for table " + table);

        List<Column> columns = table.getColumns();
        for (Column column : columns) {
            predicate.addClause(isNotNull(table, column));
        }

        requirements.add(predicate);
        return requirements;
    }
}
