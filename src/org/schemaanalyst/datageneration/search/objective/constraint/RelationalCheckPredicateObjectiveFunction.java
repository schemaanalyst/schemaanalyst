package org.schemaanalyst.datageneration.search.objective.constraint;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.OperandToValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.predicate.NullValueObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.predicate.ValueObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.checkcondition.RelationalCheckCondition;

public class RelationalCheckPredicateObjectiveFunction extends ObjectiveFunction<Data> {
	
	protected RelationalCheckCondition relation;
	protected Table table;
	protected Data state;
	protected String description;
	protected boolean goalIsToSatisfy, allowNull;
		
	public RelationalCheckPredicateObjectiveFunction(RelationalCheckCondition relation, 
													 Table table, 
													 Data state, 
													 String description, 
													 boolean goalIsToSatisfy, 
													 boolean allowNull) {			
		this.relation = relation;
		this.table = table;
		this.state = state;		
		this.description = description;
		this.goalIsToSatisfy = goalIsToSatisfy;
		this.allowNull = allowNull;
	}
	
	public ObjectiveValue evaluate(Data data) {
		MultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);
		
		for (int i=0; i < data.getNumRows(table); i++) {
		
			Value lhsValue = OperandToValue.convert(relation.getLHS(), data, i);
			Value rhsValue = OperandToValue.convert(relation.getRHS(), data, i);
	
			RelationalOperator op = relation.getOperator();
			if (!goalIsToSatisfy) {
				op = op.inverse();
			}
		
			objVal.add(evaluateRow(lhsValue, op, rhsValue, allowNull));
		}
		
		return objVal;
	}	
	
	protected ObjectiveValue evaluateRow(Value lhsValue, RelationalOperator op, Value rhsValue, boolean evaluateNull) {		
		if (evaluateNull) {		
			MultiObjectiveValue rowObjVal = new BestOfMultiObjectiveValue("Allowing for nulls");
			
			rowObjVal.add(evaluateRow(lhsValue, op, rhsValue, false));
			rowObjVal.add(NullValueObjectiveFunction.compute(lhsValue, true));
			rowObjVal.add(NullValueObjectiveFunction.compute(rhsValue, true));
	
			return rowObjVal;
		} else {
			return ValueObjectiveFunction.compute(lhsValue, op, rhsValue);
		}
	}
}
