package org.schemaanalyst.test.coverage.criterion.predicate;

import org.junit.Before;
import org.junit.Test;
import org.schemaanalyst.coverage.criterion.clause.ClauseFactory;
import org.schemaanalyst.coverage.criterion.clause.MatchClause;
import org.schemaanalyst.coverage.criterion.clause.NullClause;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.test.testutil.mock.SimpleSchema;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by phil on 24/02/2014.
 */
public class TestPredicate {

    private Schema schema;
    private Table tab1;
    private Column tab1Col1, tab1Col2;

    @Before
    public void loadSchema() {
        schema = new SimpleSchema();
        tab1 = schema.getTable("Tab1");
        tab1Col1 = tab1.getColumn("Tab1Col1");
        tab1Col2 = tab1.getColumn("Tab1Col2");
    }

    @Test
    public void testEquality() {
        Predicate p1 = new Predicate("predicate1");
        Predicate p2 = new Predicate("predicate2");

        NullClause nullClause1 = new NullClause(tab1, tab1Col1, true);
        NullClause nullClause2 = new NullClause(tab1, tab1Col2, true);

        p1.addClause(nullClause1);
        p1.addClause(nullClause2);

        p2.addClause(nullClause2);
        p2.addClause(nullClause1);

        assertEquals(p1, p2);
    }

    @Test
    public void testNonEquality() {
        Predicate p1 = new Predicate("predicate1");
        Predicate p2 = new Predicate("predicate2");

        NullClause nullClause1 = new NullClause(tab1, tab1Col1, true);
        NullClause nullClause2 = new NullClause(tab1, tab1Col2, true);

        p1.addClause(nullClause1);

        p2.addClause(nullClause2);

        assertNotEquals(p1, p2);
    }

    @Test
    public void testSetNullNotAlreadyNull() {
        NullClause nc = new NullClause(tab1, tab1Col1, true);
        Predicate p = new Predicate("Test predicate");

        assertFalse(p.hasClause(nc));
        p.setColumnNullStatus(tab1, tab1Col1, true);
        assertTrue(p.hasClause(nc));
    }

    @Test
    public void testSetNullAnotherColumnNull() {
        NullClause nc1 = new NullClause(tab1, tab1Col1, true);
        NullClause nc2 = new NullClause(tab1, tab1Col2, true);
        Predicate p = new Predicate("Test predicate");
        p.addClause(nc2);

        assertFalse(p.hasClause(nc1));
        assertTrue(p.hasClause(nc2));
        p.setColumnNullStatus(tab1, tab1Col1, true);
        assertTrue(p.hasClause(nc1));
        assertTrue(p.hasClause(nc2));
    }

    @Test
    public void testSetNullAlreadyNull() {
        NullClause nc1 = new NullClause(tab1, tab1Col1, true);
        Predicate p = new Predicate("Test predicate");
        p.addClause(nc1);

        assertTrue(p.hasClause(nc1));
        p.setColumnNullStatus(tab1, tab1Col1, true);
        assertTrue(p.hasClause(nc1));
        assertEquals("There should be exactly 1 clause", 1, p.getClauses().size());
    }

    @Test
    public void testSetNullAlreadyNotNull() {
        NullClause notNullClause = new NullClause(tab1, tab1Col1, false);
        NullClause nullClause = new NullClause(tab1, tab1Col1, true);

        Predicate p = new Predicate("Test predicate");
        p.addClause(notNullClause);
        assertTrue(p.hasClause(notNullClause));

        p.setColumnNullStatus(tab1, tab1Col1, true);
        assertFalse(p.hasClause(notNullClause));
        assertTrue(p.hasClause(nullClause));
    }

    @Test
    public void testSetNotNullAlreadyNull() {
        NullClause notNullClause = new NullClause(tab1, tab1Col1, false);
        NullClause nullClause = new NullClause(tab1, tab1Col1, true);

        Predicate p = new Predicate("Test predicate");
        p.addClause(nullClause);
        assertTrue(p.hasClause(nullClause));

        p.setColumnNullStatus(tab1, tab1Col1, false);
        assertFalse(p.hasClause(nullClause));
        assertTrue(p.hasClause(notNullClause));
    }

    @Test
    public void testSetUnique() {
        MatchClause uc = ClauseFactory.unique(tab1, tab1Col1, true);
        Predicate p = new Predicate("Test predicate");

        assertFalse(p.hasClause(uc));
        p.setColumnUniqueStatus(tab1, tab1Col1, true);
        assertTrue(p.hasClause(uc));
    }

    @Test
    public void testFlipUnique() {
        MatchClause uc1 = ClauseFactory.unique(tab1, tab1Col1, true);
        MatchClause uc2 = ClauseFactory.notUnique(tab1, tab1Col1);

        Predicate p = new Predicate("Test predicate");
        p.addClause(uc1);
        assertTrue(p.hasClause(uc1));
        assertFalse(p.hasClause(uc2));

        p.setColumnUniqueStatus(tab1, tab1Col1, false);
        assertFalse(p.hasClause(uc1));
        assertTrue(p.hasClause(uc2));
    }

     @Test
     public void testSubColumnsUnique() {
        MatchClause uc1 = ClauseFactory.unique(tab1, Arrays.asList(tab1Col1, tab1Col2), true);
        MatchClause uc2 = ClauseFactory.unique(tab1, tab1Col1, true);
        MatchClause uc3 = ClauseFactory.unique(tab1, tab1Col2, true);

        Predicate p = new Predicate("Test predicate");
        p.addClause(uc1);
        assertTrue(p.hasClause(uc1));
        assertFalse(p.hasClause(uc2));
        assertFalse(p.hasClause(uc3));

        p.setColumnUniqueStatus(tab1, tab1Col1, true);
        assertFalse(p.hasClause(uc1));
        assertTrue(p.hasClause(uc2));
        assertTrue(p.hasClause(uc3));
    }

    @Test
    public void testSubColumnsSwitchUnique() {
        MatchClause uc1 = ClauseFactory.unique(tab1, Arrays.asList(tab1Col1, tab1Col2), true);
        MatchClause uc2 = ClauseFactory.notUnique(tab1, tab1Col1);
        MatchClause uc3 = ClauseFactory.unique(tab1, tab1Col2, true);

        Predicate p = new Predicate("Test predicate");
        p.addClause(uc1);
        assertTrue(p.hasClause(uc1));
        assertFalse(p.hasClause(uc2));
        assertFalse(p.hasClause(uc3));

        p.setColumnUniqueStatus(tab1, tab1Col1, false);
        assertFalse(p.hasClause(uc1));
        assertTrue(p.hasClause(uc2));
        assertTrue(p.hasClause(uc3));
    }

    @Test
    public void testAlreadyUnique() {
        MatchClause uc = ClauseFactory.unique(tab1, tab1Col1, true);
        Predicate p = new Predicate("Test predicate");
        p.addClause(uc);
        p.setColumnUniqueStatus(tab1, tab1Col1, true);
        assertTrue(p.hasClause(uc));
        assertEquals("There should be exactly 1 clause", 1, p.getClauses().size());
    }
}
