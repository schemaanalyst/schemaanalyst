package _deprecated.datageneration.search;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import _deprecated.datageneration.cellrandomisation.CellRandomiser;

import java.util.List;

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
