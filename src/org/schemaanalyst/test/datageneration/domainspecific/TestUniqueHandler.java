package org.schemaanalyst.test.datageneration.domainspecific;

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
import org.schemaanalyst.datageneration.analyst.UniqueAnalyst;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomisationFactory;
import org.schemaanalyst.datageneration.domainspecific.UniqueHandler;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.util.random.SimpleRandom;

public class TestUniqueHandler {

    Data data;
    Column column1, column2;
    Cell row1Cell1, row1Cell2;
    Cell row2Cell1, row2Cell2;
    Cell row3Cell1, row3Cell2;
    List<Column> columns;

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

        List<Cell> row3Cells = new ArrayList<>();
        row3Cell1 = new Cell(column1, vf);
        row3Cell2 = new Cell(column2, vf);
        row3Cells.add(row3Cell1);
        row3Cells.add(row3Cell2);
        data.addRow(table, new Row(row3Cells));
    }

    @Test
    public void testAttemptSatisfy() {
        row1Cell1.setValue(new NumericValue(10));
        row1Cell2.setValue(new NumericValue(15));

        row2Cell1.setValue(new NumericValue(20));
        row2Cell2.setValue(new NumericValue(25));

        row3Cell1.setValue(new NumericValue(20));
        row3Cell2.setValue(new NumericValue(25));

        UniqueAnalyst ua = new UniqueAnalyst(columns, true);

        assertFalse("Pre-handler: one row non-unique, should be falisfying",
                ua.isSatisfied(null, data));

        UniqueHandler uh = new UniqueHandler(ua, true, true,
                CellRandomisationFactory.small(new SimpleRandom(0)),
                new SimpleRandom(0));
        uh.attempt(null, data);

        assertTrue("Post handler: all rows should be unique, should be satisfying",
                ua.isSatisfied(null, data));
    }

    @Test
    public void testAttemptFalsify() {
        row1Cell1.setValue(new NumericValue(10));
        row1Cell2.setValue(new NumericValue(15));

        row2Cell1.setValue(new NumericValue(20));
        row2Cell2.setValue(new NumericValue(25));

        row3Cell1.setValue(new NumericValue(30));
        row3Cell2.setValue(new NumericValue(35));

        UniqueAnalyst ua = new UniqueAnalyst(columns, true);

        assertTrue("Pre-handler: all rows unique, should be satisfying",
                ua.isSatisfied(null, data));

        UniqueHandler uh = new UniqueHandler(ua, false, true, null, new SimpleRandom(0));
        uh.attempt(null, data);

        assertFalse("Post handler: all rows should be non-unique, should be falsifying",
                ua.isSatisfied(null, data));
    }
}