package org.schemaanalyst.testgeneration.coveragecriterion.requirements.expression;

import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.logic.predicate.clause.ExpressionClause;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 07/02/2014.
 */
public class InExpressionCACPredicatesGenerator extends ExpressionCACPredicatesGenerator {

    private Expression lhs;
    private List<Expression> subexpressions;

    public InExpressionCACPredicatesGenerator(Table table, InExpression expression) {
        super(table, expression);

        this.lhs = expression.getLHS();
        this.subexpressions = expression.getRHS().getSubexpressions();
    }

    @Override
    public List<Predicate> generateTruePredicates() {
        List<Predicate> predicates = new ArrayList<>();

        for (Expression element : subexpressions) {
            Expression equalsExpression = new RelationalExpression(lhs, RelationalOperator.EQUALS, element);
            Predicate predicate = new Predicate("Testing " + expression + " is true - " + lhs + " = " + element);
            predicate.addClause(new ExpressionClause(table, equalsExpression, true));
            setNullStatusForColumns(predicate);
            predicates.add(predicate);
        }

        return predicates;
    }

    @Override
    public List<Predicate> generateFalsePredicates() {
        List<Predicate> predicates = new ArrayList<>();

        Predicate predicate = new Predicate("Testing " + expression + " is false");
        for (Expression element : subexpressions) {
            Expression notEqualsExpression = new RelationalExpression(lhs, RelationalOperator.NOT_EQUALS, element);
            predicate.addClause(new ExpressionClause(table, notEqualsExpression, true));
        }
        setNullStatusForColumns(predicate);
        predicates.add(predicate);

        return predicates;
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
