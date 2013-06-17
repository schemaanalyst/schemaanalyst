package org.schemaanalyst.datageneration.search.objective.constraint;

import static org.schemaanalyst.logic.RelationalOperator.EQUALS;
import static org.schemaanalyst.logic.RelationalOperator.NOT_EQUALS;

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
import org.schemaanalyst.representation.Table;
import org.schemaanalyst.representation.checkcondition.InCheckCondition;

public class InCheckPredicateObjectiveFunction extends ObjectiveFunction<Data> {

	protected InCheckCondition in;
	protected Table table;
	protected Data state;
	protected String description;
	protected boolean goalIsToSatisfy, allowNull;
		
	public InCheckPredicateObjectiveFunction(InCheckCondition in, 
										     Table table, 
										     Data state, 
										     String description, 
										     boolean goalIsToSatisfy, 
										     boolean allowNull) {
		this.in = in;
		this.table = table;
		this.state = state;
		this.description = description;
		this.goalIsToSatisfy = goalIsToSatisfy;
		this.allowNull = allowNull;
	}
	
	public ObjectiveValue evaluate(Data data) {
		MultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);
		
		for (int i=0; i < data.getNumRows(table); i++) {
			Value columnValue = OperandToValue.convert(in.getColumn(), data, i);
			objVal.add(evaluateRow(columnValue, allowNull));
		}
		
		return objVal;
	}	
	
	protected ObjectiveValue evaluateRow(Value columnValue, boolean allowNull) {
		
		MultiObjectiveValue rowObjVal;
		
		if (allowNull) {
		
			rowObjVal = new BestOfMultiObjectiveValue("Allowing for nulls");
			
			rowObjVal.add(evaluateRow(columnValue, false));
			rowObjVal.add(NullValueObjectiveFunction.compute(columnValue, true));
			
			for (Value inValue : in.getValues()) {
				rowObjVal.add(NullValueObjectiveFunction.compute(inValue, true));				
			}
		
		} else { 
			String description = "Evaluating value with respect to IN values";
			
			rowObjVal = goalIsToSatisfy 
					? new BestOfMultiObjectiveValue(description)
					: new SumOfMultiObjectiveValue(description);
			
			RelationalOperator op = goalIsToSatisfy ? EQUALS : NOT_EQUALS;
						
			for (Value inValue : in.getValues()) {
				rowObjVal.add(ValueObjectiveFunction.compute(columnValue, op, inValue));				
			}
		}
		
		return rowObjVal;		
	}

}
