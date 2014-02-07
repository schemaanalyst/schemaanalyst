package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.coverage.criterion.clause.ExpressionClause;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 07/02/2014.
 */
public class InExpressionRACRequirementsGenerator extends ExpressionRACRequirementsGenerator {

    private InExpression inExpression;
    private Expression lhs;
    private ListExpression listExpression;

    public InExpressionRACRequirementsGenerator(Schema schema, Table table, Predicate predicate, InExpression inExpression) {
        super(schema, table, predicate);
        this.inExpression = inExpression;
        this.lhs = inExpression.getLHS();
        this.listExpression = (ListExpression) inExpression.getRHS();
    }

    @Override
    public List<Predicate> generateTruePredicates() {
        List<Predicate> requirements = new ArrayList<>();

        for (Expression element : listExpression.getSubexpressions()) {
            Expression equalsExpression = new RelationalExpression(lhs, RelationalOperator.EQUALS, element);
            Predicate predicate = generatePredicate("Testing " + lhs + " = " + element);
            predicate.addClause(new ExpressionClause(table, equalsExpression, true));
            requirements.add(predicate);
        }

        return requirements;
    }

    @Override
    public List<Predicate> generateFalsePredicates() {
        List<Predicate> requirements = new ArrayList<>();

        Predicate predicate = generatePredicate("Testing " + inExpression + " is false");
        for (Expression element : listExpression.getSubexpressions()) {
            Expression notEqualsExpression = new RelationalExpression(lhs, RelationalOperator.NOT_EQUALS, element);
            predicate.addClause(new ExpressionClause(table, notEqualsExpression, true));
        }
        requirements.add(predicate);

        return requirements;
    }
}
