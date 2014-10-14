package org.schemaanalyst.unittest.testgeneration.coveragecriterion.predicate;


import org.junit.Test;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.AndPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.NullPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;

import static org.junit.Assert.*;

/**
 * Created by phil on 24/07/2014.
 */
public class TestComposedPredicate {

    Table table = new Table("tab");
    Column column1 = new Column("column1", new IntDataType());
    Column column2 = new Column("column2", new IntDataType());
    Column column3 = new Column("column3", new IntDataType());

    @Test
    public void testAddSamePredicate() {
        NullPredicate nullPredicate2 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate1 = new NullPredicate(table, column1, true);

        AndPredicate andPredicate = new AndPredicate();
        andPredicate.addPredicate(nullPredicate1);
        andPredicate.addPredicate(nullPredicate1);
        andPredicate.addPredicate(nullPredicate2);

        assertEquals(1, andPredicate.getSubPredicates().size());
    }

    @Test
    public void testSingularComposedOneLevel() {
        NullPredicate nullPredicate = new NullPredicate(table, column1, true);

        AndPredicate andPredicate = new AndPredicate();
        andPredicate.addPredicate(nullPredicate);

        Predicate reduced = andPredicate.reduce();
        assertTrue(reduced.equals(nullPredicate));
    }

    @Test
    public void testSingularComposedTwoLevel() {
        NullPredicate nullPredicate2 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate1 = new NullPredicate(table, column2, false);

        AndPredicate andPredicate1 = new AndPredicate();
        andPredicate1.addPredicate(nullPredicate1);

        AndPredicate andPredicate2 = new AndPredicate();
        andPredicate2.addPredicate(nullPredicate2);
        andPredicate1.addPredicate(andPredicate2);

        assertFalse(andPredicate1.contains(nullPredicate2));

        AndPredicate reduced = (AndPredicate) andPredicate1.reduce();
        assertTrue(reduced.contains(nullPredicate2));
    }

    @Test
    public void testMergeOneLevel() {
        NullPredicate nullPredicate1 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate2 = new NullPredicate(table, column1, false);
        NullPredicate nullPredicate3 = new NullPredicate(table, column2, true);
        NullPredicate nullPredicate4 = new NullPredicate(table, column2, false);

        AndPredicate andPredicate1 = new AndPredicate();
        andPredicate1.addPredicate(nullPredicate1);
        andPredicate1.addPredicate(nullPredicate2);

        AndPredicate andPredicate2 = new AndPredicate();
        andPredicate2.addPredicate(nullPredicate3);
        andPredicate2.addPredicate(nullPredicate4);
        andPredicate1.addPredicate(andPredicate2);

        assertEquals("Num predicates in andPredicate1", 3, andPredicate1.numSubPredicates());
        assertEquals("Num predicates in andPredicate2", 2, andPredicate2.numSubPredicates());

        AndPredicate reduced = (AndPredicate) andPredicate1.reduce();
        assertEquals("Num predicates in reduced", 4, reduced.numSubPredicates());
    }

    @Test
    public void testMergeOneLevelSameSubPredicates() {
        NullPredicate nullPredicate1 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate2 = new NullPredicate(table, column1, false);

        AndPredicate andPredicate1 = new AndPredicate();
        andPredicate1.addPredicate(nullPredicate1);
        andPredicate1.addPredicate(nullPredicate2);

        AndPredicate andPredicate2 = new AndPredicate();
        andPredicate2.addPredicate(nullPredicate1);
        andPredicate2.addPredicate(nullPredicate2);
        andPredicate1.addPredicate(andPredicate2);

        assertEquals("Num predicates in andPredicate1", 3, andPredicate1.numSubPredicates());
        assertEquals("Num predicates in andPredicate2", 2, andPredicate2.numSubPredicates());

        AndPredicate reduced = (AndPredicate) andPredicate1.reduce();
        assertEquals("Num predicates in reduced", 2, reduced.numSubPredicates());
    }

    @Test
    public void testMergeTwoLevels() {
        NullPredicate nullPredicate1 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate2 = new NullPredicate(table, column1, false);
        NullPredicate nullPredicate3 = new NullPredicate(table, column2, true);
        NullPredicate nullPredicate4 = new NullPredicate(table, column2, false);
        NullPredicate nullPredicate5 = new NullPredicate(table, column3, true);
        NullPredicate nullPredicate6 = new NullPredicate(table, column3, false);

        AndPredicate andPredicate1 = new AndPredicate();
        andPredicate1.addPredicate(nullPredicate1);
        andPredicate1.addPredicate(nullPredicate2);

        AndPredicate andPredicate2 = new AndPredicate();
        andPredicate2.addPredicate(nullPredicate3);
        andPredicate2.addPredicate(nullPredicate4);
        andPredicate1.addPredicate(andPredicate2);

        AndPredicate andPredicate3 = new AndPredicate();
        andPredicate3.addPredicate(nullPredicate5);
        andPredicate3.addPredicate(nullPredicate6);
        andPredicate2.addPredicate(andPredicate3);

        assertEquals("Num predicates in andPredicate1", 3, andPredicate1.numSubPredicates());
        assertEquals("Num predicates in andPredicate2", 3, andPredicate2.numSubPredicates());
        assertEquals("Num predicates in andPredicate3", 2, andPredicate3.numSubPredicates());

        AndPredicate reduced = (AndPredicate) andPredicate1.reduce();
        assertEquals("Num predicates in reduced", 6, reduced.numSubPredicates());
    }

    @Test
    public void testEquals() {
        NullPredicate nullPredicate1 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate2 = new NullPredicate(table, column1, false);

        NullPredicate nullPredicate3 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate4 = new NullPredicate(table, column1, false);


        AndPredicate andPredicate1 = new AndPredicate();
        andPredicate1.addPredicate(nullPredicate1);
        andPredicate1.addPredicate(nullPredicate2);

        AndPredicate andPredicate2 = new AndPredicate();
        andPredicate2.addPredicate(nullPredicate3);
        andPredicate2.addPredicate(nullPredicate4);

        assertTrue(andPredicate1.equals(andPredicate2));
    }

    @Test
    public void testNotEquals() {
        NullPredicate nullPredicate1 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate2 = new NullPredicate(table, column1, false);

        NullPredicate nullPredicate3 = new NullPredicate(table, column2, true);
        NullPredicate nullPredicate4 = new NullPredicate(table, column2, false);


        AndPredicate andPredicate1 = new AndPredicate();
        andPredicate1.addPredicate(nullPredicate1);
        andPredicate1.addPredicate(nullPredicate2);

        AndPredicate andPredicate2 = new AndPredicate();
        andPredicate2.addPredicate(nullPredicate3);
        andPredicate2.addPredicate(nullPredicate4);

        assertFalse(andPredicate1.equals(andPredicate2));
    }

    @Test
    public void testEqualsTwoLevels() {
        NullPredicate nullPredicate = new NullPredicate(table, column1, true);

        AndPredicate andPredicate1 = new AndPredicate();
        AndPredicate andPredicate2 = new AndPredicate();
        andPredicate2.addPredicate(nullPredicate);
        andPredicate1.addPredicate(andPredicate2);

        AndPredicate andPredicate3 = new AndPredicate();
        AndPredicate andPredicate4 = new AndPredicate();
        andPredicate4.addPredicate(nullPredicate);
        andPredicate3.addPredicate(andPredicate2);

        assertTrue(andPredicate1.equals(andPredicate3));
    }
}
