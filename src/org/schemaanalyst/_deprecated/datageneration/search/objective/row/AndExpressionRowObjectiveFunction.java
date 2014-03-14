package org.schemaanalyst._deprecated.datageneration.search.objective.row;

import org.schemaanalyst._deprecated.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst._deprecated.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst._deprecated.datageneration.search.objective.SumOfMultiObjectiveValue;
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
