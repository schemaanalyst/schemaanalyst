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
import org.schemaanalyst.datageneration.analyst.InAnalyst;
import org.schemaanalyst.representation.Column;
import org.schemaanalyst.representation.Schema;
import org.schemaanalyst.representation.Table;
import org.schemaanalyst.representation.datatype.IntDataType;
import org.schemaanalyst.representation.expression.InExpression;

import static org.junit.Assert.*;

public class TestInAnalyst {

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
		
		InExpression in = new InExpression(column, 30, 40, 50);
		
		InAnalyst ia = new InAnalyst(in, table, true);
		
		assertFalse("One cell equal to a member of the in values",
					ia.isSatisfied(null, data));
		
		List<Cell> nonSatisfyingCells = ia.getNotInCells();
		assertEquals("Should be 2 non-satisfying cells",
					 2, nonSatisfyingCells.size());
		
		List<Cell> satisfyingCells = ia.getInCells();
		assertEquals("Should be 1 satisfying cell",
					 1, satisfyingCells.size());		
	}
	
	@Test
	public void testSatisfying() {
		row1Cell.setValue(new NumericValue(10));
		row2Cell.setValue(new NumericValue(20));
		row3Cell.setValue(new NumericValue(30));
		
		InExpression in = new InExpression(column, 30, 20, 10);
		
		InAnalyst ia = new InAnalyst(in, table, true);
		
		assertTrue("All cells equal to a member of the in values",
					ia.isSatisfied(null, data));
		
		List<Cell> satisfyingCells = ia.getInCells();
		assertEquals("Should be 3 satisfying cell",
					 3, satisfyingCells.size());		
		
		List<Cell> nonSatisfyingCells = ia.getNotInCells();
		assertEquals("Should be 0 non-satisfying cells",
					 0, nonSatisfyingCells.size());
	}	
}
