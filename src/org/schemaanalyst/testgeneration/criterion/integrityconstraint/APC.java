package org.schemaanalyst.testgeneration.criterion.integrityconstraint;

import org.schemaanalyst.logic.BooleanUtils;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.criterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.criterion.IDGenerator;
import org.schemaanalyst.testgeneration.criterion.IDGeneratorUsingTable;

/**
 * Created by phil on 18/07/2014.
 */
public class APC extends CoverageCriterion {

    private Schema schema;

    public APC(Schema schema) {
        this.schema = schema;
    }

    public void generateRequirements() {
        for (Table table : schema.getTables()) {
            IDGenerator idGenerator = new IDGeneratorUsingTable(table);

            for (boolean truthValue : BooleanUtils.VALUES) {
                addRequirement(
                        idGenerator.nextID(),
                        "Acceptance predicate for " + table + " is " + truthValue,
                        PredicateGenerator.generateAcceptancePredicate(schema, table, truthValue));
            }
        }
    }

}
