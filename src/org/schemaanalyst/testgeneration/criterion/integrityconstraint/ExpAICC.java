package org.schemaanalyst.testgeneration.criterion.integrityconstraint;

import org.schemaanalyst.logic.ThreeVL;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.testgeneration.criterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.criterion.IDGenerator;
import org.schemaanalyst.testgeneration.criterion.IDGeneratorUsingTable;
import org.schemaanalyst.testgeneration.criterion.predicate.ComposedPredicate;
import org.schemaanalyst.testgeneration.criterion.predicate.Predicate;

/**
 * Created by phil on 18/07/2014.
 */
public class ExpAICC extends CoverageCriterion {

    public ExpAICC(Schema schema) {

        for (Table table : schema.getTables()) {
            IDGenerator generator = new IDGeneratorUsingTable(table);

            for (Constraint constraint : schema.getConstraints(table)) {
                for (ThreeVL truthValue : ThreeVL.VALUES) {
                    Predicate predicate = PredicateGenerator.generateExpressionPredicate(constraint, truthValue);
                    if (predicate != null) {
                        ComposedPredicate topLevelPredicate = PredicateGenerator.generateAcceptancePredicate(schema, table, true, constraint);
                        topLevelPredicate.addPredicate(predicate);

                        addRequirement(
                                generator.nextID(),
                                constraint + "for " + table + " is " + truthValue,
                                topLevelPredicate);
                    }
                }
            }
        }
    }
}
