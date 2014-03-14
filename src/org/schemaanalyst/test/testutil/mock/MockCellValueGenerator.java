package org.schemaanalyst.test.testutil.mock;

import org.schemaanalyst.data.generation.valuegeneration.CellValueGenerator;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.NumericValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 01/03/2014.
 */
public class MockCellValueGenerator extends CellValueGenerator {

    private int index;
    private List<Integer> cellValues;

    private MockCellValueGenerator() {
        super(null, null, null, 0, 0);
        index = 0;
    }

    public MockCellValueGenerator(int[] cellValues) {
        this();
        this.cellValues = new ArrayList<>();
        for (int cellValue : cellValues) {
            this.cellValues.add(cellValue);
        }
    }

    public MockCellValueGenerator(List<Integer> cellValues) {
        this();
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
