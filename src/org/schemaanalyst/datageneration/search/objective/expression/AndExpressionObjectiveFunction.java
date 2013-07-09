package org.schemaanalyst.datageneration.search.objective.expression;

import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.sqlrepresentation.expression.OrExpression;

public class AndExpressionObjectiveFunction extends ComposedExpressionObjectiveFunction {

	public AndExpressionObjectiveFunction(OrExpression expression,
										 boolean goalIsToSatisfy,
										 boolean allowNull) {
		super(expression, goalIsToSatisfy, allowNull);
	}

	protected MultiObjectiveValue instantiateMultiObjectiveValue() {
		return new SumOfMultiObjectiveValue();
	}
}
