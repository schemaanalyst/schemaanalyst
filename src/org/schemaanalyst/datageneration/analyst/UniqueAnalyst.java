package org.schemaanalyst.datageneration.analyst;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.representation.Column;

public class UniqueAnalyst extends ConstraintAnalyst {

	protected List<Column> columns; 
	protected boolean satisfyOnNull;
	
	protected List<Row> dataRows, stateRows, allRows,
						uniqueRows, nonUniqueRows;	
	
	public UniqueAnalyst(List<Column> columns,
						 boolean satisfyOnNull) {
		
		this.columns = columns;
		this.satisfyOnNull = satisfyOnNull;
	}
	
	public boolean isSatisfied(Data state, Data data) {		
		// get data and state rows
		dataRows = data.getRows(columns);
		stateRows = state == null
					? new ArrayList<Row>()
					: state.getRows(columns);
		
		// merge data and state rows into one ArrayList
		allRows = new ArrayList<>();
		allRows.addAll(dataRows);
		allRows.addAll(stateRows);
					
		// initialize unique and non-unique rows list 
		uniqueRows = new ArrayList<>();
		nonUniqueRows = new ArrayList<>(); 

		// iterate through the data rows
		boolean satisfying = true;
		
		for (int i=0; i < dataRows.size(); i++) {
			Row dataRow = dataRows.get(i);			
			boolean rowSatisfying = isRowSatisfying(dataRow, allRows, i+1);
			
			if (rowSatisfying) {
				uniqueRows.add(dataRow);
			} else {
				nonUniqueRows.add(dataRow);
				satisfying = false;
			}
		}
		
		return satisfying;
	}
	
	protected boolean isRowSatisfying(Row dataRow, List<Row> compareRows, int index) {

		ListIterator<Row> otherRowsIterator = compareRows.listIterator(index);
		
		while (otherRowsIterator.hasNext()) {
			Row compareRow = otherRowsIterator.next();
			Boolean rowsEqual = dataRow.valuesEqual3VL(compareRow);
			
			if (Boolean.TRUE.equals(rowsEqual) || (rowsEqual == null && !satisfyOnNull)) {
				return false;
			}
		}
		
		return true;
	}
	
	public List<Row> getUniqueRows() {
		return uniqueRows;
	}
	
	public List<Row> getNonUniqueRows() {
		return nonUniqueRows;
	}
	
	public List<Row> getStateRows() {
		return stateRows;
	}
}
