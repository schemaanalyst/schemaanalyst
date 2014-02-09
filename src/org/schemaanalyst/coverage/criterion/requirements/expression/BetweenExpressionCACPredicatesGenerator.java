package org.schemaanalyst.coverage.criterion.requirements.expression;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.coverage.criterion.clause.ExpressionClause;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.BetweenExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 07/02/2014.
 */
public class BetweenExpressionCACPredicatesGenerator extends ExpressionCACPredicatesGenerator {

    private BetweenExpression betweenExpression;

    public BetweenExpressionCACPredicatesGenerator(Table table, BetweenExpression betweenExpression) {
        super(table);
        this.betweenExpression = betweenExpression;
    }

    @Override
    public List<Predicate> generateTruePredicates() {
        List<Predicate> requirements = new ArrayList<>();

        Predicate predicate = new Predicate("Testing " + betweenExpression + " is true");
        predicate.addClause(new ExpressionClause(table, betweenExpression, true));

        requirements.add(predicate);
        return requirements;
    }

    @Override
    public List<Predicate> generateFalsePredicates() {
        List<Predicate> requirements = new ArrayList<>();

        Predicate lowerBoundPredicate = new Predicate(
                "Testing " + betweenExpression + " is false (lower bound)");
        RelationalExpression lowerBoundRelationalExpression = new RelationalExpression(
                betweenExpression.getSubject(), RelationalOperator.LESS, betweenExpression.getLHS());
        lowerBoundPredicate.addClause(new ExpressionClause(table, lowerBoundRelationalExpression, true));
        requirements.add(lowerBoundPredicate);

        Predicate upperBoundPredicate = new Predicate(
                "Testing " + betweenExpression + " is false (upper bound)");
        RelationalExpression upperBoundRelationalExpression = new RelationalExpression(
                betweenExpression.getSubject(), RelationalOperator.GREATER, betweenExpression.getRHS());
        upperBoundPredicate.addClause(new ExpressionClause(table, upperBoundRelationalExpression, true));
        requirements.add(upperBoundPredicate);

        return requirements;
    }
}
