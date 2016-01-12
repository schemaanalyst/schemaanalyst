package org.schemaanalyst.unittest.testutil.mock;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.List;

public abstract class MockDatabase {

    public Table table;
    public Data state, data;
    protected int numColumns;
    
    public MockDatabase(int numColumns) {
        this.numColumns = numColumns;
    }
    
    public Data createState(int rows) {
        state = instantiateDataObject(rows);
        return state;
    }

    public Data createData(int rows) {
        data = instantiateDataObject(rows);
        return data;
    }

    protected Data instantiateDataObject(int rows) {
        ValueFactory valueFactory = new ValueFactory();
        Data data = new Data();
        for (int i = 0; i < rows; i++) {
            data.addRow(table, valueFactory);
        }
        return data;
    }

    public void setStateValues(Integer... values) {
        if (state == null) {
            createState((int) Math.ceil((values.length / (double) numColumns)));
        }
        setValues(state, values);
    }

    public void setDataValues(List<Integer> valuesList) {
        Integer[] values = null;
        if (valuesList != null) {
            values = new Integer[valuesList.size()];
            for (int i=0; i < valuesList.size(); i++) {
                values[i] = valuesList.get(i);
            }
        }
        setDataValues(values);
    }

    public void setDataValues(Integer... values) {
        if (data == null) {
            createData((int) Math.ceil((values.length / (double) numColumns)));
        }
        setValues(data, values);
    }

    protected void setValues(Data data, Integer... values) {
        List<Cell> cells = data.getCells();
        for (int i = 0; i < values.length; i++) {
            if (values[i] == null) {
                cells.get(i).setNull(true);
            } else {
                ((NumericValue) cells.get(i).getValue()).set(values[i]);
            }
        }
    }
}
