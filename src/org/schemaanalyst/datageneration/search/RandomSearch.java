package org.schemaanalyst.datageneration.search;

import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;

public class RandomSearch extends Search<Data>{
	
	protected CellRandomiser profile;
	
	public RandomSearch(CellRandomiser profile) {
		this.profile = profile;
	}

	public void search(Data candidateSolution) {
		List<Cell> cells = candidateSolution.getCells();
		do {
			for (Cell cell : cells) {
				profile.randomizeCell(cell);
			}
			evaluate(candidateSolution);
		} while (!terminationCriterion.satisfied());
	}
}
