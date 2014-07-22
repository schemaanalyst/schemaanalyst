package org.schemaanalyst.testgeneration.criterion.integrityconstraint;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.testgeneration.criterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.criterion.IDGenerator;
import org.schemaanalyst.testgeneration.criterion.IDGeneratorUsingTable;

/**
 * Created by phil on 18/07/2014.
 */
public class ICC extends CoverageCriterion {

    protected Schema schema;
    protected IDGenerator idGenerator;

    public ICC(Schema schema) {
        this.schema = schema;
    }

    public void generateRequirements() {
        for (Table table : schema.getTables()) {
            idGenerator = new IDGeneratorUsingTable(table);

            for (Constraint constraint : schema.getConstraints(table)) {
                generateRequirements(constraint, true);
                generateRequirements(constraint, false);
            }
        }
    }

    protected void generateRequirements(Constraint constraint, boolean truthValue) {
        addRequirement(
                idGenerator.nextID(),
                generateMessage(constraint) + " is " + truthValue,
                PredicateGenerator.generateConditionPredicate(constraint, truthValue));
    }


    protected String generateMessage(Constraint constraint) {
        return constraint + "for " + constraint.getTable();
    }
}
