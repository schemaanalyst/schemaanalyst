package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.List;

import static org.schemaanalyst.coverage.criterion.clause.ClauseFactory.isNull;
import static org.schemaanalyst.coverage.criterion.clause.ClauseFactory.isNotNull;
/**
 * Created by phil on 21/01/2014.
 */
public class NullColumnRequirementsGenerator extends RequirementsGenerator {

    public NullColumnRequirementsGenerator(Schema schema, Table table) {
        super(schema, table);
    }

    public List<Predicate> generateRequirements() {
        List<Predicate> requirements = new ArrayList<>();

        List<Column> columns = table.getColumns();
        for (Column column : columns) {

            // Column IS NULL requirement
            Predicate isNullPredicate = predicateGenerator.generate("Test " + column + " as null");
            isNullPredicate.addClause(isNull(table, column));
            requirements.add(isNullPredicate);

            // Column IS NOT NULL requirement
            Predicate isNotNullPredicate = predicateGenerator.generate("Test " + column + " as null");
            isNotNullPredicate.addClause(isNotNull(table, column));
            requirements.add(isNotNullPredicate);
        }

        return requirements;
    }
}
