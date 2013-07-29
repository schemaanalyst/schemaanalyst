package org.schemaanalyst.deprecated.test.datageneration.analyst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.datageneration.analyst.RelationalPredicateAnalyst;
import org.schemaanalyst.deprecated.sqlrepresentation.checkcondition.Operand;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.logic.RelationalPredicate;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

public class TestRelationalPredicateAnalyst {

    Data data;
    Table table;
    Column column;
    Cell row1Cell, row2Cell, row3Cell;

    @Before
    public void setup() {
        data = new Data();

        Schema schema = new Schema("test_schema");
        table = schema.createTable("test_table");
        column = table.addColumn("test_column", new IntDataType());
        ValueFactory vf = new ValueFactory();

        List<Cell> row1Cells = new ArrayList<>();
        row1Cell = new Cell(column, vf);
        row1Cells.add(row1Cell);
        data.addRow(table, new Row(row1Cells));

        List<Cell> row2Cells = new ArrayList<>();
        row2Cell = new Cell(column, vf);
        row2Cells.add(row2Cell);
        data.addRow(table, new Row(row2Cells));

        List<Cell> row3Cells = new ArrayList<>();
        row3Cell = new Cell(column, vf);
        row3Cells.add(row3Cell);
        data.addRow(table, new Row(row3Cells));
    }

    @Test
    public void testNotSatisfying() {
        row1Cell.setValue(new NumericValue(10));
        row2Cell.setValue(new NumericValue(20));
        row3Cell.setValue(new NumericValue(30));

        RelationalPredicate<Operand> rp = new RelationalPredicate<>(column, RelationalOperator.LESS, new NumericValue(20));

        RelationalPredicateAnalyst rpa = new RelationalPredicateAnalyst(rp, table, true);

        assertFalse("Only one row satisfies the predicate",
                rpa.isSatisfied(null, data));

        List<Integer> nonSatisfyingRows = rpa.getFalsifyingEntries();
        assertEquals("Should be 2 non-satisfying rows",
                2, nonSatisfyingRows.size());

        List<Integer> satisfyingRows = rpa.getSatisfyingEntries();
        assertEquals("Should be 1 satisfying row",
                1, satisfyingRows.size());
    }

    @Test
    public void testSatisfying() {
        row1Cell.setValue(new NumericValue(10));
        row2Cell.setValue(new NumericValue(20));
        row3Cell.setValue(new NumericValue(30));

        RelationalPredicate<Operand> rp = new RelationalPredicate<>(column, RelationalOperator.GREATER, new NumericValue(5));

        RelationalPredicateAnalyst rpa = new RelationalPredicateAnalyst(rp, table, true);

        assertTrue("All rows satisfy the predicate",
                rpa.isSatisfied(null, data));

        List<Integer> satisfyingEntries = rpa.getSatisfyingEntries();
        assertEquals("Should be 3 satisfying entries",
                3, satisfyingEntries.size());

        List<Integer> nonSatisfyingEntries = rpa.getFalsifyingEntries();
        assertEquals("Should be 0 non-satisfying entries",
                0, nonSatisfyingEntries.size());
    }
}
