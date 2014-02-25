package org.schemaanalyst.coverage.criterion.requirements.expression;

import org.schemaanalyst.coverage.criterion.clause.ExpressionClause;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.BetweenExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 07/02/2014.
 */
public class BetweenExpressionCACPredicatesGenerator extends ExpressionCACPredicatesGenerator {

    private Expression subject, lhs, rhs;

    public BetweenExpressionCACPredicatesGenerator(Table table, BetweenExpression expression) {
        super(table, expression);
        subject = expression.getSubject();
        lhs = expression.getLHS();
        rhs = expression.getRHS();
    }

    @Override
    public List<Predicate> generateTruePredicates() {
        List<Predicate> requirements = new ArrayList<>();

        Predicate predicate = new Predicate("Testing " + expression + " is true");
        predicate.addClause(new ExpressionClause(table, expression, true));
        setNullStatusForColumns(predicate);

        requirements.add(predicate);
        return requirements;
    }

    @Override
    public List<Predicate> generateFalsePredicates() {
        List<Predicate> requirements = new ArrayList<>();

        Predicate lowerBoundPredicate = new Predicate(
                "Testing " + expression + " is false (lower bound)");
        RelationalExpression lowerBoundRelationalExpression = new RelationalExpression(
                subject, RelationalOperator.LESS, lhs);
        lowerBoundPredicate.addClause(new ExpressionClause(table, lowerBoundRelationalExpression, true));
        requirements.add(lowerBoundPredicate);

        Predicate upperBoundPredicate = new Predicate(
                "Testing " + expression + " is false (upper bound)");
        RelationalExpression upperBoundRelationalExpression = new RelationalExpression(
                subject, RelationalOperator.GREATER, rhs);
        upperBoundPredicate.addClause(new ExpressionClause(table, upperBoundRelationalExpression, true));
        requirements.add(upperBoundPredicate);

        return requirements;
    }

    @Override
    public List<Predicate> generateNullPredicates() {
        List<Predicate> requirements = new ArrayList<>();

        List<Column> columns = expression.getColumnsInvolved();
        for (Column column : columns) {
            Predicate predicate = new Predicate("Testing " + expression + " is NULL - " + column + " is NULL");
            setNullStatusForColumns(predicate);
            predicate.setColumnNullStatus(table, column, true);
            requirements.add(predicate);
        }
        return requirements;
    }
}
