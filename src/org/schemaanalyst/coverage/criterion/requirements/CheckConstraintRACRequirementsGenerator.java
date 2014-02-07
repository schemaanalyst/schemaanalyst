package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

import static org.schemaanalyst.coverage.criterion.requirements.expression.ExpressionRACPredicatesGenerator.generateTruePredicates;
import static org.schemaanalyst.coverage.criterion.requirements.expression.ExpressionRACPredicatesGenerator.generateFalsePredicates;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 07/02/2014.
 */
public class CheckConstraintRACRequirementsGenerator extends RequirementsGenerator {

    private Expression expression;

    public CheckConstraintRACRequirementsGenerator(Schema schema, Table table, CheckConstraint checkConstraint) {
        super(schema, table, checkConstraint);
        this.expression = checkConstraint.getExpression();
    }

    @Override
    public List<Predicate> generateRequirements() {
        List<Predicate> requirements = new ArrayList<>();

        List<Predicate> expressionPredicates = generateTruePredicates(table, expression);
        expressionPredicates.addAll(generateFalsePredicates(table, expression));

        for (Predicate expressionPredicate : expressionPredicates) {
            Predicate predicate = generatePredicate(expressionPredicate.getPurpose());
            predicate.addClauses(expressionPredicate);
            requirements.add(predicate);
        }

        return requirements;
    }
}
