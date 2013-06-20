package org.schemaanalyst.test.datageneration.analyst;

import static org.junit.Assert.*;

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
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

public class TestUniqueAnalyst {

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
	public void testBasicSatisfaction() {
		row1Cell1.setValue(new NumericValue(10));
		row1Cell2.setValue(new NumericValue(15));
		
		row2Cell1.setValue(new NumericValue(20));
		row2Cell2.setValue(new NumericValue(25));
		
		row3Cell1.setValue(new NumericValue(30));
		row3Cell2.setValue(new NumericValue(35));
			
		UniqueAnalyst una = new UniqueAnalyst(columns, true);
		
		assertTrue("All rows unique, should be satisfying",
					una.isSatisfied(null, data));
		
		assertEquals("Should be 3 satisfying (unique) rows",
					 3, una.getUniqueRows().size());
		
		assertEquals("Should be 0 non-satisfying (non-unique) rows",
				 	 0, una.getNonUniqueRows().size());		
	}
	
	@Test
	public void testBasicFalsification1() {
		row1Cell1.setValue(new NumericValue(10));
		row1Cell2.setValue(new NumericValue(15));
		
		row2Cell1.setValue(new NumericValue(20));
		row2Cell2.setValue(new NumericValue(25));
		
		row3Cell1.setValue(new NumericValue(20));
		row3Cell2.setValue(new NumericValue(25));
			
		UniqueAnalyst una = new UniqueAnalyst(columns, true);
		
		assertFalse("All rows unique, should be falsifying",
					una.isSatisfied(null, data));
		
		assertEquals("Should be 2 satisfying (unique) row",
					 2, una.getUniqueRows().size());
		
		assertEquals("Should be 1 non-satisfying (non-unique) rows",
				 	 1, una.getNonUniqueRows().size());		
	}	
	
	@Test
	public void testBasicFalsification2() {
		row1Cell1.setValue(new NumericValue(10));
		row1Cell2.setValue(new NumericValue(15));
		
		row2Cell1.setValue(new NumericValue(10));
		row2Cell2.setValue(new NumericValue(15));
		
		row3Cell1.setValue(new NumericValue(10));
		row3Cell2.setValue(new NumericValue(15));
			
		UniqueAnalyst una = new UniqueAnalyst(columns, true);
		
		assertFalse("All rows unique, should be falsifying",
					una.isSatisfied(null, data));
		
		assertEquals("Should be 2 satisfying (unique) row",
					 1, una.getUniqueRows().size());
		
		assertEquals("Should be 1 non-satisfying (non-unique) rows",
				 	 2, una.getNonUniqueRows().size());		
	}		
	
	@Test
	public void testSatisfactionWithNulls() {
		row1Cell1.setValue(new NumericValue(10));
		row1Cell2.setValue(new NumericValue(15));
		
		row2Cell1.setValue(new NumericValue(20));
		row2Cell2.setValue(null);
		
		row3Cell1.setValue(new NumericValue(20));
		row3Cell2.setValue(null);
			
		UniqueAnalyst una = new UniqueAnalyst(columns, true);
		assertTrue("Should satisfy with nulls",
					una.isSatisfied(null, data));
		
		assertEquals("Should be 3 satisfying (unique) rows with null satisfaction on",
					 3, una.getUniqueRows().size());
		
		assertEquals("Should be 0 non-satisfying (non-unique) rows with null satisfaction on",
			 	 	 0, una.getNonUniqueRows().size());	
	}
	
	@Test
	public void testFalsificationWithNulls() {
		row1Cell1.setValue(new NumericValue(10));
		row1Cell2.setValue(new NumericValue(15));
		
		row2Cell1.setValue(new NumericValue(20));
		row2Cell2.setValue(null);
		
		row3Cell1.setValue(new NumericValue(20));
		row3Cell2.setValue(null);
			
		UniqueAnalyst una = new UniqueAnalyst(columns, false);
		assertFalse("Should not satisfy with nulls",
					una.isSatisfied(null, data));
		
		assertEquals("Should be 1 satisfying (unique) rows with null satisfaction off",
				 	 1, una.getUniqueRows().size());
	
		assertEquals("Should be 2 non-satisfying (non-unique) rows with null satisfaction off",
		 	 	 	 2, una.getNonUniqueRows().size());		
	}	
}
