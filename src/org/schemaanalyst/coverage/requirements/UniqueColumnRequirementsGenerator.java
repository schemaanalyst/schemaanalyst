package org.schemaanalyst.coverage.requirements;

import org.schemaanalyst.coverage.predicate.Predicate;
import org.schemaanalyst.coverage.predicate.TestRequirements;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.List;

import static org.schemaanalyst.coverage.predicate.function.FunctionFactory.*;

/**
 * Created by phil on 21/01/2014.
 */
public class UniqueColumnRequirementsGenerator extends RequirementsGenerator {

    public UniqueColumnRequirementsGenerator(Schema schema, Table table) {
        super(schema, table);
    }

    public TestRequirements generateRequirements() {
        TestRequirements requirements = new TestRequirements();

        List<Column> columns = table.getColumns();
        for (Column column : columns) {

            // (1) Unique column entry
            Predicate predicate1 = generatePredicate("-- Testing " + column + " as unique");
            predicate1.addClause(unique(table, column));
            requirements.add(predicate1);

            // (2) Non-unique column entry
            Predicate predicate2 = generatePredicate("-- Testing " + column + " as non-unique (matching)");
            predicate2.addClause(notUnique(table, column));
            requirements.add(predicate2);
        }

        return requirements;
    }

}
