package org.schemaanalyst.testgeneration.criterion.integrityconstraint;

import org.schemaanalyst.logic.TwoVL;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.criterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.criterion.IDGenerator;
import org.schemaanalyst.testgeneration.criterion.IDGeneratorUsingTable;

/**
 * Created by phil on 18/07/2014.
 */
public class APC extends CoverageCriterion {

    public APC(Schema schema) {

        for (Table table : schema.getTables()) {
            IDGenerator generator = new IDGeneratorUsingTable(table);

            for (boolean truthValue : TwoVL.VALUES) {
                addRequirement(
                        generator.nextID(),
                        "Acceptance predicate for " + table + " is " + truthValue,
                        PredicateGenerator.generateAcceptancePredicate(schema, table, truthValue));
            }
        }
    }
}
