package org.schemaanalyst.datageneration.search.objective.constraint;

import java.util.List;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.expression.RowExpressionObjectiveFunctionFactory;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionTree;

public class ExpressionObjectiveFunction extends ObjectiveFunction<Data> {

	protected ExpressionTree expressionTree;
	protected Table table;
	protected String description;
	protected boolean goalIsToSatisfy, allowNull;
		
	public ExpressionObjectiveFunction(ExpressionTree expressionTree,  
									   Table table,
									   String description, 
									   boolean goalIsToSatisfy, 
									   boolean allowNull) {
		this.expressionTree = expressionTree;
		this.table = table;
		this.description = description;
		this.goalIsToSatisfy = goalIsToSatisfy;
		this.allowNull = allowNull;
	}
	
	public ObjectiveValue evaluate(Data data) {				
		
		MultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);		
		RowExpressionObjectiveFunctionFactory factory = 
				new RowExpressionObjectiveFunctionFactory(expressionTree, 
														  goalIsToSatisfy, 
														  allowNull);
		ObjectiveFunction<Row> objFun = factory.create();
		
		List<Row> rows = data.getRows(table);		
		for (Row row : rows) {
			objVal.add(objFun.evaluate(row));
		}				
		
		return objVal;
	}
}
