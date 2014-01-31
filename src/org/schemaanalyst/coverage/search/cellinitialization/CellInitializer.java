package org.schemaanalyst.coverage.search.cellinitialization;

import org.schemaanalyst.data.Cell;

import java.util.List;

/**
 * Created by phil on 31/01/2014.
 */
public abstract class CellInitializer {

    public abstract void initialize(Cell cell);

    public void initialize(List<Cell> cells) {
        for (Cell cell : cells) {
            initialize(cell);
        }
    }
}

