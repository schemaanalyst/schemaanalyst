package org.schemaanalyst.testgeneration.coveragecriterion_old.requirements;

import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.List;

/**
 * Created by phil on 21/01/2014.
 */
public class NullColumnRequirementsGenerator extends ConstraintRequirementsGenerator {

    public NullColumnRequirementsGenerator(Schema schema, Table table) {
        super(schema, table);
    }

    public NullColumnRequirementsGenerator(Schema schema, Table table, boolean generateFullPredicate) {
        super(schema, table, generateFullPredicate);
    }

    public Requirements generateRequirements() {
        Requirements requirements = new Requirements();
        List<Column> columns = table.getColumns();

        for (Column column : columns) {
            // Column IS NULL requirement
            addIsNullRequirement(requirements, column);

            // Column IS NOT NULL requirement
            addIsNotNullRequirement(requirements, column);
        }

        return requirements;
    }

    private void addIsNotNullRequirement(Requirements requirements, Column column) {
        Predicate predicate = generatePredicate("Test " + column + " is NOT NULL for " + table);
        predicate.setColumnNullStatus(table, column, false);
        requirements.addPredicate(predicate);
    }

    private void addIsNullRequirement(Requirements requirements, Column column) {
        Predicate predicate = generatePredicate("Test " + column + " is NULL for " + table);
        predicate.setColumnNullStatus(table, column, true);
        requirements.addPredicate(predicate);
    }
}
