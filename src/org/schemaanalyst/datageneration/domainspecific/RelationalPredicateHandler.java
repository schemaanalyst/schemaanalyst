package org.schemaanalyst.datageneration.domainspecific;

import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.OperandToValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.analyst.RelationalPredicateAnalyst;
import org.schemaanalyst.datageneration.cellrandomization.CellRandomizer;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.logic.RelationalPredicate;
import org.schemaanalyst.representation.Column;
import org.schemaanalyst.representation.Table;
import org.schemaanalyst.representation.checkcondition.Operand;
import org.schemaanalyst.util.random.Random;

import static org.schemaanalyst.logic.RelationalOperator.GREATER;
import static org.schemaanalyst.logic.RelationalOperator.LESS;
import static org.schemaanalyst.logic.RelationalOperator.NOT_EQUALS;

public class RelationalPredicateHandler extends ConstraintHandler<RelationalPredicateAnalyst> {
	
	protected boolean allowNull;
	protected CellRandomizer cellRandomizer;
	protected Random random;
	
	protected RelationalPredicate<Operand> predicate;
	
	public RelationalPredicateHandler(RelationalPredicateAnalyst analyst,
						  			  boolean goalIsToSatisfy,
						  			  boolean allowNull,
						  			  CellRandomizer cellRandomizer,
						  			  Random random) {		
		super(analyst, goalIsToSatisfy);
		this.allowNull = allowNull;		
		this.cellRandomizer = cellRandomizer;
		this.random = random;
		
		this.predicate = analyst.getPredicate();
	}
		
	protected void attemptToSatisfy() {
		attemptEntries(analyst.getFalsifyingEntries(), predicate.getOperator());
	}
	
	protected void attemptToFalsify() {
		attemptEntries(analyst.getSatisfyingEntries(), predicate.getOperator().inverse());
	}
	
	protected void attemptEntries(List<Integer> rowNos, RelationalOperator op) {
		Data data = analyst.getData();
		Table table = analyst.getTable();
		
		Column lhsColumn = null, rhsColumn = null;
		
		// one of the operands has to be a column
		Operand lhs = predicate.getLHS();
		if (lhs instanceof Column) {
			lhsColumn = (Column) lhs;
		}
		
		Operand rhs = predicate.getRHS();
		if (rhs instanceof Column) {
			rhsColumn = (Column) rhs;
		}
		
		if (lhsColumn != null || rhsColumn != null) {		
			
			for (int rowNo : rowNos) {			
				boolean doLHS = lhsColumn != null;
				boolean doRHS = rhsColumn != null;
				
				if (doLHS && doRHS) {
					// if both LHS and RHS columns are available, 
					// choose one at random
					if (random.nextBoolean()) {
						doLHS = false;
					} else {
						doRHS = false;
					}
				}
				
				if (doLHS) {
					Cell cell = data.getCell(table, lhsColumn, rowNo);
					Value value = OperandToValue.convert(rhs, data, rowNo);
					adjustLHSCell(cell, op, value);
				
				} 
				
				if (doRHS) {
					Value value = OperandToValue.convert(lhs, data, rowNo);
					Cell cell = data.getCell(table, rhsColumn, rowNo);
					adjustRHSCell(value, op, cell);				
				}
			}	
		}
	}
	
	protected void adjustLHSCell(Cell cell, RelationalOperator op, Value value) {		
		cellRandomizer.randomizeCell(cell, allowNull);
		
		if (!cell.isNull() && op != NOT_EQUALS) {
			Value newValue = value.duplicate();
			
			if (op == GREATER) {
				newValue.increment();	
			} 
			
			if (op == LESS) {
				newValue.decrement();	
			}
			
			cell.setValue(newValue);
		}
	}
	
	protected void adjustRHSCell(Value value, RelationalOperator op, Cell cell) {
		switch (op) {
		case GREATER:
		case GREATER_OR_EQUALS:
		case LESS:
		case LESS_OR_EQUALS:
			op = op.inverse();
			break;
		default:
			break;
		}
		
		adjustLHSCell(cell, op, value);
	}
}
