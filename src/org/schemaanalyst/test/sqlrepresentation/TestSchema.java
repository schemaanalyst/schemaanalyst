package org.schemaanalyst.test.sqlrepresentation;

import static org.junit.Assert.*;

import org.junit.Test;
import org.schemaanalyst.sqlrepresentation.SQLRepresentationException;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

public class TestSchema {
    
    @Test
    public void testCreateTable() {
        Schema s = new Schema("schema");
        Table t = s.createTable("table");
        assertSame(
                "The table created should be the same as that returned",
                t, s.getTable("table"));
    }
    
    @Test
    public void testAddTable() {
        Schema s = new Schema("schema");
        Table t = new Table("table");
        s.addTable(t);
        assertSame(
                "The table added should be the same as that returned",
                t, s.getTable("table"));
    }
    
    @Test
    public void testAddMultipleTables() {
        Schema s = new Schema("schema");
        Table t1 = new Table("table1");
        Table t2 = new Table("table2");
        s.addTable(t1);
        s.addTable(t2);
        
        assertSame(
                "The first table should be added and correctly returned",
                t1, s.getTable("table1"));
        
        assertSame(
                "The second table should be added and correctly returned",
                t2, s.getTable("table2"));
        
        assertEquals(
                "There should be 2 tables in the schema",
                2, s.getTables().size());
        
        assertSame(
                "The first table returned by getTables() should be the first table added",
                t1, s.getTables().get(0));
        
        assertSame(
                "The second table returned by getTables() should be the second table added",
                t2, s.getTables().get(1));
    }    
    
    @Test(expected=SQLRepresentationException.class)
    public void testTableNameClash() {
        Schema s = new Schema("schema");
        Table t1 = new Table("table");
        Table t2 = new Table("table");
        s.addTable(t1);
        s.addTable(t2);
    }
    
    public void testDuplication() {
        Schema s1 = new Schema("schema");
        s1.createTable("table1");
        s1.createTable("table2");
        Schema s2 = s1.duplicate();
        
        assertNotSame(
                "The duplicate of a schema should not be the same object",
                s1, s2);

        assertEquals(
                "The duplicate of a schema should be equal to the original",
                s1, s2);        
        
        assertEquals(
                "The duplicate of a schema should have the same number of tables",
                2, s2.getTables().size());
        
        assertNotNull(
                "The duplicate of a schema should have the same tables",
                s2.getTable("table1"));
        
        assertNotNull(
                "The duplicate of a schema should have the same tables",
                s2.getTable("table2"));        
    }
    
    // TODO - test duplication with FKs
    
}
