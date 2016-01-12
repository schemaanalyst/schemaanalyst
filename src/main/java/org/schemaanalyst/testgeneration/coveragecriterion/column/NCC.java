package org.schemaanalyst.testgeneration.coveragecriterion.column;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementDescriptor;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.NullPredicate;

/**
 * Created by phil on 18/08/2014.
 */
public class NCC extends ColumnCriterion {

    public NCC(Schema schema, TestRequirementIDGenerator testRequirementIDGenerator) {
        super(schema, testRequirementIDGenerator);
    }

    public String getName() {
        return "NCC";
    }

    protected void generateRequirement(Table table, Column column, boolean truthValue) {
        testRequirements.addTestRequirement(
                new TestRequirement(
                        new TestRequirementDescriptor(
                                testRequirementIDGenerator.nextID(),
                                column + " is " + ((truthValue) ? "NULL" : "NOT NULL")
                        ),
                        new NullPredicate(table, column, truthValue),
                        null,
                        false
                )
        );
    }
}
