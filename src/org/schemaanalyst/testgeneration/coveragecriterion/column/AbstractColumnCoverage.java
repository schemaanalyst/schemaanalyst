package org.schemaanalyst.testgeneration.coveragecriterion.column;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;

/**
 * Created by phil on 18/08/2014.
 */
public abstract class AbstractColumnCoverage implements CoverageCriterion {

    protected Schema schema;
    protected TestRequirementIDGenerator testRequirementIDGenerator;
    protected TestRequirements testRequirements;

    public AbstractColumnCoverage(Schema schema,
                                  TestRequirementIDGenerator testRequirementIDGenerator) {
        this.schema = schema;
        this.testRequirementIDGenerator = testRequirementIDGenerator;
    }

    public abstract String getName();

    public TestRequirements generateRequirements() {
        testRequirements = new TestRequirements();
        testRequirementIDGenerator.reset(schema.getName(), "schema");

        for (Table table : schema.getTables()) {
            testRequirementIDGenerator.reset(table.getName(), "table");
            generateRequirements(table);
        }
        return testRequirements;
    }

    public abstract void generateRequirements(Table table);
}
