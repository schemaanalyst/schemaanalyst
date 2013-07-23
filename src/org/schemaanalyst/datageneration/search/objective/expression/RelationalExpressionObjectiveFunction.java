package org.schemaanalyst.datageneration.search.objective.expression;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.relationalpredicate.ValueObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.logic.RelationalPredicate;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

public class RelationalExpressionObjectiveFunction extends ObjectiveFunction<Row> {

    protected ExpressionEvaluator lhs, rhs;
    protected RelationalOperator op;
    protected ValueObjectiveFunction valObjFun;

    public RelationalExpressionObjectiveFunction(RelationalExpression expression,
            boolean goalIsToSatisfy,
            boolean allowNull) {
        lhs = new ExpressionEvaluator(expression.getLHS());
        rhs = new ExpressionEvaluator(expression.getRHS());

        RelationalOperator op = expression.getRelationalOperator();
        this.op = goalIsToSatisfy ? op : op.inverse();

        valObjFun = new ValueObjectiveFunction();
    }

    public ObjectiveValue evaluate(Row row) {
        return valObjFun.evaluate(
                new RelationalPredicate<>(
                lhs.evaluate(row),
                op,
                rhs.evaluate(row)));
    }
}
