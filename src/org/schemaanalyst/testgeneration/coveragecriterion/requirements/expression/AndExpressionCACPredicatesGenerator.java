package org.schemaanalyst.testgeneration.coveragecriterion.requirements.expression;

import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.AndExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 07/02/2014.
 */
public class AndExpressionCACPredicatesGenerator extends ExpressionCACPredicatesGenerator {

    private List<Expression> subexpressions;

    public AndExpressionCACPredicatesGenerator(Table table, AndExpression expression) {
        super(table, expression);
        subexpressions = expression.getSubexpressions();
    }

    @Override
    public List<Predicate> generateTruePredicates() {
        List<Predicate> predicates = new ArrayList<>();

        for (Expression subexpression : subexpressions) {
            Predicate truePredicateExcludingExpression = getTruePredicateExcludingExpression(subexpression);

            for (Predicate truePredicate : generateTruePredicates(table, subexpression)) {
                Predicate predicate = new Predicate(
                        "Testing " + expression + " is true with " + truePredicate.getPurposes());
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
                        "Testing " + expression + " is false with " + falsePredicate.getPurposes());
                predicate.addClauses(falsePredicate);
                predicate.addClauses(truePredicateExcludingExpression);
                predicates.add(predicate);
            }
        }

        return predicates;
    }

    @Override
    public List<Predicate> generateNullPredicates() {
        List<Predicate> predicates = new ArrayList<>();

        for (Expression subexpression : subexpressions) {
            Predicate truePredicateExcludingExpression = getTruePredicateExcludingExpression(subexpression);

            for (Predicate nullPredicate : generateNullPredicates(table, subexpression)) {
                Predicate predicate = new Predicate(
                        "Testing " + expression + " is NULL with " + nullPredicate.getPurposes());
                predicate.addClauses(nullPredicate);
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
