package org.schemaanalyst.test.data;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

public class TestRow3VL {

    Column col1, col2;
    Row row1, row2;

    @Before
    public void setup() {
        Schema s = new Schema("test_schema");
        Table t = s.createTable("test_table");
        col1 = t.addColumn("test_column1", new IntDataType());
        col2 = t.addColumn("test_column2", new IntDataType());
    }

    protected void setupIntRows(Integer val1r1, Integer val2r1, Integer val1r2, Integer val2r2) {
        NumericValue nval1r1 = val1r1 == null
                ? null
                : new NumericValue(val1r1);

        NumericValue nval2r1 = val2r1 == null
                ? null
                : new NumericValue(val2r1);

        NumericValue nval1r2 = val1r2 == null
                ? null
                : new NumericValue(val1r2);

        NumericValue nval2r2 = val2r2 == null
                ? null
                : new NumericValue(val2r2);

        setupRows(nval1r1, nval2r1, nval1r2, nval2r2);
    }

    protected void setupRows(Value val1r1, Value val2r1, Value val1r2, Value val2r2) {
        List<Cell> row1Cells = new ArrayList<>();
        Cell cell1r1 = new Cell(col1, new ValueFactory());
        Cell cell2r1 = new Cell(col2, new ValueFactory());
        cell1r1.setValue(val1r1);
        cell2r1.setValue(val2r1);
        row1Cells.add(cell1r1);
        row1Cells.add(cell2r1);
        row1 = new Row(row1Cells);

        List<Cell> row2Cells = new ArrayList<>();
        Cell cell1r2 = new Cell(col1, new ValueFactory());
        Cell cell2r2 = new Cell(col2, new ValueFactory());
        cell1r2.setValue(val1r2);
        cell2r2.setValue(val2r2);
        row2Cells.add(cell1r2);
        row2Cells.add(cell2r2);
        row2 = new Row(row2Cells);
    }

    @Test
    public void testRow3VL_True() {
        setupIntRows(10, 20,
                10, 20);

        assertTrue(row1.valuesEqual3VL(row2));
    }

    @Test
    public void testRow3VL_False1() {
        setupIntRows(10, 20,
                10, 30);

        assertFalse(row1.valuesEqual3VL(row2));
    }

    @Test
    public void testRow3VL_False2() {
        setupIntRows(10, 20,
                40, 30);

        assertFalse(row1.valuesEqual3VL(row2));
    }

    @Test
    public void testRow3VL_Null1() {
        setupIntRows(10, 15,
                20, null);

        assertNull(row1.valuesEqual3VL(row2));
    }

    @Test
    public void testRow3VL_Null2() {
        setupIntRows(10, null,
                10, null);

        assertNull(row1.valuesEqual3VL(row2));
    }

    @Test
    public void testRow3VL_Null3() {
        setupRows(null, null,
                null, null);

        assertNull(row1.valuesEqual3VL(row2));
    }
}
