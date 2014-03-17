package _deprecated.datageneration.search.objective.row;

import _deprecated.datageneration.search.objective.ObjectiveFunction;
import _deprecated.datageneration.search.objective.ObjectiveValue;
import _deprecated.datageneration.search.objective.value.NullValueObjectiveFunction;
import org.schemaanalyst.data.Row;
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
