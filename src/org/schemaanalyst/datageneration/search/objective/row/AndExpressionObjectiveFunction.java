package org.schemaanalyst.datageneration.search.objective.row;

import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.sqlrepresentation.expression.AndExpression;

public class AndExpressionObjectiveFunction extends ComposedExpressionObjectiveFunction {

    public AndExpressionObjectiveFunction(AndExpression expression,
                                          boolean goalIsToSatisfy, 
                                          boolean nullIsTrue) {
        super(expression, goalIsToSatisfy, nullIsTrue);
    }

    @Override
    protected MultiObjectiveValue instantiateMultiObjectiveValue() {
        return goalIsToSatisfy 
                ? new SumOfMultiObjectiveValue()
                : new BestOfMultiObjectiveValue();
    }
}
