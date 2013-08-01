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
    private boolean allowNull;

    public ExpressionObjectiveFunction(Expression expression,
            String description,
            boolean goalIsToSatisfy,
            boolean allowNull) {
        super(description, goalIsToSatisfy);
        this.expression = expression;
        this.allowNull = allowNull;
    }

    @Override
    protected List<Row> getDataRows(Data data) {
        return data.getRows(expression.getColumnsInvolved());
    }
    
    @Override
    protected ObjectiveValue performEvaluation(List<Row> dataRows) {

        MultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);
        ExpressionObjectiveFunctionFactory factory =
                new ExpressionObjectiveFunctionFactory(expression,
                goalIsToSatisfy,
                allowNull);
        ObjectiveFunction<Row> objFun = factory.create();
        
        for (Row row : dataRows) {
            ObjectiveValue rowObjVal = objFun.evaluate(row); 
            objVal.add(rowObjVal);
            classifyRow(rowObjVal, row);
        }

        return objVal;
    }
}
