package org.schemaanalyst.coverage.criterion.requirements.expression;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.coverage.criterion.clause.ExpressionClause;
import org.schemaanalyst.logic.RelationalOperator;
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

    private InExpression inExpression;
    private Expression lhs;
    private List<Expression> subexpressions;

    public InExpressionCACPredicatesGenerator(Table table, InExpression inExpression) {
        super(table);

        this.inExpression = inExpression;
        this.lhs = inExpression.getLHS();
        this.subexpressions = inExpression.getRHS().getSubexpressions();
    }

    @Override
    public List<Predicate> generateTruePredicates() {
        List<Predicate> predicates = new ArrayList<>();

        for (Expression element : subexpressions) {
            Expression equalsExpression = new RelationalExpression(lhs, RelationalOperator.EQUALS, element);
            Predicate predicate = new Predicate("Testing " + lhs + " = " + element);
            predicate.addClause(new ExpressionClause(table, equalsExpression, true));
            predicates.add(predicate);
        }

        return predicates;
    }

    @Override
    public List<Predicate> generateFalsePredicates() {
        List<Predicate> predicates = new ArrayList<>();

        Predicate predicate = new Predicate("Testing " + inExpression + " is false");
        for (Expression element : subexpressions) {
            Expression notEqualsExpression = new RelationalExpression(lhs, RelationalOperator.NOT_EQUALS, element);
            predicate.addClause(new ExpressionClause(table, notEqualsExpression, true));
        }
        predicates.add(predicate);

        return predicates;
    }
}
