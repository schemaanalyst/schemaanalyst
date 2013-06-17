package org.schemaanalyst.datageneration.analyst;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.OperandToValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.logic.EvaluableRelationalPredicate;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.logic.RelationalPredicate;
import org.schemaanalyst.representation.Table;
import org.schemaanalyst.representation.checkcondition.Operand;

public class RelationalPredicateAnalyst extends ConstraintAnalyst {

	protected RelationalPredicate<Operand> predicate;
	protected Table table;
	protected boolean satisfyOnNull;
	
	protected List<Integer> satisfyingRowNos;
	protected List<Integer> falsifyingRowNos;
		
	protected Data data;
	
	public RelationalPredicateAnalyst(RelationalPredicate<Operand> predicate, 
									  Table table, 
									  boolean satisfyOnNull) {
		this.predicate = predicate;
		this.table = table;
		this.satisfyOnNull = satisfyOnNull;
	}
	
	public boolean isSatisfied(Data state, Data data) {
		this.data = data;
		this.satisfyingRowNos = new ArrayList<>();
		this.falsifyingRowNos = new ArrayList<>();

		Operand lhs = predicate.getLHS();
		Operand rhs = predicate.getRHS();				
		RelationalOperator op = predicate.getOperator();
		
		boolean isSatisfied = true;
		
		for (int rowNo=0; rowNo < data.getNumRows(table); rowNo++) {
		
			Value lhsValue = OperandToValue.convert(lhs, data, rowNo);
			Value rhsValue = OperandToValue.convert(rhs, data, rowNo);
			
			if (rowSatisfies(op, lhsValue, rhsValue)) {
				satisfyingRowNos.add(rowNo);
			} else {
				falsifyingRowNos.add(rowNo);
				isSatisfied = false;
			}
		}

		return isSatisfied;
	}

	protected boolean rowSatisfies(RelationalOperator op, Value lhsValue, Value rhsValue) {
		Boolean rowSatisfied = 
				new EvaluableRelationalPredicate<Value>(lhsValue, op, rhsValue)
							.isSatisfied3VL();
		
		if (rowSatisfied == null) {
			return satisfyOnNull;
		} 
		
		return rowSatisfied;
	}
	
	public Data getData() {
		return data;
	}
	
	public Table getTable() {
		return table;
	}
	
	public RelationalPredicate<Operand> getPredicate() {
		return predicate;
	}
	
	public List<Integer> getSatisfyingEntries() {
		return satisfyingRowNos;
	}
	
	public List<Integer> getFalsifyingEntries() {
		return falsifyingRowNos;
	}	
}
