package org.schemaanalyst.testgeneration.criterion.integrityconstraint;

import org.schemaanalyst.logic.TwoVL;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.testgeneration.criterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.criterion.IDGenerator;
import org.schemaanalyst.testgeneration.criterion.IDGeneratorUsingTable;
import org.schemaanalyst.testgeneration.criterion.predicate.ComposedPredicate;

/**
 * Created by phil on 18/07/2014.
 */
public class AICC extends CoverageCriterion {

    public AICC(Schema schema) {

        for (Table table : schema.getTables()) {
            IDGenerator generator = new IDGeneratorUsingTable(table);

            for (Constraint constraint : schema.getConstraints(table)) {
                for (boolean truthValue : TwoVL.VALUES) {
                    ComposedPredicate topLevelPredicate = PredicateGenerator.generateAcceptancePredicate(schema, table, true, constraint);
                    topLevelPredicate.addPredicate(PredicateGenerator.generateConditionPredicate(constraint, truthValue));

                    addRequirement(
                            generator.nextID(),
                            constraint + "for " + table + " is " + truthValue,
                            topLevelPredicate);
                }
            }
        }
    }
}
