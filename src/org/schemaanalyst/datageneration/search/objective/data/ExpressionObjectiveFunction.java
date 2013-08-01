package org.schemaanalyst.datageneration.search.objective.data;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.row.ExpressionObjectiveFunctionFactory;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

public class ExpressionObjectiveFunction extends ConstraintObjectiveFunction {

    private ObjectiveFunction<Row> rowObjFun;

    /**
     * Objective function for an expression (typically embedded in a CHECK
     * constraint).
     * 
     * @param expression
     *            The expression on which the objective function is to be based.
     * @param description
     *            Some descriptive text to describe the objective function.
     * @param goalIsToSatisfy
     *            If set to true, the goal of the objective function will be to
     *            produce rows that all satisfy the constraint, else the goal is
     *            to produce rows that all falsify the constraint.
     * @param nullAdmissableForSatisfy
     *            If set to true, NULL values are permissible to satisfy the
     *            constraint, else NULL is permissible to falsify the
     *            constraint.
     */
    public ExpressionObjectiveFunction(Expression expression,
            String description, boolean goalIsToSatisfy,
            boolean nullAdmissableForSatisfy) {

        super(expression.getColumnsInvolved(), description, goalIsToSatisfy);

        ExpressionObjectiveFunctionFactory factory = new ExpressionObjectiveFunctionFactory(
                expression, goalIsToSatisfy,
                goalIsToSatisfy ? nullAdmissableForSatisfy
                        : !nullAdmissableForSatisfy);
        rowObjFun = factory.create();
    }

    @Override
    protected ObjectiveValue evaluateRow(Row row) {
        return rowObjFun.evaluate(row);
    }
}
