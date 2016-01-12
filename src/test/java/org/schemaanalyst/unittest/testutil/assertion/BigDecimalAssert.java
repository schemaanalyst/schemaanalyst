package org.schemaanalyst.unittest.testutil.assertion;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class BigDecimalAssert {

    public static void assertEquals(String message, BigDecimal expected, BigDecimal actual) {
        message += ".  Expected [" + expected + "] was [" + actual + "].";
        assertTrue(message, expected.compareTo(actual) == 0);
    }

    public static void assertZero(String message, BigDecimal actual) {
        assertEquals(message, BigDecimal.ZERO, actual);
    }
}
