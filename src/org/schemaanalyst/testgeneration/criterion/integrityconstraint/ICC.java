package org.schemaanalyst.testgeneration.criterion.integrityconstraint;

import org.schemaanalyst.logic.TwoVL;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.testgeneration.criterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.criterion.IDGenerator;
import org.schemaanalyst.testgeneration.criterion.IDGeneratorUsingTable;

/**
 * Created by phil on 18/07/2014.
 */
public class ICC extends CoverageCriterion {

    public ICC(Schema schema) {

        for (Table table : schema.getTables()) {
            IDGenerator generator = new IDGeneratorUsingTable(table);

            for (Constraint constraint : schema.getConstraints(table)) {
                for (boolean truthValue : TwoVL.VALUES) {
                    addRequirement(
                            generator.nextID(),
                            constraint + "for " + table + " is " + truthValue,
                            PredicateGenerator.generateConditionPredicate(constraint, truthValue));
                }
            }
        }
    }
}
