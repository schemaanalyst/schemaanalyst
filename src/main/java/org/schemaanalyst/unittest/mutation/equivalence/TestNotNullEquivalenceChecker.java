/*
 */
package org.schemaanalyst.unittest.mutation.equivalence;

import org.junit.Test;
import org.schemaanalyst.mutation.equivalence.NotNullEquivalenceChecker;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Chris J. Wright
 */
public class TestNotNullEquivalenceChecker {
    
    @Test
    public void testSameInstance() {
        NotNullEquivalenceChecker tester = new NotNullEquivalenceChecker();
        Table t1 = new Table("t");
        Column a1 = new Column("a", new IntDataType());
        Column b1 = new Column("b", new IntDataType());
        t1.addColumn(a1);
        t1.addColumn(b1);
        NotNullConstraint nn1 = new NotNullConstraint(t1, a1);
        assertTrue("A not null constraint should be equivalent to itself",
                tester.areEquivalent(nn1, nn1));
    }
    
    @Test
    public void testDifferentInstance() {
        NotNullEquivalenceChecker tester = new NotNullEquivalenceChecker();
        Table t1 = new Table("t");
        Column a1 = new Column("a", new IntDataType());
        Column b1 = new Column("b", new IntDataType());
        t1.addColumn(a1);
        t1.addColumn(b1);
        NotNullConstraint nn1 = new NotNullConstraint(t1, a1);
        NotNullConstraint nn2 = new NotNullConstraint(t1, a1);
        assertTrue("Two identical not null constraints on the same table and "
                + "column instances should be equivalent", tester.areEquivalent(nn1, nn2));
    }
    
    @Test
    public void testDifferentTableInstance() {
        NotNullEquivalenceChecker tester = new NotNullEquivalenceChecker();
        Table t1 = new Table("t");
        Table t2 = new Table("t");
        Column a1 = new Column("a", new IntDataType());
        Column b1 = new Column("b", new IntDataType());
        t1.addColumn(a1);
        t1.addColumn(b1);
        t2.addColumn(a1);
        t2.addColumn(b1);
        NotNullConstraint nn1 = new NotNullConstraint(t1, a1);
        NotNullConstraint nn2 = new NotNullConstraint(t2, a1);
        assertTrue("Two not null constraints on the separate tables with the "
                + "same identifier should be equivalent", tester.areEquivalent(nn1, nn2));
    }
    
    @Test
    public void testDifferentColumn() {
        NotNullEquivalenceChecker tester = new NotNullEquivalenceChecker();
        Table t1 = new Table("t");
        Column a1 = new Column("a", new IntDataType());
        Column b1 = new Column("b", new IntDataType());
        t1.addColumn(a1);
        t1.addColumn(b1);
        NotNullConstraint nn1 = new NotNullConstraint(t1, a1);
        NotNullConstraint nn2 = new NotNullConstraint(t1, b1);
        assertFalse("Two not null constraints on different columns should not "
                + "be equivalent", tester.areEquivalent(nn1, nn2));
        nn2.setColumn(a1);
        assertTrue("Changing the column of a not null constraint should be able"
                + " to make two not null constraints equivalent", tester.areEquivalent(nn1, nn2));
    }
    
    @Test
    public void testDifferentTable() {
        NotNullEquivalenceChecker tester = new NotNullEquivalenceChecker();
        Table t1 = new Table("t");
        Table t2 = new Table("t2");
        Column a1 = new Column("a", new IntDataType());
        Column b1 = new Column("b", new IntDataType());
        t1.addColumn(a1);
        t1.addColumn(b1);
        NotNullConstraint nn1 = new NotNullConstraint(t1, a1);
        NotNullConstraint nn2 = new NotNullConstraint(t2, b1);
        assertFalse("Two not null constraints on different tables should not "
                + "be equivalent", tester.areEquivalent(nn1, nn2));
    }
    
    @Test
    public void testDifferentIdentifier() {
        NotNullEquivalenceChecker tester = new NotNullEquivalenceChecker();
        Table t1 = new Table("t");
        Column a1 = new Column("a", new IntDataType());
        Column b1 = new Column("b", new IntDataType());
        t1.addColumn(a1);
        t1.addColumn(b1);
        NotNullConstraint nn1 = new NotNullConstraint("nn1", t1, a1);
        NotNullConstraint nn2 = new NotNullConstraint("nn2", t1, a1);
        assertFalse("Two not null constraints with different identifiers should"
                + " not be equivalent", tester.areEquivalent(nn1, nn2));
        nn2.setName("nn1");
        assertTrue("Changing the identifier on two otherwise identical not null"
                + " constraints should be able to make them equivalent",
                tester.areEquivalent(nn1, nn2));
    }
}
