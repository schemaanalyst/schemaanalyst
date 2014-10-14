package org.schemaanalyst.unittest.data;

import org.junit.Test;
import org.schemaanalyst.data.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestValueEquality {

    @Test
    public void testBooleanValue() {
        BooleanValue v1 = new BooleanValue(true);
        BooleanValue v2 = new BooleanValue(true);

        assertTrue("BooleanValues should be equal",
                v1.equals(v2));

        v1.set(false);

        assertFalse("BooleanValues should not be equal",
                v1.equals(v2));

        assertFalse("BooleanValues should not be equal to null", v1 == null);

        assertFalse("BooleanValues should not be equal to values of other types",
                v1.equals(new NumericValue(0)));
    }

    @Test
    public void testDateValue() {
        DateValue v1 = new DateValue(2012, 8, 12);
        DateValue v2 = new DateValue(2012, 8, 12);

        assertTrue("DateValues should be equal",
                v1.equals(v2));

        v1.setMonth(9);

        assertFalse("DateValues should not be equal",
                v1.equals(v2));

        assertFalse("DateValues should not be equal to null", v1 == null);

        assertFalse("DateValues should not be equal to values of other types",
                v1.equals(new DateTimeValue(2012, 9, 12, 0, 0, 0)));
    }

    @Test
    public void testDateTimeValue() {
        DateTimeValue v1 = new DateTimeValue(2012, 8, 12, 7, 53, 00);
        DateTimeValue v2 = new DateTimeValue(2012, 8, 12, 7, 53, 00);

        assertTrue("DateTimeValues should be equal", v1.equals(v2));

        v1.setSecond(50);

        assertFalse("DateTimeValues should not be equal", v1.equals(v2));

        assertFalse("DateTimeValues should not be equal to null", v1 == null);

        assertFalse("DateTimeValues should not be equal to values of other types",
                v1.equals(new DateValue(2012, 9, 12)));
    }

    @Test
    public void testNumericValue() {
        NumericValue v1 = new NumericValue(10);
        NumericValue v2 = new NumericValue(10);

        assertTrue("NumericValues should be equal", v1.equals(v2));

        v1.set(20);

        assertFalse("NumericValues should not be equal", v1.equals(v2));

        assertFalse("NumericValues should not be equal to null", v1 == null);

        assertFalse("NumericValues should not be equal to values of other types",
                v1.equals(new TimestampValue(0)));
    }

    @Test
    public void testStringValue() {
        StringValue v1 = new StringValue("pizza");
        StringValue v2 = new StringValue("pizza");

        assertTrue("StringValues should be equal", v1.equals(v2));

        v1.set("chips");

        assertFalse("StringValues should not be equal", v1.equals(v2));

        assertFalse("StringValues should not be equal to null", v1 == null);

        assertFalse("StringValues should not be equal to values of other types",
                v1.equals(new NumericValue(0)));
    }

    @Test
    public void testTimestampValue() {
        TimestampValue v1 = new TimestampValue(1000);
        TimestampValue v2 = new TimestampValue(1000);

        assertTrue("TimestampValues should be equal", v1.equals(v2));

        v1.set(2000);

        assertFalse("TimestampValues should not be equal", v1.equals(v2));

        assertFalse("TimestampValues should not be equal to null", v1 == null);

        assertFalse("TimestampValues should not be equal to values of other types",
                v1.equals(new NumericValue(2000)));
    }

    @Test
    public void testTimeValue() {
        TimeValue v1 = new TimeValue(8, 0, 0);
        TimeValue v2 = new TimeValue(8, 0, 0);

        assertTrue("TimeValues should be equal", v1.equals(v2));

        v1.setMinute(10);

        assertFalse("TimeValues should not be equal", v1.equals(v2));

        assertFalse("TimeValues should not be equal to null", v1 == null);

        assertFalse("TimeValues should not be equal to values of other types",
                v1.equals(new DateTimeValue(2012, 8, 12, 8, 23, 0)));
    }
}
