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
import org.schemaanalyst.datageneration.analyst.InAnalyst;
import org.schemaanalyst.datageneration.cellrandomization.CellRandomizationProfiles;
import org.schemaanalyst.datageneration.domainspecific.InHandler;
import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.InCheckPredicate;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.IntColumnType;
import org.schemaanalyst.util.random.SimpleRandom;

public class TestInHandler {

	Data data;
	Table table;
	Column column;
	Cell row1Cell, row2Cell, row3Cell;
	
	@Before
	public void setup() {
		data = new Data();
		
		Schema schema = new Schema("test_schema");
		table = schema.createTable("test_table");
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
		row2Cell.setValue(new NumericValue(20));
		row3Cell.setValue(new NumericValue(30));
		
		InCheckPredicate in = new InCheckPredicate(column, 30, 40, 50);
		
		InAnalyst ia = new InAnalyst(in, table, true);
		
		assertFalse("Pre-handler: constraint should not be satisfied",
					ia.isSatisfied(null, data));

		InHandler ih = new InHandler(ia, true, true, 
				 					 CellRandomizationProfiles.small(new SimpleRandom(0)), 
				 					 new SimpleRandom(0));	
		ih.attempt(null, data);
		
		assertTrue("Post-handler: constraint should be satisfied",
				   ia.isSatisfied(null, data));		
		

		List<Cell> nonSatisfyingCells = ia.getNotInCells();
		assertEquals("Should be 0 non-satisfying cells",
					 0, nonSatisfyingCells.size());
		
		List<Cell> satisfyingCells = ia.getInCells();
		assertEquals("Should be 3 satisfying cells",
					 3, satisfyingCells.size());			
	}
	
	@Test
	public void testAttemptFalsify() {
		row1Cell.setValue(new NumericValue(10));
		row2Cell.setValue(new NumericValue(20));
		row3Cell.setValue(new NumericValue(30));
		
		InCheckPredicate in = new InCheckPredicate(column, 30, 20, 10);
		
		InAnalyst ia = new InAnalyst(in, table, true);
		
		assertTrue("Pre-handler: constraint should be satified",
					ia.isSatisfied(null, data));
		
		InHandler ih = new InHandler(ia, false, true, 
				 CellRandomizationProfiles.small(new SimpleRandom(0)), 
				 new SimpleRandom(0));	
		
		ih.attempt(null, data);
		
		assertFalse("Post-handler: constraint should be satisfied",
				ia.isSatisfied(null, data));		


		List<Cell> nonSatisfyingCells = ia.getNotInCells();
		assertEquals("Should be 3 non-satisfying cells",
				     3, nonSatisfyingCells.size());

		List<Cell> satisfyingCells = ia.getInCells();
		assertEquals("Should be 0 satisfying cells",
					 0, satisfyingCells.size());			
	}	
}