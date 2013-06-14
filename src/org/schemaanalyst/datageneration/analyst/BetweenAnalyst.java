package org.schemaanalyst.datageneration.analyst;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.OperandToValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.logic.EvaluableRelationalPredicate;
import org.schemaanalyst.representation.Column;
import org.schemaanalyst.representation.Table;
import org.schemaanalyst.representation.expression.BetweenExpression;
import org.schemaanalyst.representation.expression.Operand;

public class BetweenAnalyst extends ConstraintAnalyst {

	protected BetweenExpression between; 
	protected Table table;
	protected boolean satisfyOnNull;	
	
	protected List<Integer> satisfyingRowNos;
	protected List<Integer> falsifyingRowNos;
		
	protected Data data;
		
	public BetweenAnalyst(BetweenExpression between, 
						  Table table,
						  boolean satisfyOnNull) {
		this.between = between;
		this.table = table;
		this.satisfyOnNull = satisfyOnNull;	
	}

	public boolean isSatisfied(Data state, Data data) {
		this.data = data;
		this.satisfyingRowNos = new ArrayList<>();
		this.falsifyingRowNos = new ArrayList<>();

		Operand lower = between.getLower();
		Operand upper = between.getUpper();				
		Column column = between.getColumn();
		
		boolean isSatisfied = true;
		
		for (int rowNo=0; rowNo < data.getNumRows(table); rowNo++) {
		
			Value lowerValue = OperandToValue.convert(lower, data, rowNo);
			Value upperValue = OperandToValue.convert(upper, data, rowNo);
			Value columnValue = OperandToValue.convert(column, data, rowNo);
			
			boolean rowSatisfying =  rowSatisfies(lowerValue, upperValue, columnValue);
			
			if (rowSatisfying) {
				satisfyingRowNos.add(rowNo);
			} else {
				falsifyingRowNos.add(rowNo);
				isSatisfied = false;
			}
		}

		return isSatisfied;
	}

	protected boolean rowSatisfies(Value lowerValue, Value upperValue, Value columnValue) {
		Boolean lowerBoundSatisfied 
			= new EvaluableRelationalPredicate<Value>(lowerValue, "<=", columnValue).isSatisfied3VL();
		
		Boolean upperBoundSatisfied
			= new EvaluableRelationalPredicate<Value>(columnValue, "<=", upperValue).isSatisfied3VL();
		
		if (lowerBoundSatisfied == null || upperBoundSatisfied == null) {
			return satisfyOnNull;
		} else {
			return lowerBoundSatisfied && upperBoundSatisfied;
		}
	}
	
	public Data getData() {
		return data;
	}
	
	public Table getTable() {
		return table;
	}
	
	public BetweenExpression getBetween() {
		return between;
	}
	
	public List<Integer> getSatisfyingEntries() {
		return satisfyingRowNos;
	}
	
	public List<Integer> getFalsifyingEntries() {
		return falsifyingRowNos;
	}
}
