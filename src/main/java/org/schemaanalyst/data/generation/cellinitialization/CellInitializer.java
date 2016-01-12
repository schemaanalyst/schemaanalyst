package org.schemaanalyst.data.generation.cellinitialization;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;

import java.util.List;

/**
 * Created by phil on 17/03/2014.
 */
public abstract class CellInitializer {

    public void initialize(Data data) {
        initialize(data.getCells());
    }

    public void initialize(List<Cell> cells) {
        for (Cell cell : cells) {
            initialize(cell);
        }
    }

    public abstract void initialize(Cell cell);
}
