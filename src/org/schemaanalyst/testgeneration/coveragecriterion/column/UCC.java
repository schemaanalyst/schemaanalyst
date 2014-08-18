package org.schemaanalyst.testgeneration.coveragecriterion.column;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.IntegrityConstraintCriterion;

/**
 * Created by phil on 18/08/2014.
 */
public class UCC extends IntegrityConstraintCriterion {

    public UCC(Schema schema,
               TestRequirementIDGenerator testRequirementIDGenerator) {
        super(schema, testRequirementIDGenerator, null);
    }

    public String getName() {
        return "UCC";
    }

    public TestRequirements generateRequirements() {

        testRequirements = new TestRequirements();
        testRequirementIDGenerator.reset(schema.getName(), "schema");

        for (Table table : schema.getTables()) {
            testRequirementIDGenerator.reset(table.getName(), "table");
            generateRequirements(table);
        }
        return testRequirements;
    }

    protected void generateRequirements(Table table) {

    }
}
