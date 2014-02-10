package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.List;

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
            addIsNullRequirement(requirements, column);

            // Column IS NOT NULL requirement
            addIsNotNullRequirement(requirements, column);
        }

        return requirements;
    }

    private void addIsNotNullRequirement(List<Predicate> requirements, Column column) {
        Predicate predicate = generatePredicate("Test " + column + " as not null");
        predicate.setColumnNullStatus(table, column, false);
        requirements.add(predicate);
    }

    private void addIsNullRequirement(List<Predicate> requirements, Column column) {
        Predicate predicate = generatePredicate("Test " + column + " as null");
        predicate.setColumnNullStatus(table, column, true);
        requirements.add(predicate);
    }


}
