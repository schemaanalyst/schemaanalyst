package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.List;

import static org.schemaanalyst.coverage.criterion.clause.ClauseFactory.notUnique;
import static org.schemaanalyst.coverage.criterion.clause.ClauseFactory.unique;

/**
 * Created by phil on 21/01/2014.
 */
public class UniqueColumnRequirementsGenerator extends RequirementsGenerator {

    public UniqueColumnRequirementsGenerator(Schema schema, Table table) {
        super(schema, table);
    }

    public Requirements generateRequirements() {
        Requirements requirements = new Requirements();

        List<Column> columns = table.getColumns();
        for (Column column : columns) {

            // (1) Unique column entry
            Predicate predicate1 = generatePredicate("Test " + column + " as unique");
            predicate1.addClause(unique(table, column, true));
            requirements.add(predicate1);

            // (2) Non-unique column entry
            Predicate predicate2 = generatePredicate("Test " + column + " as non-unique (matching)");
            predicate2.addClause(notUnique(table, column));
            requirements.add(predicate2);
        }

        return requirements;
    }

}
