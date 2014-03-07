package org.schemaanalyst.test.testutil.mock;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;

import java.util.ArrayList;
import java.util.List;

public class MockCellRandomiser extends CellRandomiser {

    private List<Value> values;
    private int count;
    
    public MockCellRandomiser(Integer[] integerValues) {
        this();
        setIntegerValues(integerValues);
    }
    
    public MockCellRandomiser(List<Value> values) {
        this();
        setValues(values);
    }    
   
    public MockCellRandomiser() {
        super(null, 0, 0, 0, 0, 0, 0 ,0 ,0, 0, 0, 0, 0, 0, null, null, 0, 0, 0, 0, 0);
    }
    
    public void setIntegerValues(Integer[] integerValues) {
        List<Value> values = new ArrayList<>();
        for (Integer integerValue : integerValues) {
            values.add(new NumericValue(integerValue));
        }
        setValues(values);
    }
    
    public void setValues(List<Value> values) {
        this.values = values;
        count = 0;
    }
    
    public void randomiseCell(Cell cell, boolean allowNull) {
        if (count > values.size()) {
            count = 0;
        }
        cell.setValue(values.get(count));
        count ++;
    }
}
