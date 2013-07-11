package org.schemaanalyst.datageneration.domainspecific;

import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.analyst.InAnalyst;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.util.random.Random;

public class InHandler extends ConstraintHandler<InAnalyst> {
	
	protected boolean allowNull;
	protected CellRandomiser randomizer;
	protected Random random;
	 
	public InHandler(InAnalyst analyst,
					 boolean goalIsToSatisfy,
			 		 boolean allowNull,
			 		 CellRandomiser randomizer,
			 		 Random random) {		
		
		super(analyst, goalIsToSatisfy);
		this.allowNull = allowNull;
		this.randomizer = randomizer;
		this.random = random;
	}
		
	protected void attemptToSatisfy() {
		List<Cell> notInCells = analyst.getNotInCells();
		List<Value> inValues = analyst.getInValues();
		
		for (Cell cell : notInCells) {
			int randomIndex = random.nextInt(inValues.size()-1);
			cell.setValue(inValues.get(randomIndex));
		}
	}
	
	protected void attemptToFalsify() {
		for (Cell cell : analyst.getInCells()) {
			randomizer.randomizeCell(cell, allowNull);
		}
	}
}
