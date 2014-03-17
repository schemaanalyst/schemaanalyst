package org.schemaanalyst.data.generation.search.objective.row;

import org.schemaanalyst.data.generation.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.MultiObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.sqlrepresentation.expression.OrExpression;

public class OrExpressionRowObjectiveFunction extends
        ComposedExpressionRowObjectiveFunction {

    public OrExpressionRowObjectiveFunction(OrExpression expression,
            boolean goalIsToSatisfy, boolean allowNull) {
        super(expression, goalIsToSatisfy, allowNull);
    }

    @Override
    protected MultiObjectiveValue instantiateMultiObjectiveValue() {
        return goalIsToSatisfy 
                ? new BestOfMultiObjectiveValue()
                : new SumOfMultiObjectiveValue();
    }
}
