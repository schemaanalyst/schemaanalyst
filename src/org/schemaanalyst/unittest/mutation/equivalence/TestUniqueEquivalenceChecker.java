/*
 */
package org.schemaanalyst.unittest.mutation.equivalence;

import org.junit.Test;
import org.schemaanalyst.mutation.equivalence.UniqueEquivalenceChecker;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Chris J. Wright
 */
public class TestUniqueEquivalenceChecker {
    
    @Test
    public void testSameInstance() {
        UniqueEquivalenceChecker tester = new UniqueEquivalenceChecker();
        Table t1 = new Table("t");
        t1.addColumn(new Column("a", new IntDataType()));
        UniqueConstraint u = new UniqueConstraint(t1, t1.getColumn("a"));
        assertTrue("A unique constraint should be equivalent to itself",
                tester.areEquivalent(u, u));
    }
    
    @Test
    public void testDifferentInstance() {
        UniqueEquivalenceChecker tester = new UniqueEquivalenceChecker();
        Table t1 = new Table("t");
        t1.addColumn(new Column("a", new IntDataType()));
        UniqueConstraint u1 = new UniqueConstraint(t1, t1.getColumn("a"));
        UniqueConstraint u2 = new UniqueConstraint(t1, t1.getColumn("a"));
        assertTrue("Two identical unique constraints should be equivalent",
                tester.areEquivalent(u1, u2));
    }
    
    @Test
    public void testDifferentNames() {
        UniqueEquivalenceChecker tester = new UniqueEquivalenceChecker();
        Table t1 = new Table("t");
        t1.addColumn(new Column("a", new IntDataType()));
        UniqueConstraint u1 = new UniqueConstraint("u1", t1, t1.getColumn("a"));
        UniqueConstraint u2 = new UniqueConstraint("u2", t1, t1.getColumn("a"));
        assertFalse("Two unique constraints with different names should not be "
                + "equivalent", tester.areEquivalent(u1, u2));
        u2.setName("u1");
        assertTrue("Renaming a unique constraint should be able to make two "
                + "constraints equivalent", tester.areEquivalent(u1, u2));
    }
    
    @Test
    public void testDifferentTable() {
        UniqueEquivalenceChecker tester = new UniqueEquivalenceChecker();
        Table t1 = new Table("t");
        t1.addColumn(new Column("a", new IntDataType()));
        UniqueConstraint u1 = new UniqueConstraint(t1, t1.getColumn("a"));
        Table t2 = new Table("t");
        t2.addColumn(new Column("a", new IntDataType()));
        UniqueConstraint u2 = new UniqueConstraint(t2, t2.getColumn("a"));
        assertTrue("Two unique constraints on identical tables should be "
                + "equivalent", tester.areEquivalent(u1, u2));
    }
    
    @Test
    public void testDifferentColumnCount() {
        UniqueEquivalenceChecker tester = new UniqueEquivalenceChecker();
        Table t1 = new Table("t");
        t1.addColumn(new Column("a", new IntDataType()));
        t1.addColumn(new Column("b", new IntDataType()));
        UniqueConstraint u1 = new UniqueConstraint(t1, t1.getColumn("a"), t1.getColumn("b"));
        Table t2 = new Table("t");
        t2.addColumn(new Column("a", new IntDataType()));
        t2.addColumn(new Column("b", new IntDataType()));
        UniqueConstraint u2 = new UniqueConstraint(t2, t2.getColumn("a"));
        assertFalse("Two unique constraints with different column counts should"
                + " be equivalent", tester.areEquivalent(u1, u2));
        u2.setColumns(Arrays.asList(t2.getColumn("a"), t2.getColumn("b")));
        assertTrue("Adding a column to a unique consraint should be able to "
                + "make two constraints equivalent", tester.areEquivalent(u1, u2));
    }
    
    @Test
    public void testNonEquivalentTables() {
        UniqueEquivalenceChecker tester = new UniqueEquivalenceChecker();
        Table t1 = new Table("t");
        t1.addColumn(new Column("a", new IntDataType()));
        t1.addColumn(new Column("b", new IntDataType()));
        UniqueConstraint u1 = new UniqueConstraint(t1, t1.getColumn("a"));
        Table t2 = new Table("t");
        t2.addColumn(new Column("a", new IntDataType()));
        UniqueConstraint u2 = new UniqueConstraint(t2, t2.getColumn("a"));
        assertTrue("Otherwise equivalent constraints should still be "
                + "equivalent even if the underlying tables are not",
                tester.areEquivalent(u1, u2));
    }
}
