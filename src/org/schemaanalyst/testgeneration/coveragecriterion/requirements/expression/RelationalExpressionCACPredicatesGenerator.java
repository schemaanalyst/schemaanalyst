package org.schemaanalyst.testgeneration.coveragecriterion.requirements.expression;

import org.schemaanalyst.testgeneration.coveragecriterion.clause.ExpressionClause;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 07/02/2014.
 */
public class RelationalExpressionCACPredicatesGenerator extends ExpressionCACPredicatesGenerator {

    public RelationalExpressionCACPredicatesGenerator(Table table, RelationalExpression expression) {
        super(table, expression);
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

        Predicate predicate = new Predicate("Testing " + expression + " is false");
        predicate.addClause(new ExpressionClause(table, expression, false));
        setNullStatusForColumns(predicate);

        requirements.add(predicate);
        return requirements;
    }

    @Override
    public List<Predicate> generateNullPredicates() {
        List<Predicate> requirements = new ArrayList<>();

        Predicate predicate = new Predicate("Testing " + expression + " is NULL");
        List<Column> columns = expression.getColumnsInvolved();
        if (columns.size() > 0) {
            setNullStatusForColumns(predicate);
            predicate.setColumnNullStatus(table, columns.get(0), true);
            requirements.add(predicate);
        }
        return requirements;
    }
}
