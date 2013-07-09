package org.schemaanalyst.datageneration.search.objective.expression;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.relationalpredicate.NullValueObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;

public class NullExpressionObjectiveFunction extends ObjectiveFunction<Row> {

	protected NullExpression expression;
	protected NullValueObjectiveFunction nullValueObjFun;
	protected ExpressionEvaluator subexpressionEvaluator;
	
	public NullExpressionObjectiveFunction(NullExpression expression, boolean goalIsToSatisfy) {
		this.expression = expression;
		
		//boolean shouldBeNull = goalIsToSatisfy && !expression.isNotNull();
		
		nullValueObjFun = new NullValueObjectiveFunction(!expression.isNotNull());
		subexpressionEvaluator = new ExpressionEvaluator(expression.getSubexpression());
	}
	
	public ObjectiveValue evaluate(Row row) {
		System.out.println("here");
		return nullValueObjFun.evaluate(subexpressionEvaluator.evaluate(row));
	}	
}
