package org.schemaanalyst.unittest.sqlrepresentation;

import org.junit.Test;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.SQLRepresentationException;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DoubleDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import static org.junit.Assert.*;

public class TestTable {

    @Test
    public void testAddColumn() {
        Table t = new Table("table");
        Column c = new Column("column", new IntDataType());
        t.addColumn(c);
        assertTrue(
                "The table should have the column added", t.hasColumn(c));
        assertTrue(
                "The table should detect it has the column added using its name", 
                t.hasColumn("column"));
        assertNotNull(
                "The table should return the instance of the column added",
                t.getColumn("column"));
    }
    
    @Test
    public void testCreateColumn() {
        Table t = new Table("table");
        Column c = t.createColumn("column", new IntDataType());
        assertTrue(
                "The table should have the column created", 
                t.hasColumn(c));
        assertTrue(
                "The table should detect it has the column created using its name", 
                t.hasColumn("column"));
        assertNotNull(
                "The table should return the instance of the created added",
                t.getColumn("column"));
    }    
    
    @Test
    public void testCreateMultipleColumns() {
        Table t = new Table("table");
        Column c1 = t.createColumn("column1", new IntDataType());
        Column c2 = t.createColumn("column2", new IntDataType());
        
        assertSame(
                "The first column should be added and correctly returned",
                c1, t.getColumn("column1"));
        
        assertSame(
                "The second column should be added and correctly returned",
                c2, t.getColumn("column2"));
        
        assertEquals(
                "There should be 2 columns in the table",
                2, t.getColumns().size());
        
        assertSame(
                "The first column returned by getColumns() should be the first column added",
                c1, t.getColumns().get(0));
        
        assertSame(
                "The second column returned by getColumns() should be the second column added",
                c2, t.getColumns().get(1));        
    }
    
    @Test(expected=SQLRepresentationException.class)
    public void testColumnNameClash() {
        Table t = new Table("table");
        t.createColumn("column", new IntDataType());
        t.createColumn("column", new DoubleDataType());
    }
    
    @Test    
    public void testDuplicateColumnsOnly() {
        Table t1 = new Table("table");
        t1.createColumn("column1", new IntDataType());
        t1.createColumn("column2", new DoubleDataType());
        
        Table t2 = t1.duplicate();
        
        assertNotSame(
                "t1 and t2 should not refer to the same table",
                t1, t2);
        
        assertEquals(
                "t1 and t2 should be equal",
                t1, t2);

        assertNotSame(
                "Columns in t1 and t2 should not be the same",
                t1.getColumn("column1"), t2.getColumn("column1"));        

        assertNotSame(
                "Columns in t1 and t2 should not be the same",
                t1.getColumn("column2"), t2.getColumn("column2"));         

        assertEquals(
                "Columns in t1 and t2 should be equal",
                t1.getColumn("column1"), t2.getColumn("column1"));        

        assertEquals(
                "Columns in t1 and t2 should be equal",
                t1.getColumn("column2"), t2.getColumn("column2"));
    }
    
    @Test 
    public void testRenameInSchema() {
    	Schema schema = new Schema("test");
    	Table table1 = schema.createTable("test1");
    	schema.createTable("test2");
    	
    	table1.setName("test3");
    	assertEquals(
    			"The column's name should be changed as the new name is unique",
    			"test3", table1.getName());
    }
    
    @Test(expected=SQLRepresentationException.class)
    public void testRenameInSchemaFail() {
    	Schema schema = new Schema("test");
    	Table table1 = schema.createTable("test1");
    	schema.createTable("test2");
    	table1.setName("test2");
    }    
    
    @Test
    public void testRenameInDuplicatedSchema() {
    	Schema schema = new Schema("test");
    	schema.createTable("test1");
    	schema.createTable("test2");
    	
    	Schema duplicate = schema.duplicate();
    	Table dupTab1 = duplicate.getTable("test1");
    	Table dupTab2 = duplicate.getTable("test2");
    	dupTab2.setName("test3");
    	
    	assertEquals(
    			"The table's name should be changed as the new name is unique",
    			"test3", dupTab2.getName());    	
    	
    	dupTab1.setName("test2");
    	
    	assertEquals(
    			"The table's name should be changed as the new name is unique in the duplicated schema",
    			"test2", dupTab1.getName());    	    	
    	
    	dupTab1.setName("test1");
    	assertEquals(
    			"The table's name should be changed as the new name is unique again in the duplicated schema",
    			"test1", dupTab1.getName());    	
    }    
}
