package org.schemaanalyst.datageneration.search.objective.expression;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionAdapter;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;
import org.schemaanalyst.sqlrepresentation.expression.OrExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

public class RowExpressionObjectiveFunctionFactory {

    protected Expression expression;
    protected boolean goalIsToSatisfy, allowNull;

    public RowExpressionObjectiveFunctionFactory(Expression expression,
            boolean goalIsToSatisfy,
            boolean allowNull) {
        this.expression = expression;
        this.goalIsToSatisfy = goalIsToSatisfy;
        this.allowNull = allowNull;
    }

    public ObjectiveFunction<Row> create() {

        class ExpressionDispatcher extends ExpressionAdapter {

            ObjectiveFunction<Row> objFun;

            ObjectiveFunction<Row> dispatch() {
                objFun = null;
                expression.accept(this);
                return objFun;
            }

            @Override
            public void visit(NullExpression expression) {
                objFun = new NullExpressionObjectiveFunction(
                        expression, goalIsToSatisfy);
            }

            @Override
            public void visit(OrExpression expression) {
                objFun = new OrExpressionObjectiveFunction(
                        expression, goalIsToSatisfy, allowNull);
            }

            @Override
            public void visit(RelationalExpression expression) {
                objFun = new RelationalExpressionObjectiveFunction(
                        expression, goalIsToSatisfy, allowNull);
            }
        }

        return (new ExpressionDispatcher()).dispatch();
    }
}
