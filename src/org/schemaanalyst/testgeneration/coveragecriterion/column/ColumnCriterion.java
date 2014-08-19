package org.schemaanalyst.testgeneration.coveragecriterion.column;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;

import java.util.List;

import static org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator.IDType.SCHEMA;
import static org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator.IDType.TABLE;

/**
 * Created by phil on 18/08/2014.
 */
public abstract class ColumnCriterion extends CoverageCriterion {

    protected Schema schema;
    protected TestRequirementIDGenerator testRequirementIDGenerator;
    protected TestRequirements testRequirements;

    public ColumnCriterion(Schema schema, TestRequirementIDGenerator testRequirementIDGenerator) {
        this.schema = schema;
        this.testRequirementIDGenerator = testRequirementIDGenerator;
    }

    public abstract String getName();

    public TestRequirements generateRequirements() {
        testRequirements = new TestRequirements();
        testRequirementIDGenerator.reset(SCHEMA, schema.getName());

        for (Table table : schema.getTables()) {
            testRequirementIDGenerator.reset(TABLE, table.getName());
            generateRequirements(table);
        }
        return testRequirements;
    }

    protected void generateRequirements(Table table) {
        List<Column> columns = getColumns(table);
        for (Column column : columns) {
            generateRequirement(table, column, true);
            generateRequirement(table, column, false);
        }
    }

    protected List<Column> getColumns(Table table) {
        return table.getColumns();
    }

    protected abstract void generateRequirement(Table table, Column column, boolean truthValue);
}
