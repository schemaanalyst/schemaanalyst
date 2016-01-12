/*
 */
package org.schemaanalyst.unittest.mutation.equivalence;

import org.junit.Test;
import org.schemaanalyst.mutation.equivalence.ColumnEquivalenceChecker;
import org.schemaanalyst.mutation.equivalence.TableEquivalenceChecker;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.FloatDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Chris J. Wright
 */
public class TestTableEquivalenceChecker {

    @Test
    public void testSameInstance() {
        TableEquivalenceChecker tester = new TableEquivalenceChecker(new ColumnEquivalenceChecker());
        Table t1 = new Table("t");
        t1.addColumn(new Column("a", new IntDataType()));
        assertTrue("A table should be equivalent to itself",
                tester.areEquivalent(t1, t1));
    }

    @Test
    public void testDifferentInstance() {
        TableEquivalenceChecker tester = new TableEquivalenceChecker(new ColumnEquivalenceChecker());
        Table t1 = new Table("t");
        t1.addColumn(new Column("a", new IntDataType()));
        Table t2 = new Table("t");
        t2.addColumn(new Column("a", new IntDataType()));
        assertTrue("Two identical columns should be equivalent",
                tester.areEquivalent(t1, t2));
    }
    
    @Test
    public void testDifferentName() {
        TableEquivalenceChecker tester = new TableEquivalenceChecker(new ColumnEquivalenceChecker());
        Table t = new Table("t");
        t.addColumn(new Column("a", new IntDataType()));
        Table s = new Table("s");
        s.addColumn(new Column("a", new IntDataType()));
        assertFalse("Two tables with different names should not be equivalent",
                tester.areEquivalent(s, t));
    }
    
    @Test
    public void testDifferentColumnCount() {
        TableEquivalenceChecker tester = new TableEquivalenceChecker(new ColumnEquivalenceChecker());
        Table t1 = new Table("t");
        t1.addColumn(new Column("a", new IntDataType()));
        t1.addColumn(new Column("b", new IntDataType()));
        Table t2 = new Table("t");
        t2.addColumn(new Column("a", new IntDataType()));
        assertFalse("Two tables with different column counts should not be "
                + "equivalent", tester.areEquivalent(t1, t2));
        t2.addColumn(new Column("b", new IntDataType()));
        assertTrue("Adding a column to a table should be able to make two "
                + "tables equivalent", tester.areEquivalent(t1, t2));
    }
    
    @Test
    public void testDifferentColumnTypes() {
        TableEquivalenceChecker tester = new TableEquivalenceChecker(new ColumnEquivalenceChecker());
        Table t1 = new Table("t");
        t1.addColumn(new Column("a", new IntDataType()));
        Table t2 = new Table("t");
        t2.addColumn(new Column("a", new FloatDataType()));
        assertFalse("Two tables with columns with different datatypes should "
                + "not be equivalent", tester.areEquivalent(t1, t2));
        t2.getColumn("a").setDataType(new IntDataType());
        assertTrue("Changing a column datatype within a table should be able to"
                + " make two tables equivalent", tester.areEquivalent(t1, t2));
    }
}
