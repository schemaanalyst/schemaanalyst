package org.schemaanalyst.testgeneration.coveragecriterion_old.requirements;

import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.testgeneration.coveragecriterion_old.requirements.expression.ExpressionCACPredicatesGenerator;

import java.util.List;

/**
 * Created by phil on 07/02/2014.
 */
public class CheckConstraintCACRequirementsGenerator extends ConstraintRequirementsGenerator {

    private Expression expression;

    public CheckConstraintCACRequirementsGenerator(Schema schema, CheckConstraint constraint) {
        super(schema, constraint);
        this.expression = constraint.getExpression();
    }

    @Override
    public Requirements generateRequirements() {
        Requirements requirements = new Requirements();

        List<Predicate> expressionPredicates =
                ExpressionCACPredicatesGenerator.generatePredicates(table, expression);

        for (Predicate expressionPredicate : expressionPredicates) {
            Predicate predicate = generatePredicate(expressionPredicate.getPurposes());
            predicate.addClauses(expressionPredicate);
            requirements.addPredicate(predicate);
        }

        return requirements;
    }
}
