package org.schemaanalyst.deprecated.test.datageneration.analyst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.deprecated.datageneration.analyst.ReferenceAnalyst;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

public class TestReferenceAnalyst {

    Data data;
    Column column1, column2;
    Column refColumn1, refColumn2;
    List<Column> columns, refColumns;
    Cell row1Cell1, row1Cell2;
    Cell row2Cell1, row2Cell2;
    Cell refRow1Cell1, refRow1Cell2;
    Cell refRow2Cell1, refRow2Cell2;

    @Before
    public void setup() {
        data = new Data();

        Schema schema = new Schema("test_schema");
        Table table = schema.createTable("test_table");
        column1 = table.addColumn("test_column1", new IntDataType());
        column2 = table.addColumn("test_column2", new IntDataType());
        columns = new ArrayList<>();
        columns.add(column1);
        columns.add(column2);

        Table refTable = schema.createTable("ref_table");
        refColumn1 = refTable.addColumn("ref_column1", new IntDataType());
        refColumn2 = refTable.addColumn("ref_column2", new IntDataType());
        refColumns = new ArrayList<>();
        refColumns.add(refColumn1);
        refColumns.add(refColumn2);

        ValueFactory vf = new ValueFactory();

        List<Cell> row1Cells = new ArrayList<>();
        row1Cell1 = new Cell(column1, vf);
        row1Cell2 = new Cell(column2, vf);
        row1Cells.add(row1Cell1);
        row1Cells.add(row1Cell2);
        data.addRow(table, new Row(row1Cells));

        List<Cell> row2Cells = new ArrayList<>();
        row2Cell1 = new Cell(column1, vf);
        row2Cell2 = new Cell(column2, vf);
        row2Cells.add(row2Cell1);
        row2Cells.add(row2Cell2);
        data.addRow(table, new Row(row2Cells));

        List<Cell> refRow1Cells = new ArrayList<>();
        refRow1Cell1 = new Cell(refColumn1, vf);
        refRow1Cell2 = new Cell(refColumn2, vf);
        refRow1Cells.add(refRow1Cell1);
        refRow1Cells.add(refRow1Cell2);
        data.addRow(refTable, new Row(refRow1Cells));

        List<Cell> refRow2Cells = new ArrayList<>();
        refRow2Cell1 = new Cell(refColumn1, vf);
        refRow2Cell2 = new Cell(refColumn2, vf);
        refRow2Cells.add(refRow2Cell1);
        refRow2Cells.add(refRow2Cell2);
        data.addRow(refTable, new Row(refRow2Cells));
    }

    @Test
    public void testBasicSatisfaction() {
        row1Cell1.setValue(new NumericValue(10));
        row1Cell2.setValue(new NumericValue(15));

        row2Cell1.setValue(new NumericValue(20));
        row2Cell2.setValue(new NumericValue(25));

        refRow1Cell1.setValue(new NumericValue(10));
        refRow1Cell2.setValue(new NumericValue(15));

        refRow2Cell1.setValue(new NumericValue(20));
        refRow2Cell2.setValue(new NumericValue(25));

        ReferenceAnalyst ra = new ReferenceAnalyst(columns, refColumns, true);

        assertTrue("All rows referencing, should be satisfying",
                ra.isSatisfied(null, data));

        assertEquals("Should be 2 satisfying (referencing) rows",
                2, ra.getReferencingRows().size());

        assertEquals("Should be 0 non-satisfying (non-referencing) rows",
                0, ra.getNonReferencingRows().size());
    }

    @Test
    public void testBasicFalsification1() {
        row1Cell1.setValue(new NumericValue(10));
        row1Cell2.setValue(new NumericValue(15));

        row2Cell1.setValue(new NumericValue(20));
        row2Cell2.setValue(new NumericValue(25));

        refRow1Cell1.setValue(new NumericValue(10));
        refRow1Cell2.setValue(new NumericValue(15));

        refRow2Cell1.setValue(new NumericValue(30));
        refRow2Cell2.setValue(new NumericValue(25));

        ReferenceAnalyst ra = new ReferenceAnalyst(columns, refColumns, true);

        assertFalse("One row non-referencing, should be false",
                ra.isSatisfied(null, data));

        assertEquals("Should be 1 satisfying (referencing) rows",
                1, ra.getReferencingRows().size());

        assertEquals("Should be 1 non-satisfying (non-referencing) rows",
                1, ra.getNonReferencingRows().size());
    }

    @Test
    public void testBasicFalsification2() {
        row1Cell1.setValue(new NumericValue(100));
        row1Cell2.setValue(new NumericValue(150));

        row2Cell1.setValue(new NumericValue(20));
        row2Cell2.setValue(new NumericValue(25));

        refRow1Cell1.setValue(new NumericValue(10));
        refRow1Cell2.setValue(new NumericValue(15));

        refRow2Cell1.setValue(new NumericValue(30));
        refRow2Cell2.setValue(new NumericValue(25));

        ReferenceAnalyst ra = new ReferenceAnalyst(columns, refColumns, true);

        assertFalse("One row non-referencing, should be false",
                ra.isSatisfied(null, data));

        assertEquals("Should be 1 satisfying (referencing) rows",
                0, ra.getReferencingRows().size());

        assertEquals("Should be 1 non-satisfying (non-referencing) rows",
                2, ra.getNonReferencingRows().size());
    }

    @Test
    public void testSatisfactionWithNulls1() {
        row1Cell1.setValue(new NumericValue(10));
        row1Cell2.setValue(new NumericValue(15));

        row2Cell1.setValue(new NumericValue(20));
        row2Cell2.setValue(new NumericValue(25));

        refRow1Cell1.setValue(null);
        refRow1Cell2.setValue(new NumericValue(15));

        refRow2Cell1.setValue(new NumericValue(20));
        refRow2Cell2.setValue(new NumericValue(25));

        ReferenceAnalyst ra = new ReferenceAnalyst(columns, refColumns, true);

        assertTrue("All rows referencing, should be satisfying",
                ra.isSatisfied(null, data));

        assertEquals("Should be 2 satisfying (referencing) rows",
                2, ra.getReferencingRows().size());

        assertEquals("Should be 0 non-satisfying (non-referencing) rows",
                0, ra.getNonReferencingRows().size());
    }

    @Test
    public void testSatisfactionWithNulls2() {
        row1Cell1.setValue(new NumericValue(10));
        row1Cell2.setValue(new NumericValue(15));

        row2Cell1.setValue(null);
        row2Cell2.setValue(new NumericValue(25));

        refRow1Cell1.setValue(null);
        refRow1Cell2.setValue(new NumericValue(15));

        refRow2Cell1.setValue(new NumericValue(20));
        refRow2Cell2.setValue(new NumericValue(25));

        ReferenceAnalyst ra = new ReferenceAnalyst(columns, refColumns, true);

        assertTrue("All rows referencing, should be satisfying",
                ra.isSatisfied(null, data));

        assertEquals("Should be 2 satisfying (referencing) rows",
                2, ra.getReferencingRows().size());

        assertEquals("Should be 0 non-satisfying (non-referencing) rows",
                0, ra.getNonReferencingRows().size());
    }

    @Test
    public void testFalsificationWithNulls1() {
        row1Cell1.setValue(new NumericValue(10));
        row1Cell2.setValue(new NumericValue(15));

        row2Cell1.setValue(new NumericValue(20));
        row2Cell2.setValue(new NumericValue(25));

        refRow1Cell1.setValue(null);
        refRow1Cell2.setValue(new NumericValue(15));

        refRow2Cell1.setValue(new NumericValue(20));
        refRow2Cell2.setValue(new NumericValue(25));

        ReferenceAnalyst ra = new ReferenceAnalyst(columns, refColumns, false);

        assertFalse("All rows referencing, should be satisfying",
                ra.isSatisfied(null, data));

        assertEquals("Should be 2 satisfying (referencing) rows",
                1, ra.getReferencingRows().size());

        assertEquals("Should be 0 non-satisfying (non-referencing) rows",
                1, ra.getNonReferencingRows().size());
    }

    @Test
    public void testFalsificationWithNulls2() {
        row1Cell1.setValue(new NumericValue(10));
        row1Cell2.setValue(new NumericValue(15));

        row2Cell1.setValue(null);
        row2Cell2.setValue(new NumericValue(25));

        refRow1Cell1.setValue(null);
        refRow1Cell2.setValue(new NumericValue(15));

        refRow2Cell1.setValue(new NumericValue(20));
        refRow2Cell2.setValue(new NumericValue(25));

        ReferenceAnalyst ra = new ReferenceAnalyst(columns, refColumns, false);

        assertFalse("All rows referencing, should be satisfying",
                ra.isSatisfied(null, data));

        assertEquals("Should be 2 satisfying (referencing) rows",
                0, ra.getReferencingRows().size());

        assertEquals("Should be 0 non-satisfying (non-referencing) rows",
                2, ra.getNonReferencingRows().size());
    }
}
