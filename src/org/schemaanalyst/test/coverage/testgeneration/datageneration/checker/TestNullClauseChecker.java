package org.schemaanalyst.test.coverage.testgeneration.datageneration.checker;

import org.junit.Test;
import org.schemaanalyst.testgeneration.coveragecriterion.clause.NullClause;
import org.schemaanalyst.data.generation.checker.NullClauseChecker;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.test.testutil.mock.OneColumnMockDatabase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by phil on 01/03/2014.
 */
public class TestNullClauseChecker {

    @Test
    public void testOneSatisfySuccess() {
        List<Integer> values = new ArrayList<>();
        values.add(null);
        testNullClauseChecker(values, true, true);
    }

    @Test
    public void testOneSatisfyFail() {
        List<Integer> values = new ArrayList<>();
        values.add(1);
        testNullClauseChecker(values, true, false);
    }

    @Test
    public void testMultipleSatisfySuccess() {
        List<Integer> values = new ArrayList<>();
        values.add(null);
        values.add(null);
        testNullClauseChecker(values, true, true);
    }

    @Test
    public void testMultipleSatisfyFail() {
        List<Integer> values = new ArrayList<>();
        values.add(null);
        values.add(1);
        testNullClauseChecker(values, true, false);
    }

    @Test
    public void testOneNotSatisfySuccess() {
        List<Integer> values = new ArrayList<>();
        values.add(1);
        testNullClauseChecker(values, false, true);
    }

    @Test
    public void testOneNotSatisfyFail() {
        List<Integer> values = new ArrayList<>();
        values.add(null);
        testNullClauseChecker(values, false, false);
    }

    @Test
    public void testMultipleNotSatisfySuccess() {
        List<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(1);
        testNullClauseChecker(values, false, true);
    }

    @Test
    public void testMultipleNotSatisfyFail() {
        List<Integer> values = new ArrayList<>();
        values.add(null);
        values.add(1);
        testNullClauseChecker(values, false, false);
    }


    public void testNullClauseChecker(List<Integer> values, boolean satisfy, boolean result) {
        OneColumnMockDatabase database = new OneColumnMockDatabase();
        Data data = database.createData(values.size());
        database.setDataValues(values);

        NullClause nullClause = new NullClause(
                database.table,
                database.column,
                satisfy
        );


        NullClauseChecker nullClauseChecker = new NullClauseChecker(nullClause, data);
        assertEquals(nullClauseChecker.check(), result);

    }
}
