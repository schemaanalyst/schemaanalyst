package org.schemaanalyst.datageneration.search.objective.row;

import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;

import org.schemaanalyst.sqlrepresentation.expression.OrExpression;

public class OrExpressionObjectiveFunction extends ComposedExpressionObjectiveFunction {

    public OrExpressionObjectiveFunction(OrExpression expression,
            boolean goalIsToSatisfy,
            boolean allowNull) {
        super(expression, goalIsToSatisfy, allowNull);
    }

    @Override
    protected MultiObjectiveValue instantiateMultiObjectiveValue() {
        return new BestOfMultiObjectiveValue();
    }
}
