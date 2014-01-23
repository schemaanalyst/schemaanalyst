package org.schemaanalyst.coverage.requirements;

import org.schemaanalyst.coverage.predicate.Predicate;
import org.schemaanalyst.coverage.predicate.TestRequirements;
import org.schemaanalyst.coverage.predicate.function.NullFunction;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by phil on 21/01/2014.
 */
public class NullColumnRequirementsGenerator extends RequirementsGenerator {

    public NullColumnRequirementsGenerator(Schema schema, Table table) {
        super(schema, table);
    }

    public TestRequirements generateRequirements() {
        TestRequirements requirements = new TestRequirements();

        List<Column> columns = table.getColumns();
        for (Column column : columns) {
            Predicate predicate = generatePredicate("-- Testing " + column + " as null");
            predicate.addClause(new NullFunction(table, column));
            requirements.add(predicate);
        }

        return requirements;
    }
}
