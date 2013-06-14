package org.schemaanalyst.datageneration.search.objective.constraint;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.predicate.NullValueObjectiveFunction;
import org.schemaanalyst.representation.Column;

public class NullColumnObjectiveFunction extends ObjectiveFunction<Data> {
	
	protected Column column;
	protected String description;
	protected boolean goalIsToSatisfy;
	
	public NullColumnObjectiveFunction(Column column, 
									   String description, 
									   boolean goalIsToSatisfy) {
		this.column = column;
		this.description = description;
		this.goalIsToSatisfy = goalIsToSatisfy;		
	}
	
	public ObjectiveValue evaluate(Data data) {

		MultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);
		
		// NOTE: The database state (encapsulated by the state instance
		// variable can be ignored for the purposes of this objective 
		// function evaluation.  All the values in the state will obey 
		// the constraint and so do need to be checked.
		
		for (Cell cell : data.getCells(column)) {
			objVal.add(NullValueObjectiveFunction.compute(cell.getValue(), goalIsToSatisfy));			
		}
		
		return objVal;
	}	
}
