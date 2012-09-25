package org.schemaanalyst.test.datageneration.domainspecific;

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
import org.schemaanalyst.datageneration.analyst.NotNullAnalyst;
import org.schemaanalyst.datageneration.domainspecific.NotNullHandler;
import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.IntColumnType;

public class TestNotNullHandler {

	Data data;
	Column column;
	Cell row1Cell, row2Cell, row3Cell;
	
	@Before
	public void setup() {
		data = new Data();
		
		Schema schema = new Schema("test_schema");
		Table table = schema.createTable("test_table");
		column = table.addColumn("test_column", new IntColumnType());
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
	public void testAttemptSatisfy() {
		row1Cell.setValue(new NumericValue(10));
		row2Cell.setValue(null);
		row3Cell.setValue(new NumericValue(20));
		
		NotNullAnalyst nna = new NotNullAnalyst(column);
		assertFalse("Nulls present, so should not be satisfying before handling",
					nna.isSatisfied(null, data));
		
		NotNullHandler nnh = new NotNullHandler(nna, true);
		nnh.attempt(null, data);
		
		assertTrue("Nulls should be corrected so should be satisfying after handling",
				nna.isSatisfied(null, data));		
		

		List<Cell> satisfyingCells = nna.getNotNullCells();
		assertEquals("Should be 3 satisfying (not null) cells",
					 3, satisfyingCells.size());
		
		List<Cell> nonSatisfyingCells = nna.getNullCells();
		assertEquals("Should be 1 non-satisfying (null) cells",
					 0, nonSatisfyingCells.size());				
	}
	
	@Test
	public void testAttemptFalsify() {
		row1Cell.setValue(new NumericValue(10));
		row2Cell.setValue(new NumericValue(15));
		row3Cell.setValue(new NumericValue(20));
		
		NotNullAnalyst nna = new NotNullAnalyst(column);
		assertTrue("Nulls not present, so should be satisfying before handling",
					nna.isSatisfied(null, data));
		
		NotNullHandler nnh = new NotNullHandler(nna, false);
		nnh.attempt(null, data);
		
		assertFalse("Cells should be set to null, so should be falsifying after handling",
				nna.isSatisfied(null, data));		
		

		List<Cell> satisfyingCells = nna.getNotNullCells();
		assertEquals("Should be 0 satisfying (not null) cells",
					 0, satisfyingCells.size());
		
		List<Cell> nonSatisfyingCells = nna.getNullCells();
		assertEquals("Should be 3 non-satisfying (null) cells",
					 3, nonSatisfyingCells.size());				
	}	
}