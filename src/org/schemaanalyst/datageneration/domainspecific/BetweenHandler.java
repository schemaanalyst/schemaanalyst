package org.schemaanalyst.datageneration.domainspecific;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.OperandToValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.analyst.BetweenAnalyst;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.checkcondition.BetweenCheckCondition;
import org.schemaanalyst.sqlrepresentation.checkcondition.Operand;
import org.schemaanalyst.util.random.Random;

public class BetweenHandler extends ConstraintHandler<BetweenAnalyst> {
	
	protected boolean allowNull;
	protected CellRandomiser cellRandomizer;
	protected Random random;	
	
	protected BetweenCheckCondition between;
	protected Table table;
	protected Column column;
	protected Operand lower, upper;
	
	public BetweenHandler(BetweenAnalyst analyst,
						  boolean goalIsToSatisfy,
						  boolean allowNull,
						  CellRandomiser cellRandomizer,
						  Random random) {		
		super(analyst, goalIsToSatisfy);
		this.allowNull = allowNull;		
		this.cellRandomizer = cellRandomizer;
		this.random = random;
		
		between = analyst.getBetween();
		table = analyst.getTable();
		column = between.getColumn();
		lower = between.getLower();
		upper = between.getUpper();		
	}
		
	protected void attemptToSatisfy() {

		for (int rowNo : analyst.getFalsifyingEntries()) {
			Cell cell = data.getCell(table, column, rowNo);
			cellRandomizer.randomizeCell(cell, allowNull);
			
			if (!cell.isNull()) {
				if (random.nextBoolean()) {
					Value lowerValue = OperandToValue.convert(lower, data, rowNo);
					cell.setValue(lowerValue.duplicate());
				} else {	
					Value upperValue = OperandToValue.convert(upper, data, rowNo);
					cell.setValue(upperValue.duplicate());
				}
			}
		}
	}
	
	protected void attemptToFalsify() {
		
		for (int rowNo : analyst.getSatisfyingEntries()) {
			Cell cell = data.getCell(table, column, rowNo);
			cellRandomizer.randomizeCell(cell, allowNull);
			
			if (!cell.isNull()) {
				if (random.nextBoolean()) {
					Value lowerValue = OperandToValue.convert(lower, data, rowNo).duplicate();
					lowerValue.decrement();
					cell.setValue(lowerValue);
				} else {	
					Value upperValue = OperandToValue.convert(upper, data, rowNo).duplicate();
					upperValue.increment();
					cell.setValue(upperValue);
				}
			}
		}		
	}
}
