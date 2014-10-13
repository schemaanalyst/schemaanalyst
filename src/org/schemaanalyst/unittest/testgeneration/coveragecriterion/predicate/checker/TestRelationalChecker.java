package org.schemaanalyst.unittest.testgeneration.coveragecriterion.predicate.checker;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.RelationalChecker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.schemaanalyst.logic.RelationalOperator.*;

/**
 * Created by phil on 13/10/2014.
 */
public class TestRelationalChecker {

    private boolean check(Value lhs, RelationalOperator op, Value rhs, boolean allowNull) {
        return new RelationalChecker(lhs, op, rhs, allowNull).check();
    }

    @Test
    public void testNumericExpressions() {
        assertTrue(check(new NumericValue(20), EQUALS, null, true));
        assertFalse(check(new NumericValue(20), EQUALS, null, false));

        assertTrue(check(new NumericValue(10), EQUALS, new NumericValue(10), false));
        assertFalse(check(new NumericValue(20), EQUALS, new NumericValue(10), false));

        assertFalse(check(new NumericValue(10), NOT_EQUALS, new NumericValue(10), false));
        assertTrue(check(new NumericValue(20), NOT_EQUALS, new NumericValue(10), false));

        assertFalse(check(new NumericValue(10), GREATER, new NumericValue(10), false));
        assertTrue(check(new NumericValue(20), GREATER, new NumericValue(10), false));
        assertFalse(check(new NumericValue(10), GREATER, new NumericValue(20), false));

        assertFalse(check(new NumericValue(10), LESS, new NumericValue(10), false));
        assertTrue(check(new NumericValue(10), LESS, new NumericValue(20), false));
        assertFalse(check(new NumericValue(30), LESS, new NumericValue(20), false));

        assertTrue(check(new NumericValue(10), GREATER_OR_EQUALS, new NumericValue(10), false));
        assertTrue(check(new NumericValue(20), GREATER_OR_EQUALS, new NumericValue(10), false));
        assertFalse(check(new NumericValue(5), GREATER_OR_EQUALS, new NumericValue(10), false));

        assertTrue(check(new NumericValue(10), LESS_OR_EQUALS, new NumericValue(10), false));
        assertTrue(check(new NumericValue(10), LESS_OR_EQUALS, new NumericValue(20), false));
        assertFalse(check(new NumericValue(30), LESS_OR_EQUALS, new NumericValue(20), false));
    }

    @Test
    public void testCompoundExpressionsEqualValues() {
        assertTrue(check(new StringValue("hello"), EQUALS, new StringValue("hello"), false));
        assertFalse(check(new StringValue("hello"), NOT_EQUALS, new StringValue("hello"), false));
        assertFalse(check(new StringValue("hello"), GREATER, new StringValue("hello"), false));
        assertTrue(check(new StringValue("hello"), GREATER_OR_EQUALS, new StringValue("hello"), false));
        assertFalse(check(new StringValue("hello"), LESS, new StringValue("hello"), false));
        assertTrue(check(new StringValue("hello"), LESS_OR_EQUALS, new StringValue("hello"), false));
    }

    @Test
    public void testCompoundExpressionsEqualToSameLengthButLHSLonger() {
        assertFalse(check(new StringValue("helloyou"), EQUALS, new StringValue("hello"), false));
        assertTrue(check(new StringValue("helloyou"), NOT_EQUALS, new StringValue("hello"), false));
        assertTrue(check(new StringValue("helloyou"), GREATER, new StringValue("hello"), false));
        assertTrue(check(new StringValue("helloyou"), GREATER_OR_EQUALS, new StringValue("hello"), false));
        assertFalse(check(new StringValue("helloyou"), LESS, new StringValue("hello"), false));
        assertFalse(check(new StringValue("helloyou"), LESS_OR_EQUALS, new StringValue("hello"), false));
    }

    @Test
    public void testCompoundExpressionsEqualToSameLengthButRHSLonger() {
        assertFalse(check(new StringValue("hello"), EQUALS, new StringValue("hellogoodbye"), false));
        assertTrue(check(new StringValue("hello"), NOT_EQUALS, new StringValue("hellogoodbye"), false));
        assertFalse(check(new StringValue("hello"), GREATER, new StringValue("hellogoodbye"), false));
        assertFalse(check(new StringValue("hello"), GREATER_OR_EQUALS, new StringValue("hellogoodbye"), false));
        assertTrue(check(new StringValue("hello"), LESS, new StringValue("hellogoodbye"), false));
        assertTrue(check(new StringValue("hello"), LESS_OR_EQUALS, new StringValue("hellogoodbye"), false));
    }

    @Test
    public void testCompoundExpressionsLengthOneNonEqual() {
        assertFalse(check(new StringValue("a"), EQUALS, new StringValue("b"), false));
        assertTrue(check(new StringValue("a"), NOT_EQUALS, new StringValue("b"), false));

        assertFalse(check(new StringValue("a"), GREATER, new StringValue("b"), false));
        assertFalse(check(new StringValue("a"), GREATER_OR_EQUALS, new StringValue("b"), false));

        assertTrue(check(new StringValue("b"), GREATER, new StringValue("a"), false));
        assertTrue(check(new StringValue("b"), GREATER_OR_EQUALS, new StringValue("a"), false));

        assertTrue(check(new StringValue("a"), LESS, new StringValue("b"), false));
        assertTrue(check(new StringValue("a"), LESS_OR_EQUALS, new StringValue("b"), false));

        assertFalse(check(new StringValue("b"), LESS, new StringValue("a"), false));
        assertFalse(check(new StringValue("b"), LESS_OR_EQUALS, new StringValue("a"), false));
    }

    @Test
    public void testCompoundExpressionsLengthTwoNonEqual() {
        assertFalse(check(new StringValue("aa"), EQUALS, new StringValue("ab"), false));
        assertTrue(check(new StringValue("aa"), NOT_EQUALS, new StringValue("ab"), false));

        assertFalse(check(new StringValue("aa"), GREATER, new StringValue("ab"), false));
        assertFalse(check(new StringValue("aa"), GREATER_OR_EQUALS, new StringValue("ab"), false));
        assertTrue(check(new StringValue("ab"), GREATER, new StringValue("aa"), false));
        assertTrue(check(new StringValue("ab"), GREATER_OR_EQUALS, new StringValue("aa"), false));

        assertTrue(check(new StringValue("aa"), LESS, new StringValue("ab"), false));
        assertTrue(check(new StringValue("aa"), LESS_OR_EQUALS, new StringValue("ab"), false));

        assertFalse(check(new StringValue("ab"), LESS, new StringValue("aa"), false));
        assertFalse(check(new StringValue("ab"), LESS_OR_EQUALS, new StringValue("aa"), false));
    }

    @Test
    public void testCompoundExpressionsArbitraryLengthNonEqual() {
        assertFalse(check(new StringValue("aa"), EQUALS, new StringValue("abc"), false));
        assertTrue(check(new StringValue("aa"), NOT_EQUALS, new StringValue("abc"), false));

        assertFalse(check(new StringValue("aa"), GREATER, new StringValue("abc"), false));
        assertFalse(check(new StringValue("aa"), GREATER_OR_EQUALS, new StringValue("abc"), false));
        assertTrue(check(new StringValue("abc"), GREATER, new StringValue("aa"), false));
        assertTrue(check(new StringValue("abc"), GREATER_OR_EQUALS, new StringValue("aa"), false));

        assertTrue(check(new StringValue("aa"), LESS, new StringValue("abc"), false));
        assertTrue(check(new StringValue("aa"), LESS_OR_EQUALS, new StringValue("abc"), false));

        assertFalse(check(new StringValue("abc"), LESS, new StringValue("aa"), false));
        assertFalse(check(new StringValue("abc"), LESS_OR_EQUALS, new StringValue("aa"), false));
    }

}
