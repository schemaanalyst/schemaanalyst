package org.schemaanalyst.test.mutation;


import org.junit.Test;
import static org.junit.Assert.*;

import org.schemaanalyst.mutation.SQLInsertRecord;
import org.schemaanalyst.mutation.MutantRecord;
import org.schemaanalyst.mutation.SQLExecutionRecord;

public class TestSQLInsertRecord {

    @Test
    public void testDefaultIsTryingToNegate() {
        SQLInsertRecord record = new SQLInsertRecord();
        assertTrue(record.isTryingToNegate());
        assertTrue(!record.isTryingToSatisfy());
    }

    @Test
    public void testNowTryingToSatisfy() {
        SQLInsertRecord record = new SQLInsertRecord();
        record.tryToSatisfy();
        assertTrue(!record.isTryingToNegate());
        assertTrue(record.isTryingToSatisfy());
    }

    @Test
    public void testNowTryingToNegate() {
        SQLInsertRecord record = new SQLInsertRecord();
        record.tryToNegate();
        assertTrue(record.isTryingToNegate());
        assertTrue(!record.isTryingToSatisfy());
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
        assertTrue(record.isTryingToNegate());
        assertTrue(!record.isTryingToSatisfy());
    }

    @Test
    public void testNowTryingToSatisfyMR() {
        MutantRecord record = new MutantRecord();
        record.tryToSatisfy();
        assertTrue(!record.isTryingToNegate());
        assertTrue(record.isTryingToSatisfy());
    }

    @Test
    public void testNowTryingToNegateMR() {
        MutantRecord record = new MutantRecord();
        record.tryToNegate();
        assertTrue(record.isTryingToNegate());
        assertTrue(!record.isTryingToSatisfy());
    }

    @Test
    public void testMutantKillingDefault() {
        MutantRecord record = new MutantRecord();
        assertTrue(!record.isKilled());
    }

    @Test
    public void testMutantKillingTryOneNow() {
        MutantRecord record = new MutantRecord();
        assertTrue(!record.isKilled());
        record.setKilled(true);
        assertTrue(record.isKilled());
    }
}