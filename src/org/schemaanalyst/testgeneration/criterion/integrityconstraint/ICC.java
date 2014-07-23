package org.schemaanalyst.testgeneration.criterion.integrityconstraint;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.testgeneration.criterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.criterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.criterion.TestRequirements;

/**
 * Created by phil on 18/07/2014.
 */
public class ICC implements CoverageCriterion {

    protected Schema schema;
    protected TestRequirementIDGenerator trIDGenerator;
    protected TestRequirements tr;

    public ICC(Schema schema, TestRequirementIDGenerator trIDGenerator) {
        this.schema = schema;
        this.trIDGenerator = trIDGenerator;
    }

    public TestRequirements generateRequirements() {
        tr = new TestRequirements();
        trIDGenerator.reset(schema.getName(), "schema");

        for (Table table : schema.getTables()) {
            trIDGenerator.reset(table.getName(), "table");

            for (Constraint constraint : schema.getConstraints(table)) {
                generateRequirements(constraint, true);
                generateRequirements(constraint, false);
            }
        }
        return tr;
    }

    protected void generateRequirements(Constraint constraint, boolean truthValue) {
        tr.addTestRequirement(
                trIDGenerator.nextID(),
                generateMsg(constraint) + " is " + truthValue,
                PredicateGenerator.generateConditionPredicate(constraint, truthValue));
    }


    protected String generateMsg(Constraint constraint) {
        return constraint + "for " + constraint.getTable();
    }
}
