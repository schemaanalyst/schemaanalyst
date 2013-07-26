package org.schemaanalyst.datageneration.search.objective.row;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.value.NullValueObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;

public class NullExpressionObjectiveFunction extends ObjectiveFunction<Row> {

    protected NullValueObjectiveFunction nullValueObjFun;
    protected ExpressionEvaluator subexpressionEvaluator;

    public NullExpressionObjectiveFunction(NullExpression expression, boolean goalIsToSatisfy) {

        boolean shouldBeNull = goalIsToSatisfy != expression.isNotNull();        
        nullValueObjFun = new NullValueObjectiveFunction(shouldBeNull);
        subexpressionEvaluator = new ExpressionEvaluator(expression.getSubexpression());
    }

    @Override
    public ObjectiveValue evaluate(Row row) {
        return nullValueObjFun.evaluate(subexpressionEvaluator.evaluate(row));
    }
}
