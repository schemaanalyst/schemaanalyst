package org.schemaanalyst.data.generation.search.objective.row;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.sqlrepresentation.expression.ParenthesisedExpression;

public class ParenthesisedExpressionRowObjectiveFunction extends ObjectiveFunction<Row> {

    protected ObjectiveFunction<Row> subObjFun;

    public ParenthesisedExpressionRowObjectiveFunction(ParenthesisedExpression expression, 
                                                    boolean goalIsToSatisfy,
                                                    boolean allowNull) {
        
        subObjFun = (new ExpressionRowObjectiveFunctionFactory(
                expression.getSubexpression(), goalIsToSatisfy, allowNull)).create();
    }

    @Override
    public ObjectiveValue evaluate(Row row) {
        return subObjFun.evaluate(row);
    }
}
