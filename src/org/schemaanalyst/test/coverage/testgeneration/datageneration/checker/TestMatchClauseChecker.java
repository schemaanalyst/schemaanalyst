package org.schemaanalyst.test.coverage.testgeneration.datageneration.checker;

import org.junit.Test;
import org.schemaanalyst.coverage.criterion.clause.MatchClause;
import org.schemaanalyst.coverage.testgeneration.datageneration.checker.MatchClauseChecker;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.test.testutil.mock.OneColumnMockDatabase;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;


/**
 * Created by phil on 26/02/2014.
 */
public class TestMatchClauseChecker {

    @Test
    public void testOneColMatchAndAreSame() {
        testOneColSame(10, 10, true);
    }

    @Test
    public void testOneColMatchAndAreDifferent() {
        testOneColSame(10, 20, false);
    }

    private void testOneColSame(int row1Value, int row2Value, boolean result) {

        OneColumnMockDatabase database = new OneColumnMockDatabase();
        Data data = database.createData(2);
        database.setDataValues(row1Value, row2Value);

        MatchClause matchClause = new MatchClause(
                database.table,
                Arrays.asList(database.column),
                MatchClause.EMPTY_COLUMN_LIST,
                MatchClause.Mode.AND,
                true
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
    public void testOneColNonMatchAndAreDifferent() {
        testOneColNonMatching(10, 20, true);
    }

    @Test
    public void testOneColNonMatchAndAreSame() {
        testOneColNonMatching(10, 10, false);
    }

    private void testOneColNonMatching(int row1Value, int row2Value, boolean result) {

        OneColumnMockDatabase database = new OneColumnMockDatabase();

        Data data = database.createData(2);
        database.setDataValues(row1Value, row2Value);

        MatchClause matchClause = new MatchClause(
                database.table,
                MatchClause.EMPTY_COLUMN_LIST,
                Arrays.asList(database.column),
                MatchClause.Mode.AND,
                true
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
