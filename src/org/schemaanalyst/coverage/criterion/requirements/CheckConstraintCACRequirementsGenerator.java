package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.coverage.criterion.clause.Clause;
import org.schemaanalyst.coverage.criterion.clause.NullClause;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

import static org.schemaanalyst.coverage.criterion.requirements.expression.ExpressionCACPredicatesGenerator.generateTruePredicates;
import static org.schemaanalyst.coverage.criterion.requirements.expression.ExpressionCACPredicatesGenerator.generateFalsePredicates;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 07/02/2014.
 */
public class CheckConstraintCACRequirementsGenerator extends RequirementsGenerator {

    private Expression expression;

    public CheckConstraintCACRequirementsGenerator(Schema schema, Table table, CheckConstraint checkConstraint) {
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

            // Explicitly set the column NULL status for NullClauses, to avoid infeasible predicates
            // if the column was required to be the opposite NULL status previously in the predicate
            for (Clause clause : expressionPredicate.getClauses()) {
                if (clause instanceof NullClause) {
                    NullClause nullClause = (NullClause) clause;
                    predicate.setColumnNullStatus(nullClause.getTable(), nullClause.getColumn(), nullClause.getSatisfy());
                }
            }

            requirements.add(predicate);
        }

        return requirements;
    }
}
