package org.schemaanalyst.datageneration.search.datainitialization;

import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;

public class RandomDataInitializer extends DataInitializer {

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
