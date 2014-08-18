package org.schemaanalyst.testgeneration.coveragecriterion.column;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;

import java.util.List;

/**
 * Created by phil on 18/08/2014.
 */
public class ANCC extends NCC {

    public ANCC(Schema schema, TestRequirementIDGenerator testRequirementIDGenerator) {
        super(schema, testRequirementIDGenerator);
    }

    public String getName() {
        return "ANCC";
    }

    protected void removeColumnsWithNotNullConstraints(Table table, List<Column> columns) {
        for (NotNullConstraint notNullConstraint : schema.getNotNullConstraints(table)) {
            columns.remove(notNullConstraint.getColumn());
        }
    }

    protected void generateRequirements(Table table) {

    }
}
