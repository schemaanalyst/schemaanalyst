package org.schemaanalyst.test.data.generation;

import org.junit.Test;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.data.generation.CellValueGenerator;
import org.schemaanalyst.data.ValueLibrary;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;
import org.schemaanalyst.test.testutil.mock.MockRandom;

import static org.junit.Assert.assertEquals;

/**
 * Created by phil on 26/02/2014.
 */
public class TestCellValueGenerator {

    @Test
    public void testUseLibraryNumericValue() {
        ValueLibrary valueLibrary = new ValueLibrary();
        valueLibrary.addValue(new NumericValue(10));
        valueLibrary.addValue(new NumericValue(20));

        CellValueGenerator cellValueGenerator = new CellValueGenerator(
                new MockRandom(0, 0, 1, 0, 0, 0), null, 0, valueLibrary,
                // no profile required
                // null probability
                1                       // use library probability
        );

        Cell cell = new Cell(new Column("test", new IntDataType()), new ValueFactory());

        cellValueGenerator.generateCellValue(cell);
        assertEquals(20, ((NumericValue) cell.getValue()).get().intValue());

        cellValueGenerator.generateCellValue(cell);
        assertEquals(10, ((NumericValue) cell.getValue()).get().intValue());
    }

    @Test
    public void testUseLibraryStringValue() {
        ValueLibrary valueLibrary = new ValueLibrary();
        valueLibrary.addValue(new StringValue("Hello"));
        valueLibrary.addValue(new StringValue("Goodbye"));

        CellValueGenerator cellValueGenerator = new CellValueGenerator(
                new MockRandom(0, 0, 1, 0, 0, 0), null, 0, valueLibrary,
                // no profile required
                // null probability
                1                       // use library probability
        );

        Cell cell = new Cell(new Column("test", new VarCharDataType(4)), new ValueFactory());

        cellValueGenerator.generateCellValue(cell);
        assertEquals("Good", ((StringValue) cell.getValue()).get());

        cellValueGenerator.generateCellValue(cell);
        assertEquals("Hell", ((StringValue) cell.getValue()).get());
    }
}
