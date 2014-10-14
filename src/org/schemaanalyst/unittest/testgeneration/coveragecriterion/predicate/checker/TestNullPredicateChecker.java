package org.schemaanalyst.unittest.testgeneration.coveragecriterion.predicate.checker;

import org.junit.Test;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.NullPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.NullPredicateChecker;
import org.schemaanalyst.unittest.testutil.mock.OneColumnMockDatabase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by phil on 13/10/2014.
 */
public class TestNullPredicateChecker {

    @Test
    public void testOneSatisfySuccess() {
        List<Integer> values = new ArrayList<>();
        values.add(null);
        testNullPredicateChecker(values, true, true);
    }

    @Test
    public void testOneSatisfyFail() {
        List<Integer> values = new ArrayList<>();
        values.add(1);
        testNullPredicateChecker(values, true, false);
    }

    @Test
    public void testMultipleSatisfySuccess() {
        List<Integer> values = new ArrayList<>();
        values.add(null);
        values.add(null);
        testNullPredicateChecker(values, true, true);
    }

    @Test
    public void testMultipleSatisfyFail() {
        List<Integer> values = new ArrayList<>();
        values.add(null);
        values.add(1);
        testNullPredicateChecker(values, true, false);
    }

    @Test
    public void testOneNotSatisfySuccess() {
        List<Integer> values = new ArrayList<>();
        values.add(1);
        testNullPredicateChecker(values, false, true);
    }

    @Test
    public void testOneNotSatisfyFail() {
        List<Integer> values = new ArrayList<>();
        values.add(null);
        testNullPredicateChecker(values, false, false);
    }

    @Test
    public void testMultipleNotSatisfySuccess() {
        List<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(1);
        testNullPredicateChecker(values, false, true);
    }

    @Test
    public void testMultipleNotSatisfyFail() {
        List<Integer> values = new ArrayList<>();
        values.add(null);
        values.add(1);
        testNullPredicateChecker(values, false, false);
    }


    public void testNullPredicateChecker(List<Integer> values, boolean satisfy, boolean result) {
        OneColumnMockDatabase database = new OneColumnMockDatabase();
        Data data = database.createData(values.size());
        database.setDataValues(values);

        NullPredicate nullPredicate = new NullPredicate(
                database.table,
                database.column,
                satisfy
        );

        NullPredicateChecker nullPredicateChecker = new NullPredicateChecker(nullPredicate, data);
        assertEquals(nullPredicateChecker.check(), result);

    }
}
