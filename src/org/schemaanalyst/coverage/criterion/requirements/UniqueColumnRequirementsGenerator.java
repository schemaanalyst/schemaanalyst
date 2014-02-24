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
public class UniqueColumnRequirementsGenerator extends RequirementsGenerator {

    public UniqueColumnRequirementsGenerator(Schema schema, Table table) {
        super(schema, table);
    }

    public UniqueColumnRequirementsGenerator(Schema schema, Table table, boolean generateFullPredicate) {
        super(schema, table, generateFullPredicate);
    }

    public List<Predicate> generateRequirements() {
        List<Predicate> requirements = new ArrayList<>();

        List<Column> columns = table.getColumns();
        for (Column column : columns) {

            // UNIQUE column requirement
            addUniqueColumnRequirement(requirements, column);

            // NOT UNIQUE column requirement
            addNotUniqueColumnRequirement(requirements, column);
        }

        return requirements;
    }

    private void addNotUniqueColumnRequirement(List<Predicate> requirements, Column column) {
        Predicate predicate = generatePredicate("Test " + column + " is NOT UNIQUE for " + table);
        predicate.setColumnUniqueStatus(table, column, false);
        predicate.setColumnNullStatus(table, column, false);
        requirements.add(predicate);
    }

    private void addUniqueColumnRequirement(List<Predicate> requirements, Column column) {
        Predicate predicate = generatePredicate("Test " + column + " is UNIQUE for " + table);
        predicate.setColumnUniqueStatus(table, column, true);
        predicate.setColumnNullStatus(table, column, false);
        requirements.add(predicate);
    }
}
