package org.schemaanalyst.datageneration.search.objective.expression;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.sqlrepresentation.expression.CompoundExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

public abstract class ComposedExpressionObjectiveFunction extends ObjectiveFunction<Row> {

	protected CompoundExpression expression;
	protected List<ObjectiveFunction<Row>> subObjFuns;
		
	public ComposedExpressionObjectiveFunction(CompoundExpression expression,
										 	   boolean goalIsToSatisfy,
										 	   boolean allowNull) {
		this.expression = expression;
		subObjFuns = new ArrayList<>();
		for (Expression subexpression : expression.getSubexpressions()) {
			subObjFuns.add((new RowExpressionObjectiveFunctionFactory(
					subexpression, goalIsToSatisfy, allowNull)).create());
		}
	}

	public ObjectiveValue evaluate(Row row) {
		MultiObjectiveValue objVal = new BestOfMultiObjectiveValue();
		for (ObjectiveFunction<Row> objFun : subObjFuns) {
			objVal.add(objFun.evaluate(row));
		}
		return objVal;
	}
	
	protected abstract MultiObjectiveValue instantiateMultiObjectiveValue();
}
