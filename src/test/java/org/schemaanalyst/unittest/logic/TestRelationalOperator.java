package org.schemaanalyst.unittest.logic;

import org.junit.Test;
import org.schemaanalyst.logic.RelationalOperatorException;

import static org.junit.Assert.assertEquals;
import static org.schemaanalyst.logic.RelationalOperator.*;

public class TestRelationalOperator {

    @Test
    public void testValueOf() {
        assertEquals(EQUALS, valueOf("EQUALS"));

        assertEquals(NOT_EQUALS, valueOf("NOT_EQUALS"));

        assertEquals(GREATER, valueOf("GREATER"));

        assertEquals(LESS, valueOf("LESS"));

        assertEquals(GREATER_OR_EQUALS, valueOf("GREATER_OR_EQUALS"));

        assertEquals(LESS_OR_EQUALS, valueOf("LESS_OR_EQUALS"));

    }

    @Test
    public void testGetRelationalOperator() {
        assertEquals(EQUALS, getRelationalOperator("="));

        assertEquals(NOT_EQUALS, getRelationalOperator("!="));

        assertEquals(GREATER, getRelationalOperator(">"));

        assertEquals(LESS, getRelationalOperator("<"));

        assertEquals(GREATER_OR_EQUALS, getRelationalOperator(">="));

        assertEquals(LESS_OR_EQUALS, getRelationalOperator("<="));
    }

    @Test(expected = RelationalOperatorException.class)
    public void testGetRelationalOperatorException() {
        getRelationalOperator("==");
    }

    @Test
    public void testInverse() {
        assertEquals(EQUALS, NOT_EQUALS.inverse());

        assertEquals(NOT_EQUALS, EQUALS.inverse());

        assertEquals(GREATER, LESS_OR_EQUALS.inverse());

        assertEquals(LESS, GREATER_OR_EQUALS.inverse());

        assertEquals(GREATER_OR_EQUALS, LESS.inverse());

        assertEquals(LESS_OR_EQUALS, GREATER.inverse());
    }
}
