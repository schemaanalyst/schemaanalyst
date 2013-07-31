package org.schemaanalyst.datageneration.search.objective.data;

import java.util.List;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.row.ExpressionObjectiveFunctionFactory;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

public class ExpressionObjectiveFunction extends ConstraintObjectiveFunction {

    private Expression expression;
    private String description;
    private boolean goalIsToSatisfy, allowNull;

    public ExpressionObjectiveFunction(Expression expression,
            String description,
            boolean goalIsToSatisfy,
            boolean allowNull) {
        this.expression = expression;
        this.description = description;
        this.goalIsToSatisfy = goalIsToSatisfy;
        this.allowNull = allowNull;
    }

    @Override
    protected ObjectiveValue performEvaluation(Data data) {

        MultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);
        ExpressionObjectiveFunctionFactory factory =
                new ExpressionObjectiveFunctionFactory(expression,
                goalIsToSatisfy,
                allowNull);
        ObjectiveFunction<Row> objFun = factory.create();
        
        List<Row> rows = data.getRows(expression.getColumnsInvolved());
        for (Row row : rows) {
            ObjectiveValue rowObjVal = objFun.evaluate(row); 
            objVal.add(rowObjVal);
            classifyRow(rowObjVal, row);
        }

        return objVal;
    }
}
