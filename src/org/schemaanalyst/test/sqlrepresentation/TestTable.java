package org.schemaanalyst.test.sqlrepresentation;

import static org.junit.Assert.*;

import org.junit.Test;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.SQLRepresentationException;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DoubleDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

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
    public void testDuplicatePrimaryKey() {
        Table t1 = new Table("table");
        Column column = t1.createColumn("column", new IntDataType());
        t1.createPrimaryKeyConstraint(column);
        
        Table t2 = t1.duplicate();
        
        assertNotSame(
                "t1 and t2 should not refer to the same table",
                t1, t2);
        
        assertEquals(
                "t1 and t2 should be equal",
                t1, t2);        
        
        assertNotSame(
                "The PRIMARY KEY of t1 and t2 should not refer to the same object",
                t1.getPrimaryKeyConstraint(), t2.getPrimaryKeyConstraint());
        
        assertEquals(
                "The PRIMARY KEY of t1 and t2 should be equal",
                t1.getPrimaryKeyConstraint(), t2.getPrimaryKeyConstraint());
        
        assertNotSame(
                "The PRIMARY KEY column of t1 and t2 should not refer to the same object",
                t1.getPrimaryKeyConstraint().getColumns().get(0), 
                t2.getPrimaryKeyConstraint().getColumns().get(0));
        
        assertEquals(
                "The PRIMARY KEY column of t1 and t2 should be equal",
                t1.getPrimaryKeyConstraint().getColumns().get(0), 
                t2.getPrimaryKeyConstraint().getColumns().get(0));            
    }
    
    @Test
    public void testDuplicateNullPrimaryKey() {       
        new Table("table").duplicate();          
    }    

    @Test
    public void testDuplicateNotNull() {
        Table t1 = new Table("table");
        Column column = t1.createColumn("column", new IntDataType());
        t1.createNotNullConstraint(column);
        
        Table t2 = t1.duplicate();
        
        assertNotSame(
                "t1 and t2 should not refer to the same table",
                t1, t2);
        
        assertEquals(
                "t1 and t2 should be equal",
                t1, t2);        
        
        assertNotSame(
                "The NOT NULL constraint of t1 and t2 should not refer to the same object",
                t1.getNotNullConstraints().get(0), 
                t2.getNotNullConstraints().get(0));
        
        assertEquals(
                "The NOT NULL constraint of t1 and t2 should be equal",
                t1.getNotNullConstraints().get(0), 
                t2.getNotNullConstraints().get(0));
        
        assertNotSame(
                "The NOT NULL constraint column of t1 and t2 should not refer to the same object",
                t1.getNotNullConstraints().get(0).getColumn(), 
                t2.getNotNullConstraints().get(0).getColumn());
        
        assertEquals(
                "The NOT NULL constraint column of t1 and t2 should be equal",
                t1.getNotNullConstraints().get(0).getColumn(), 
                t2.getNotNullConstraints().get(0).getColumn());            
    }     
    
    @Test    
    public void testDuplicateUnique() {
        Table t1 = new Table("table");
        Column column = t1.createColumn("column", new IntDataType());
        t1.createUniqueConstraint(column);
        
        Table t2 = t1.duplicate();
        
        assertNotSame(
                "t1 and t2 should not refer to the same table",
                t1, t2);
        
        assertEquals(
                "t1 and t2 should be equal",
                t1, t2);        
        
        assertNotSame(
                "The UNIQUE constraint of t1 and t2 should not refer to the same object",
                t1.getUniqueConstraints().get(0), 
                t2.getUniqueConstraints().get(0));
        
        assertEquals(
                "The UNIQUE constraint of t1 and t2 should be equal",
                t1.getUniqueConstraints().get(0), 
                t2.getUniqueConstraints().get(0));
        
        assertNotSame(
                "The UNIQUE constraint column of t1 and t2 should not refer to the same object",
                t1.getUniqueConstraints().get(0).getColumns().get(0), 
                t2.getUniqueConstraints().get(0).getColumns().get(0));
        
        assertEquals(
                "The UNIQUE constraint column of t1 and t2 should be equal",
                t1.getUniqueConstraints().get(0).getColumns().get(0), 
                t2.getUniqueConstraints().get(0).getColumns().get(0));            
    }    
}
