package org.schemaanalyst.test.coverage.testgeneration.datageneration.checker;

import org.junit.Test;
import org.schemaanalyst.testgeneration.coveragecriterion.clause.MatchClause;
import org.schemaanalyst.data.generation.checker.MatchClauseChecker;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.test.testutil.mock.OneColumnMockDatabase;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;


/**
 * Created by phil on 26/02/2014.
 */
public class TestMatchClauseChecker {

    @Test
    public void testOneColMatchAndAreSameAndMode() {
        testOneColSame(10, 10, MatchClause.Mode.AND, true);
    }

    @Test
    public void testOneColMatchAndAreSameOrMode() {
        testOneColSame(10, 10, MatchClause.Mode.OR, true);
    }

    @Test
    public void testOneColMatchAndAreDifferentAndMode() {
        testOneColSame(10, 20, MatchClause.Mode.AND, false);
    }

    @Test
    public void testOneColMatchAndAreDifferentOrMode() {
        testOneColSame(10, 20, MatchClause.Mode.OR, false);
    }

    private void testOneColSame(int row1Value, int row2Value, MatchClause.Mode mode, boolean result) {

        OneColumnMockDatabase database = new OneColumnMockDatabase();
        Data data = database.createData(2);
        database.setDataValues(row1Value, row2Value);

        MatchClause matchClause = new MatchClause(
                database.table,
                Arrays.asList(database.column), // equals columns
                MatchClause.EMPTY_COLUMN_LIST,  // non-equals columns
                mode,
                false
        );

        MatchClauseChecker checker = new MatchClauseChecker(matchClause, true, data);

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
        testOneColNonMatching(10, 20, MatchClause.Mode.AND, true);
    }

    @Test
    public void testOneColNonMatchAndAreSameAndMode() {
        testOneColNonMatching(10, 10, MatchClause.Mode.AND, false);
    }

    @Test
    public void testOneColNonMatchAndAreDifferentOrMode() {
        testOneColNonMatching(10, 20, MatchClause.Mode.OR, true);
    }

    @Test
    public void testOneColNonMatchAndAreSameOrMode() {
        testOneColNonMatching(10, 10, MatchClause.Mode.OR, false);
    }

    private void testOneColNonMatching(int row1Value, int row2Value, MatchClause.Mode mode, boolean result) {

        OneColumnMockDatabase database = new OneColumnMockDatabase();

        Data data = database.createData(2);
        database.setDataValues(row1Value, row2Value);

        MatchClause matchClause = new MatchClause(
                database.table,
                MatchClause.EMPTY_COLUMN_LIST,
                Arrays.asList(database.column),
                mode,
                false
        );

        MatchClauseChecker checker = new MatchClauseChecker(matchClause, true, data);

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
