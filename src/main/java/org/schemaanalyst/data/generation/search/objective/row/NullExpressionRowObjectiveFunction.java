package org.schemaanalyst.data.generation.search.objective.row;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.value.NullValueObjectiveFunction;
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
