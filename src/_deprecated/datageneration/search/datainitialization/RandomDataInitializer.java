package _deprecated.datageneration.search.datainitialization;

import _deprecated.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;

import java.util.List;

public class RandomDataInitializer extends DataInitialiser {

    protected CellRandomiser cellRandomizer;

    public RandomDataInitializer(CellRandomiser cellRandomizer) {
        this.cellRandomizer = cellRandomizer;
    }

    @Override
    public void initialize(Data data) {
        List<Cell> cells = data.getCells();
        for (Cell cell : cells) {
            cellRandomizer.randomiseCell(cell);
        }
    }
}
