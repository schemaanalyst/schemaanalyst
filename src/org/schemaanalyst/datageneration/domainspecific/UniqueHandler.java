package org.schemaanalyst.datageneration.domainspecific;

import java.util.List;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.analyst.UniqueAnalyst;
import org.schemaanalyst.datageneration.cellrandomization.CellRandomizer;
import org.schemaanalyst.util.random.Random;

public class UniqueHandler extends ConstraintHandler<UniqueAnalyst> {

	protected boolean allowNull;
	protected CellRandomizer randomizer;
	protected Random random;
	
	public UniqueHandler(UniqueAnalyst analyst,
						 boolean goalIsToSatisfy,
						 boolean allowNull,
						 CellRandomizer randomizer,
						 Random random) {		
		super(analyst, goalIsToSatisfy);
		this.allowNull = allowNull;
		this.randomizer = randomizer;
		this.random = random;
	}
		
	protected void attemptToSatisfy() {
		List<Row> nonUniqueRows = analyst.getNonUniqueRows();
		
		for (Row row : nonUniqueRows) {
			randomizer.randomizeCells(row, allowNull);
		}
	}
	
	protected void attemptToFalsify() {
		List<Row> uniqueRows = analyst.getUniqueRows();
		
		// the other rows we can pick a non-unique set of values from
		List<Row> otherRows = analyst.getNonUniqueRows();
		otherRows.addAll(analyst.getStateRows());
				
		// cycle through the unique rows, making them non-unique
		for (Row row : uniqueRows) {
			int numOtherRows = otherRows.size();
			
			if (numOtherRows > 0) {
				int maxIndex = numOtherRows-1;
				
				// pick a row at random
				int rowIndex = maxIndex > 0 
							 ? random.nextInt(maxIndex)
							 : 0;
				
				Row otherRow = otherRows.get(rowIndex);
				
				// copy those values into the row
				row.copyValues(otherRow);
			}
			otherRows.add(row);
		}
	}
}
