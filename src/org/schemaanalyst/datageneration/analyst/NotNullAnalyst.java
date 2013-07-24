package org.schemaanalyst.datageneration.analyst;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.sqlrepresentation.Column;

public class NotNullAnalyst extends ConstraintAnalyst {

    protected Column column;
    protected List<Cell> nullCells, notNullCells;

    public NotNullAnalyst(Column column) {
        this.column = column;
    }

    @Override
    public boolean isSatisfied(Data state, Data data) {
        nullCells = new ArrayList<>();
        notNullCells = new ArrayList<>();

        boolean isSatisfying = true;

        for (Cell cell : data.getCells(column)) {
            if (cell.isNull()) {
                isSatisfying = false;
                nullCells.add(cell);
            } else {
                notNullCells.add(cell);
            }
        }

        return isSatisfying;
    }

    public List<Cell> getNullCells() {
        return nullCells;
    }

    public List<Cell> getNotNullCells() {
        return notNullCells;
    }
}
