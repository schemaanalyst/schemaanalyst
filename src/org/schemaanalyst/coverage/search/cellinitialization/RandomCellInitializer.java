package org.schemaanalyst.coverage.search.cellinitialization;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;

/**
 * Created by phil on 31/01/2014.
 */
public class RandomCellInitializer extends CellInitializer {

    protected CellRandomiser cellRandomizer;

    public RandomCellInitializer(CellRandomiser cellRandomizer) {
        this.cellRandomizer = cellRandomizer;
    }

    @Override
    public void initialize(Cell cell) {
        cellRandomizer.randomiseCell(cell);
    }
}
