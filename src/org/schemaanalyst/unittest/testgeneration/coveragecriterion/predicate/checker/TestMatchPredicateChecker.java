package org.schemaanalyst.unittest.testgeneration.coveragecriterion.predicate.checker;

import org.junit.Test;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.MatchPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.MatchPredicateChecker;
import org.schemaanalyst.unittest.testutil.mock.OneColumnMockDatabase;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by phil on 13/10/2014.
 */
public class TestMatchPredicateChecker {

    @Test
    public void testOneColMatchAndAreSameAndMode() {
        testOneColSame(10, 10, MatchPredicate.Mode.AND, true);
    }

    @Test
    public void testOneColMatchAndAreSameOrMode() {
        testOneColSame(10, 10, MatchPredicate.Mode.OR, true);
    }

    @Test
    public void testOneColMatchAndAreDifferentAndMode() {
        testOneColSame(10, 20, MatchPredicate.Mode.AND, false);
    }

    @Test
    public void testOneColMatchAndAreDifferentOrMode() {
        testOneColSame(10, 20, MatchPredicate.Mode.OR, false);
    }

    private void testOneColSame(int row1Value, int row2Value, MatchPredicate.Mode mode, boolean result) {

        OneColumnMockDatabase database = new OneColumnMockDatabase();
        Data data = database.createData(2);
        database.setDataValues(row1Value, row2Value);

        MatchPredicate matchPredicate = new MatchPredicate(
                database.table,
                Arrays.asList(database.column), // equals columns
                MatchPredicate.EMPTY_COLUMN_LIST,  // non-equals columns
                mode
        );

        MatchPredicateChecker checker = new MatchPredicateChecker(matchPredicate, true, data, new Data());

        assertEquals(result, checker.check());

        if (result) {
            assertEquals(0, checker.getMatchingCells().size());
            assertEquals(0, checker.getNonMatchingCells().size());
        } else {
            assertEquals(0, checker.getMatchingCells().size());
            assertEquals(1, checker.getNonMatchingCells().size());
        }
    }

    @Test
    public void testOneColNonMatchAndAreDifferentAndMode() {
        testOneColNonMatching(10, 20, MatchPredicate.Mode.AND, true);
    }

    @Test
    public void testOneColNonMatchAndAreSameAndMode() {
        testOneColNonMatching(10, 10, MatchPredicate.Mode.AND, false);
    }

    @Test
    public void testOneColNonMatchAndAreDifferentOrMode() {
        testOneColNonMatching(10, 20, MatchPredicate.Mode.OR, true);
    }

    @Test
    public void testOneColNonMatchAndAreSameOrMode() {
        testOneColNonMatching(10, 10, MatchPredicate.Mode.OR, false);
    }

    private void testOneColNonMatching(int row1Value, int row2Value, MatchPredicate.Mode mode, boolean result) {

        OneColumnMockDatabase database = new OneColumnMockDatabase();

        Data data = database.createData(2);
        database.setDataValues(row1Value, row2Value);

        MatchPredicate matchPredicate = new MatchPredicate(
                database.table,
                MatchPredicate.EMPTY_COLUMN_LIST,
                Arrays.asList(database.column),
                mode
        );

        MatchPredicateChecker checker = new MatchPredicateChecker(matchPredicate, true, data, new Data());

        assertEquals(result, checker.check());

        if (result) {
            assertEquals(0, checker.getMatchingCells().size());
            assertEquals(0, checker.getNonMatchingCells().size());
        } else {
            assertEquals(1, checker.getMatchingCells().size());
            assertEquals(0, checker.getNonMatchingCells().size());
        }
    }
}
