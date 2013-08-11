package org.schemaanalyst.datageneration.search.objective.row;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.value.NullValueObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;

public class NullExpressionRowObjectiveFunction extends ObjectiveFunction<Row> {

    private ExpressionEvaluator subexpressionEvaluator;
    private boolean allowNull;
    
    public NullExpressionRowObjectiveFunction(NullExpression expression, boolean goalIsToSatisfy) {
        allowNull = goalIsToSatisfy != expression.isNotNull();            
        subexpressionEvaluator = new ExpressionEvaluator(expression.getSubexpression());
    }

    @Override
    public ObjectiveValue evaluate(Row row) {
        return NullValueObjectiveFunction.compute(subexpressionEvaluator.evaluate(row), allowNull);
    }
}
