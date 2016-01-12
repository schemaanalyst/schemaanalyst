/*
 */
package org.schemaanalyst.unittest.mutation.equivalence;

import org.junit.Test;
import org.schemaanalyst.mutation.equivalence.ColumnEquivalenceChecker;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Chris J. Wright
 */
public class TestColumnEquivalenceChecker {
    
    @Test
    public void testSameInstance() {
        ColumnEquivalenceChecker tester = new ColumnEquivalenceChecker();
        Column a = new Column("a", new IntDataType());
        assertTrue("A column should be equivalent to itself",
                tester.areEquivalent(a, a));
    }
    
    @Test
    public void testDifferentInstance() {
        ColumnEquivalenceChecker tester = new ColumnEquivalenceChecker();
        Column a = new Column("a", new IntDataType());
        Column b = new Column("a", new IntDataType());
        assertTrue("Two identical columns should be equivalent",
                tester.areEquivalent(a, b));
    }
    
    @Test
    public void testDifferentName() {
        ColumnEquivalenceChecker tester = new ColumnEquivalenceChecker();
        Column a = new Column("a", new IntDataType());
        Column b = new Column("b", new IntDataType());
        assertFalse("Two columns with different names should not be equivalent",
                tester.areEquivalent(a, b));
        b.setName("a");
        assertTrue("Changing a column name should be able to make two columns "
                + "equivalent", tester.areEquivalent(a, b));
    }
    
    @Test
    public void testDifferentDatatype() {
        ColumnEquivalenceChecker tester = new ColumnEquivalenceChecker();
        Column a = new Column("a", new IntDataType());
        Column b = new Column("a", new CharDataType());
        assertFalse("Two columns with different data types should not be "
                + "equivalent", tester.areEquivalent(a, b));
        b.setDataType(new IntDataType());
        assertTrue("Changing a column datatype should be able to make two "
                + "columns equivalent", tester.areEquivalent(a, b));
    }
    
    @Test
    public void testSameDatatypeDifferentLength() {
        ColumnEquivalenceChecker tester = new ColumnEquivalenceChecker();
        Column a = new Column("a", new VarCharDataType(10));
        Column b = new Column("a", new VarCharDataType(20));
        assertFalse("Two columns with different data types should not be "
                + "equivalent", tester.areEquivalent(a, b));
        b.setDataType(new VarCharDataType(10));
        assertTrue("Changing a column datatype should be able to make two "
                + "columns equivalent", tester.areEquivalent(a, b));
    }
}
