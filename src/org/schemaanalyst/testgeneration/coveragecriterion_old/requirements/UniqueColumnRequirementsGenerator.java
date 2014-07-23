package org.schemaanalyst.testgeneration.coveragecriterion_old.requirements;

import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.List;

/**
 * Created by phil on 21/01/2014.
 */
public class UniqueColumnRequirementsGenerator extends ConstraintRequirementsGenerator {

    public UniqueColumnRequirementsGenerator(Schema schema, Table table) {
        super(schema, table);
    }

    public UniqueColumnRequirementsGenerator(Schema schema, Table table, boolean generateFullPredicate) {
        super(schema, table, generateFullPredicate);
    }

    public Requirements generateRequirements() {
        Requirements requirements = new Requirements();

        List<Column> columns = table.getColumns();
        for (Column column : columns) {

            // UNIQUE column requirement
            addUniqueColumnRequirement(requirements, column);

            // NOT UNIQUE column requirement
            addNotUniqueColumnRequirement(requirements, column);
        }

        return requirements;
    }

    private void addNotUniqueColumnRequirement(Requirements requirements, Column column) {
        Predicate predicate = generatePredicate("Test " + column + " is NOT UNIQUE for " + table);
        predicate.setColumnUniqueStatus(table, column, false);
        predicate.setColumnNullStatus(table, column, false);
        requirements.addPredicate(predicate);
    }

    private void addUniqueColumnRequirement(Requirements requirements, Column column) {
        Predicate predicate = generatePredicate("Test " + column + " is UNIQUE for " + table);
        predicate.setColumnUniqueStatus(table, column, true);
        predicate.setColumnNullStatus(table, column, false);
        requirements.addPredicate(predicate);
    }
}
