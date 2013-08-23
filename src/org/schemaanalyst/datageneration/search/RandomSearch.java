package org.schemaanalyst.datageneration.search;

import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;

public class RandomSearch extends Search<Data> {

    protected CellRandomiser profile;

    public RandomSearch(CellRandomiser profile) {
        super(new Data.Duplicator());        
        this.profile = profile;
    }

    @Override
    public void search(Data candidateSolution) {
        List<Cell> cells = candidateSolution.getCells();
        do {
            for (Cell cell : cells) {
                profile.randomiseCell(cell);
            }
            evaluate(candidateSolution);
        } while (!terminationCriterion.satisfied());
    }
}
