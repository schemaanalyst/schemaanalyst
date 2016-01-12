package org.schemaanalyst.unittest.sqlrepresentation;

import org.junit.Test;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.SQLRepresentationException;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class TestColumn {

    @Test
    public void testDuplication() {
        DataType d1 = new IntDataType();
        Column c1 = new Column("test", d1);
        Column c2 = c1.duplicate();
        
        assertNotSame(
                "A column's duplicate should not be the same object", c1, c2);

        assertNotSame(
                "A column's duplicate data type should not be the same object", 
                c1.getDataType(), c2.getDataType());        
        
        assertEquals(
                "A column and its duplicate should be equal", c1, c2);
        
        assertEquals(
                "A column and its duplicate should have the same name", 
                c1.getName(), c2.getName());
        
        assertEquals(
                "A column and its duplicate should have the same data type", 
                c1.getDataType(), c2.getDataType());
        
        assertEquals(
                "A column and its duplicate should have the same hashcode", 
                c1.hashCode(), c2.hashCode());              
    }
    
    @Test 
    public void testRenameInTable() {
    	Table table = new Table("test");
    	Column column1 = table.createColumn("test1", new IntDataType());
    	table.createColumn("test2", new IntDataType());
    	
    	column1.setName("test3");
    	assertEquals(
    			"The column's name should be changed as the new name is unique",
    			"test3", column1.getName());
    }
    
    @Test(expected=SQLRepresentationException.class)
    public void testRenameInTableFail() {
    	Table table = new Table("test");
    	Column column1 = table.createColumn("test1", new IntDataType());
    	table.createColumn("test2", new IntDataType());
    	column1.setName("test2");
    }    
    
    @Test
    public void testRenameInDuplicatedTable() {
    	Table table = new Table("test");
    	table.createColumn("test1", new IntDataType());
    	table.createColumn("test2", new IntDataType());
    	
    	Table duplicate = table.duplicate();
    	Column dupCol1 = duplicate.getColumn("test1");
    	Column dupCol2 = duplicate.getColumn("test2");
    	dupCol2.setName("test3");
    	
    	assertEquals(
    			"The column's name should be changed as the new name is unique",
    			"test3", dupCol2.getName());    	
    	
    	dupCol1.setName("test2");
    	
    	assertEquals(
    			"The column's name should be changed as the new name is unique in the duplicated table",
    			"test2", dupCol1.getName());    	    	
    	
    	dupCol1.setName("test1");
    	assertEquals(
    			"The column's name should be changed as the new name is unique again in the duplicated table",
    			"test1", dupCol1.getName());    	
    }
}
