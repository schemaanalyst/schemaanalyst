package org.schemaanalyst.test.datageneration.analyst;

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
import org.schemaanalyst.representation.Column;
import org.schemaanalyst.representation.Schema;
import org.schemaanalyst.representation.Table;
import org.schemaanalyst.representation.datatype.IntDataType;

import static org.junit.Assert.*;

public class TestNotNullAnalyst {

	Data data;
	Column column;
	Cell row1Cell, row2Cell, row3Cell;
	
	@Before
	public void setup() {
		data = new Data();
		
		Schema schema = new Schema("test_schema");
		Table table = schema.createTable("test_table");
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
		row2Cell.setValue(null);
		row3Cell.setValue(new NumericValue(20));
		
		NotNullAnalyst nna = new NotNullAnalyst(column);
		assertFalse("Nulls present, so should not be satisfying",
					nna.isSatisfied(null, data));
		
		List<Cell> satisfyingCells = nna.getNotNullCells();
		assertEquals("Should be 2 satisfying (not null) cells",
					 2, satisfyingCells.size());
		
		assertEquals("First cell should have value 10",
				 	 10, ((NumericValue) satisfyingCells.get(0).getValue()).get().intValue());
		
		assertEquals("Second cell should have value 20",
			 	 	 20, ((NumericValue) satisfyingCells.get(1).getValue()).get().intValue());
		
		List<Cell> nonSatisfyingCells = nna.getNullCells();
		assertEquals("Should be 1 non-satisfying (null) cells",
					 1, nonSatisfyingCells.size());		
	}
	
	@Test
	public void testSatisfying() {
		row1Cell.setValue(new NumericValue(10));
		row2Cell.setValue(new NumericValue(20));
		row3Cell.setValue(new NumericValue(30));
		
		NotNullAnalyst nna = new NotNullAnalyst(column);
		assertTrue("No nulls present, so should be satisfying",
					nna.isSatisfied(null, data));
		
		List<Cell> satisfyingCells = nna.getNotNullCells();
		assertEquals("Should be 3 satisfying (not null) cells",
					 3, satisfyingCells.size());		
		
		List<Cell> nonSatisfyingCells = nna.getNullCells();
		assertEquals("Should be 0 non-satisfying (null) cells",
					 0, nonSatisfyingCells.size());		
	}
}
