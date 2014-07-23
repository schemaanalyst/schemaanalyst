package org.schemaanalyst.testgeneration.criterion.integrityconstraint;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.criterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.criterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.criterion.TestRequirements;

/**
 * Created by phil on 18/07/2014.
 */
public class APC implements CoverageCriterion {

    private Schema schema;
    private TestRequirements tr;
    private TestRequirementIDGenerator trIDGenerator;

    public APC(Schema schema, TestRequirementIDGenerator trIDGenerator) {
        this.schema = schema;
        this.trIDGenerator = trIDGenerator;
    }

    public TestRequirements generateRequirements() {
        tr = new TestRequirements();
        trIDGenerator.reset(schema.getName(), "schema");

        for (Table table : schema.getTables()) {
            trIDGenerator.reset(table.getName(), "table");
            generateRequirements(table, true);
            generateRequirements(table, false);
        }

        return tr;
    }

    private void generateRequirements(Table table, boolean truthValue) {
        tr.addTestRequirement(
                trIDGenerator.nextID(),
                "Acceptance predicate for " + table + " is " + truthValue,
                PredicateGenerator.generateAcceptancePredicate(schema, table, truthValue));
    }

}
