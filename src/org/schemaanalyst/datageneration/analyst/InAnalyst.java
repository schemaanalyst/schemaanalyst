package org.schemaanalyst.datageneration.analyst;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.representation.Table;
import org.schemaanalyst.representation.expression.InExpression;

public class InAnalyst extends ConstraintAnalyst {

	protected InExpression in;
	protected Table table;
	protected boolean allowNull;
		
	protected List<Cell> inCells;
	protected List<Cell> notInCells;
	protected List<Value> inValues;
	
	public InAnalyst(InExpression in, 
				     Table table, 
					 boolean allowNull) {
		this.in = in;
		this.table = table;
		this.allowNull = allowNull;
	}
	
	public boolean isSatisfied(Data state, Data data) {
		inCells = new ArrayList<>();
		notInCells = new ArrayList<>();
		
		List<Cell> cells = data.getCells(in.getColumn());
		inValues = in.getValues();
		
		boolean isSatisfying = true;
		
		for (Cell cell : cells) {
			Value columnValue = cell.getValue();
			
			boolean valueSatisfies = false;
			
			for (Value inValue : inValues) {
				Boolean valuesEqual = Value.equals3VL(columnValue, inValue);
				
				if (Boolean.TRUE.equals(valuesEqual) 
						|| (valuesEqual == null && allowNull)) {
					valueSatisfies = true;
					break;
				}
				
			}	
			
			if (valueSatisfies) {
				inCells.add(cell);
			} else {
				notInCells.add(cell);
				isSatisfying = false;
			}			
		}		
		
		return isSatisfying;
	}

	public List<Cell> getInCells() {
		return inCells;
	}
	
	public List<Value> getInValues() {
		return inValues;
	}
	
	public List<Cell> getNotInCells() {
		return notInCells;
	}
}
