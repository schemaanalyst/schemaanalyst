package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
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
        Predicate predicate = predicateGenerator.generate("Test " + column + " as non-unique (matching)");
        predicate.setColumnUniqueStatus(table, column, false);
        predicate.setColumnNullStatus(table, column, false);
        requirements.add(predicate);
    }

    private void addUniqueColumnRequirement(List<Predicate> requirements, Column column) {
        Predicate predicate = predicateGenerator.generate("Test " + column + " as unique");
        predicate.setColumnUniqueStatus(table, column, true);
        predicate.setColumnNullStatus(table, column, false);
        requirements.add(predicate);
    }
}
