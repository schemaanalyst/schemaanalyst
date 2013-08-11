package org.schemaanalyst.datageneration.search.objective.row;

import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.sqlrepresentation.expression.AndExpression;

public class AndExpressionRowObjectiveFunction extends ComposedExpressionRowObjectiveFunction {

    public AndExpressionRowObjectiveFunction(AndExpression expression,
                                          boolean goalIsToSatisfy, 
                                          boolean allowNull) {
        super(expression, goalIsToSatisfy, allowNull);
    }

    @Override
    protected MultiObjectiveValue instantiateMultiObjectiveValue() {
        return goalIsToSatisfy 
                ? new SumOfMultiObjectiveValue()
                : new BestOfMultiObjectiveValue();
    }
}
