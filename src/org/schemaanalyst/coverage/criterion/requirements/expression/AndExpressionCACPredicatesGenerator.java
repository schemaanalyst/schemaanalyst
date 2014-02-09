package org.schemaanalyst.coverage.criterion.requirements.expression;

import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.AndExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 07/02/2014.
 */
public class AndExpressionCACPredicatesGenerator extends ExpressionCACPredicatesGenerator {

    private AndExpression andExpression;
    private List<Expression> subexpressions;

    public AndExpressionCACPredicatesGenerator(Table table, AndExpression andExpression) {
        super(table);
        this.andExpression = andExpression;
        subexpressions = andExpression.getSubexpressions();
    }

    @Override
    public List<Predicate> generateTruePredicates() {
        List<Predicate> predicates = new ArrayList<>();

        for (Expression subexpression : subexpressions) {
            Predicate truePredicateExcludingExpression = getTruePredicateExcludingExpression(subexpression);

            for (Predicate truePredicate : generateTruePredicates(table, subexpression)) {
                Predicate predicate = new Predicate(
                        "Testing " + andExpression + " is true with " + truePredicate.getPurpose());
                predicate.addClauses(truePredicate);
                predicate.addClauses(truePredicateExcludingExpression);
                predicates.add(predicate);
            }
        }

        return predicates;
    }

    @Override
    public List<Predicate> generateFalsePredicates() {
        List<Predicate> predicates = new ArrayList<>();

        for (Expression subexpression : subexpressions) {
            Predicate truePredicateExcludingExpression = getTruePredicateExcludingExpression(subexpression);

            for (Predicate falsePredicate : generateFalsePredicates(table, subexpression)) {
                Predicate predicate = new Predicate(
                        "Testing " + andExpression + " is false with " + falsePredicate.getPurpose());
                predicate.addClauses(falsePredicate);
                predicate.addClauses(truePredicateExcludingExpression);
                predicates.add(predicate);
            }
        }

        return predicates;
    }

    private Predicate getTruePredicateExcludingExpression(Expression excludedExpression) {
        List<Expression> remainingSubexpressions = new ArrayList<>(subexpressions);
        remainingSubexpressions.remove(excludedExpression);

        Predicate predicate = new Predicate();
        for (Expression subexpression : remainingSubexpressions) {
            List<Predicate> truePredicates = generateTruePredicates(table, subexpression);
            predicate.addClauses(truePredicates.get(0));
        }
        return predicate;
    }
}
