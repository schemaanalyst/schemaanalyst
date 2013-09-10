/*
 */
package org.schemaanalyst.test.mutation.redundancy;

import org.junit.Test;
import static org.junit.Assert.*;
import org.schemaanalyst.mutation.redundancy.SchemaEquivalenceTester;
import org.schemaanalyst.sqlrepresentation.*;
import org.schemaanalyst.sqlrepresentation.datatype.*;

/**
 *
 * @author Chris J. Wright
 */
public class TestSchemaEquivalenceTester {
    {
        assertTrue(true);
    }
    
    @Test
    public void testSameInstance() {
        SchemaEquivalenceTester tester = new SchemaEquivalenceTester();
        Schema s = new Schema("s");
        Table t = s.createTable("t");
        Column a = t.createColumn("a", new IntDataType());
        assertTrue("A schema should be equivalent to itself",
                tester.areEquivalent(s, s));
    }
    
    @Test
    public void testDifferentInstance() {
        SchemaEquivalenceTester tester = new SchemaEquivalenceTester();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        assertTrue("Two schemas with the same tables and columns should be "
                + "equivalent", tester.areEquivalent(s1, s2));
    }
    
    @Test
    public void testDifferentTables() {
        SchemaEquivalenceTester tester = new SchemaEquivalenceTester();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t1");
        Column a2 = t2.createColumn("a", new IntDataType());
        assertFalse("Two schemas containing different tables should not be "
                + "equivalent", tester.areEquivalent(s1, s2));
    }
    
    @Test
    public void testDifferentTableCount() {
        SchemaEquivalenceTester tester = new SchemaEquivalenceTester();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Table u1 = s1.createTable("u");
        Column a1 = t1.createColumn("a", new IntDataType());
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        assertFalse("Two schemas containing different numbers of tables should "
                + "not be equivalent", tester.areEquivalent(s1, s2));
        Table u2 = s2.createTable("u");
        assertTrue("Adding a table to a schema should be able to make two "
                + "schemas equivalent", tester.areEquivalent(s1, s2));
    }
    
    @Test
    public void testDifferentColumnsInTables() {
        SchemaEquivalenceTester tester = new SchemaEquivalenceTester();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new CharDataType());
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        assertFalse("Two schemas with tables containing columns with different "
                + "datatypes should not be equivalent", tester.areEquivalent(s1, s2));
        a2.setDataType(new CharDataType());
        assertTrue("Changing the datatype of a column should be able to make "
                + "two schema equivalent", tester.areEquivalent(s1, s2));
    }
    
    @Test
    public void testDifferentTableOrdering() {
        SchemaEquivalenceTester tester = new SchemaEquivalenceTester();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        Table u1 = s1.createTable("u");
        Schema s2 = new Schema("s");
        Table u2 = s2.createTable("u");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        assertTrue("The ordering of tables in a schema should not affect the "
                + "detection of equivalence", tester.areEquivalent(s1, s2));
    }
}
