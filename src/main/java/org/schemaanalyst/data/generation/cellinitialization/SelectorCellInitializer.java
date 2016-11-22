package org.schemaanalyst.data.generation.cellinitialization;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.generation.cellvaluegeneration.SelectorCellValueGenerator;

public class SelectorCellInitializer extends CellInitializer {
    private SelectorCellValueGenerator cellValueGenerator;

    public SelectorCellInitializer(SelectorCellValueGenerator cellValueGenerator) {
        this.cellValueGenerator = cellValueGenerator;
    }

    @Override
    public void initialize(Cell cell) {
        cellValueGenerator.generateCellValue(cell);
    }
}
