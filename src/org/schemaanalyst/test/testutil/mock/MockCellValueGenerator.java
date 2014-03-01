package org.schemaanalyst.test.testutil.mock;

import org.schemaanalyst.coverage.testgeneration.datageneration.valuegeneration.CellValueGenerator;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.NumericValue;

import java.util.List;

/**
 * Created by phil on 01/03/2014.
 */
public class MockCellValueGenerator extends CellValueGenerator {

    private int index;
    private List<Integer> cellValues;

    public MockCellValueGenerator(List<Integer> cellValues) {
        super(null, null, null, 0, 0, false);
        index = 0;
        this.cellValues = cellValues;
    }

    public void generateCellValue(Cell cell) {

        Integer cellValue = cellValues.get(index);

        if (cellValue == null) {
            cell.setNull(true);
        } else {
            cell.setValue(new NumericValue(cellValue));
        }

        index ++;
    }
}
