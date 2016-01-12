package org.schemaanalyst.data.generation.cellinitialization;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;

/**
 * Created by phil on 17/03/2014.
 */
public class RandomCellInitializer extends CellInitializer {

    private RandomCellValueGenerator cellValueGenerator;

    public RandomCellInitializer(RandomCellValueGenerator cellValueGenerator) {
        this.cellValueGenerator = cellValueGenerator;
    }

    @Override
    public void initialize(Cell cell) {
        cellValueGenerator.generateCellValue(cell);
    }
}
