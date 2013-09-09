/*
 */
package org.schemaanalyst.test.mutation.redundancy;

import org.junit.Test;
import static org.junit.Assert.*;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.mutation.redundancy.CheckEquivalenceTester;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.expression.BetweenExpression;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;

/**
 *
 * @author Chris J. Wright
 */
public class TestCheckEquivalenceTester {

    {
        assertTrue(true);
    }

    @Test
    public void testSameInstance() {
        CheckEquivalenceTester tester = new CheckEquivalenceTester();
        Table t = new Table("t");
        CheckConstraint cc = new CheckConstraint(t, new BetweenExpression(
                new ConstantExpression(new NumericValue(3)),
                new ConstantExpression(new NumericValue(1)),
                new ConstantExpression(new NumericValue(5)),
                false, true));
        assertTrue("A check constraint should be equivalent to itself",
                tester.areEquivalent(cc, cc));
    }
    
    @Test
    public void testDifferentInstance() {
        CheckEquivalenceTester tester = new CheckEquivalenceTester();
        Table t = new Table("t");
        CheckConstraint cc1 = new CheckConstraint(t, new BetweenExpression(
                new ConstantExpression(new NumericValue(3)),
                new ConstantExpression(new NumericValue(1)),
                new ConstantExpression(new NumericValue(5)),
                false, true));
        CheckConstraint cc2 = new CheckConstraint(t, new BetweenExpression(
                new ConstantExpression(new NumericValue(3)),
                new ConstantExpression(new NumericValue(1)),
                new ConstantExpression(new NumericValue(5)),
                false, true));
        assertTrue("Two identical check constraints on the same table should "
                + "be equivalent", tester.areEquivalent(cc1, cc2));
    }
    
    @Test
    public void testDifferentTables() {
        CheckEquivalenceTester tester = new CheckEquivalenceTester();
        Table t1 = new Table("t");
        CheckConstraint cc1 = new CheckConstraint(t1, new BetweenExpression(
                new ConstantExpression(new NumericValue(3)),
                new ConstantExpression(new NumericValue(1)),
                new ConstantExpression(new NumericValue(5)),
                false, true));
        Table t2 = new Table("t");
        t2.addColumn(new Column("a", new IntDataType()));
        CheckConstraint cc2 = new CheckConstraint(t2, new BetweenExpression(
                new ConstantExpression(new NumericValue(3)),
                new ConstantExpression(new NumericValue(1)),
                new ConstantExpression(new NumericValue(5)),
                false, true));
        assertTrue("Two identical check constraints on two identical tables "
                + "should be equivalent", tester.areEquivalent(cc1, cc2));
    }
    
    @Test
    public void testDifferentButIdenticalTables() {
        CheckEquivalenceTester tester = new CheckEquivalenceTester();
        Table t1 = new Table("t");
        CheckConstraint cc1 = new CheckConstraint(t1, new BetweenExpression(
                new ConstantExpression(new NumericValue(3)),
                new ConstantExpression(new NumericValue(1)),
                new ConstantExpression(new NumericValue(5)),
                false, true));
        Table t2 = new Table("t");
        CheckConstraint cc2 = new CheckConstraint(t2, new BetweenExpression(
                new ConstantExpression(new NumericValue(3)),
                new ConstantExpression(new NumericValue(1)),
                new ConstantExpression(new NumericValue(5)),
                false, true));
        assertTrue("Two identical check constraints on two identical tables "
                + "should be equivalent", tester.areEquivalent(cc1, cc2));
    }
    
    @Test
    public void testDifferentExpression() {
        CheckEquivalenceTester tester = new CheckEquivalenceTester();
        Table t1 = new Table("t");
        CheckConstraint cc1 = new CheckConstraint(t1, new BetweenExpression(
                new ConstantExpression(new NumericValue(3)),
                new ConstantExpression(new NumericValue(1)),
                new ConstantExpression(new NumericValue(5)),
                false, true));
        Table t2 = new Table("t");
        CheckConstraint cc2 = new CheckConstraint(t2, new BetweenExpression(
                new ConstantExpression(new NumericValue(3)),
                new ConstantExpression(new NumericValue(1)),
                new ConstantExpression(new NumericValue(6)),
                false, true));
        assertFalse("Two different constraints should not be equivalent",
                tester.areEquivalent(cc1, cc2));
    }
    
    @Test
    public void testSameExpressionDifferentInstances() {
        CheckEquivalenceTester tester = new CheckEquivalenceTester();
        Table t1 = new Table("t");
        t1.addColumn(new Column("a", new IntDataType()));
        CheckConstraint cc1 = new CheckConstraint(t1, new BetweenExpression(
                new ColumnExpression(t1, t1.getColumn("a")),
                new ConstantExpression(new NumericValue(1)),
                new ConstantExpression(new NumericValue(5)),
                false, true));
        Table t2 = new Table("t");
        t2.addColumn(new Column("a", new IntDataType()));
        CheckConstraint cc2 = new CheckConstraint(t2, new BetweenExpression(
                new ColumnExpression(t2, t2.getColumn("a")),
                new ConstantExpression(new NumericValue(1)),
                new ConstantExpression(new NumericValue(5)),
                false, true));
        assertTrue("Two constraints referencing the same column in different "
                + "tables should be equivalent", tester.areEquivalent(cc1, cc2));
    }
}
