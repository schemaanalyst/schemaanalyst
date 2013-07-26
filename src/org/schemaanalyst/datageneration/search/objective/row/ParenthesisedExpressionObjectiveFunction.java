package org.schemaanalyst.datageneration.search.objective.row;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.sqlrepresentation.expression.ParenthesisedExpression;

public class ParenthesisedExpressionObjectiveFunction extends ObjectiveFunction<Row> {

    protected ObjectiveFunction<Row> subObjFun;

    public ParenthesisedExpressionObjectiveFunction(ParenthesisedExpression expression, 
                                                    boolean goalIsToSatisfy,
                                                    boolean allowNull) {
        
        subObjFun = (new ExpressionObjectiveFunctionFactory(
                expression.getSubexpression(), goalIsToSatisfy, allowNull)).create();
    }

    @Override
    public ObjectiveValue evaluate(Row row) {
        return subObjFun.evaluate(row);
    }
}
