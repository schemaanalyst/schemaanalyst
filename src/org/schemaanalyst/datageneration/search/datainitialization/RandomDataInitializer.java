package org.schemaanalyst.datageneration.search.datainitialization;

import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.cellrandomization.CellRandomizer;

public class RandomDataInitializer extends DataInitializer {

	protected CellRandomizer cellRandomizer;
	
	public RandomDataInitializer(CellRandomizer cellRandomizer) {
		this.cellRandomizer = cellRandomizer;
	}
	
	public void initialize(Data data) {
		List<Cell> cells = data.getCells();
		for (Cell cell : cells) {
			cellRandomizer.randomizeCell(cell);
		}
	}
}
