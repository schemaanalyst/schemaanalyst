package org.schemaanalyst.unittest.sqlwriter;

import org.junit.Test;
import org.schemaanalyst.sqlwriter.DateWriter;

import static org.junit.Assert.*;

/**
 * Created by phil on 21/08/2014.
 */
public class TestDateWriter {

    @Test
    public void testLeapYear() {
        assertTrue(DateWriter.isLeapYear(2000));
        assertTrue(DateWriter.isLeapYear(2004));
        assertFalse(DateWriter.isLeapYear(1900));
        assertFalse(DateWriter.isLeapYear(2002));
    }

    @Test
    public void testFebDays() {
        assertEquals(DateWriter.getMaxDay(2, 2000), 29);
        assertEquals(DateWriter.getMaxDay(2, 2002), 28);
    }
}
