package deprecated.test.mutation;


import org.junit.Test;

import static org.junit.Assert.*;
import deprecated.mutation.MutantRecord;
import org.schemaanalyst.mutation.analysis.result.SQLExecutionRecord;
import org.schemaanalyst.mutation.analysis.result.SQLInsertRecord;

public class TestSQLInsertRecord {

    @Test
    public void testDefaultIsTryingToNegate() {
        SQLInsertRecord record = new SQLInsertRecord("statement", 0);
        assertFalse(record.isSatisfying());
    }

    @Test
    public void testTryingToSatisfy() {
        SQLInsertRecord record = new SQLInsertRecord("statement", 0);
        record.setSatisfying(true);
        assertTrue(record.isSatisfying());
    }

    @Test
    public void testTryingToNegate() {
        SQLInsertRecord record = new SQLInsertRecord("statement", 0);
        record.setSatisfying(false);
        assertFalse(record.isSatisfying());
    }

    @Test
    public void testSQLExecutionRecordDefaultCodeAndStatement() {
        SQLExecutionRecord record = new SQLExecutionRecord();
        assertEquals(0, record.getReturnCode());
        assertEquals("none", record.getStatement());
    }

    @Test
    public void testMutantRecordDefaultState() {
        MutantRecord record = new MutantRecord();
        assertEquals(0, record.getReturnCode());
        assertEquals("none", record.getStatement());
    }

    @Test
    public void testDefaultIsTryingToNegateMR() {
        MutantRecord record = new MutantRecord();
        assertFalse(record.isSatisfying());
    }

    @Test
    public void testMutantKillingDefault() {
        MutantRecord record = new MutantRecord();
        assertFalse(record.isKilled());
    }

    @Test
    public void testMutantKilling() {
        MutantRecord record = new MutantRecord();
        assertFalse(record.isKilled());
        record.setKilled(true);
        assertTrue(record.isKilled());
    }
}