/*
 */
package org.schemaanalyst.unittest.mutation.equivalence;

import org.junit.Test;
import org.schemaanalyst.mutation.equivalence.PrimaryKeyEquivalenceChecker;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Chris J. Wright
 */
public class TestPrimaryKeyEquivalenceChecker {
    
    @Test
    public void testSameInstance() {
        PrimaryKeyEquivalenceChecker tester = new PrimaryKeyEquivalenceChecker();
        Table t = new Table("t");
        t.addColumn(new Column("a", new IntDataType()));
        PrimaryKeyConstraint pk = new PrimaryKeyConstraint(t, t.getColumn("a"));
        assertTrue("A primary key should be equivalent to itself",
                tester.areEquivalent(pk, pk));
    }
    
    @Test
    public void testSameTableInstance() {
        PrimaryKeyEquivalenceChecker tester = new PrimaryKeyEquivalenceChecker();
        Table t = new Table("t");
        t.addColumn(new Column("a", new IntDataType()));
        PrimaryKeyConstraint pk1 = new PrimaryKeyConstraint(t, t.getColumn("a"));
        PrimaryKeyConstraint pk2 = new PrimaryKeyConstraint(t, t.getColumn("a"));
        assertTrue("Two primary keys with the same column and table should be "
                + "equivalent", tester.areEquivalent(pk1, pk2));
    }
    
    @Test
    public void testDifferentTableInstance() {
        PrimaryKeyEquivalenceChecker tester = new PrimaryKeyEquivalenceChecker();
        Table t1 = new Table("t");
        t1.addColumn(new Column("a", new IntDataType()));
        Table t2 = new Table("t");
        t2.addColumn(new Column("a", new IntDataType()));
        PrimaryKeyConstraint pk1 = new PrimaryKeyConstraint(t1, t1.getColumn("a"));
        PrimaryKeyConstraint pk2 = new PrimaryKeyConstraint(t2, t2.getColumn("a"));
        assertTrue("Two primary keys with different instances of the same "
                + "column and table should be equiavelent",
                tester.areEquivalent(pk1, pk2));
    }
    
    @Test
    public void testDifferentTableName() {
        PrimaryKeyEquivalenceChecker tester = new PrimaryKeyEquivalenceChecker();
        Table t = new Table("t");
        t.addColumn(new Column("a", new IntDataType()));
        Table s = new Table("s");
        s.addColumn(new Column("a", new IntDataType()));
        PrimaryKeyConstraint pk1 = new PrimaryKeyConstraint(t, t.getColumn("a"));
        PrimaryKeyConstraint pk2 = new PrimaryKeyConstraint(s, s.getColumn("a"));
        assertFalse("Two primary keys on the same columns but different tables "
                + "should not be equivalent", tester.areEquivalent(pk1, pk2));
        s.setName("t");
        assertTrue("Changing a table name should be able to make two primary "
                + "keys equivalent", tester.areEquivalent(pk1, pk2));
    }
    
    @Test
    public void testDifferentColumnName() {
        PrimaryKeyEquivalenceChecker tester = new PrimaryKeyEquivalenceChecker();
        Table t1 = new Table("t");
        t1.addColumn(new Column("a", new IntDataType()));
        Table t2 = new Table("t");
        t2.addColumn(new Column("b", new IntDataType()));
        PrimaryKeyConstraint pk1 = new PrimaryKeyConstraint(t1, t1.getColumn("a"));
        PrimaryKeyConstraint pk2 = new PrimaryKeyConstraint(t2, t2.getColumn("b"));
        assertFalse("Two primary keys on the same tables but different columns "
                + "should not be equivalent", tester.areEquivalent(pk1, pk2));
        t2.getColumn("b").setName("a");
        assertTrue("Changing a column name should be able to make two primary "
                + "keys equivalent", tester.areEquivalent(pk1, pk2));
    }
    
    @Test
    public void testDifferentColumnNumber() {
        PrimaryKeyEquivalenceChecker tester = new PrimaryKeyEquivalenceChecker();
        Table t1 = new Table("t");
        t1.addColumn(new Column("a", new IntDataType()));
        t1.addColumn(new Column("b", new IntDataType()));
        Table t2 = new Table("t");
        t2.addColumn(new Column("a", new IntDataType()));
        t2.addColumn(new Column("b", new IntDataType()));
        PrimaryKeyConstraint pk1 = new PrimaryKeyConstraint(t1, t1.getColumn("a"), t1.getColumn("b"));
        PrimaryKeyConstraint pk2 = new PrimaryKeyConstraint(t2, t2.getColumn("a"));
        assertFalse("Two primary keys with different numbers of columns should "
                + "not be equivalent", tester.areEquivalent(pk1, pk2));
        pk2.setColumns(Arrays.asList(t2.getColumn("a"), t2.getColumn("b")));
        assertTrue("Adding a column to a primary key should be able to make "
                + "two primary keys equivalent", tester.areEquivalent(pk1, pk2));
    }
    
    @Test
    public void testDifferentColumnType() {
        PrimaryKeyEquivalenceChecker tester = new PrimaryKeyEquivalenceChecker();
        Table t1 = new Table("t");
        t1.addColumn(new Column("a", new IntDataType()));
        Table t2 = new Table("t");
        t2.addColumn(new Column("a", new CharDataType()));
        PrimaryKeyConstraint pk1 = new PrimaryKeyConstraint(t1, t1.getColumn("a"));
        PrimaryKeyConstraint pk2 = new PrimaryKeyConstraint(t2, t2.getColumn("a"));
        assertFalse("Two primary keys with the same table and columns but "
                + "different datatype should not be equivalent", 
                tester.areEquivalent(pk1, pk2));
    }
}
