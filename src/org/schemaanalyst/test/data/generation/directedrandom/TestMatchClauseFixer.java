package org.schemaanalyst.test.data.generation.directedrandom;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.directedrandom.MatchClauseFixer;
import org.schemaanalyst.logic.predicate.checker.MatchClauseChecker;
import org.schemaanalyst.logic.predicate.clause.MatchClause;
import org.schemaanalyst.test.testutil.mock.MockCellValueGenerator;
import org.schemaanalyst.test.testutil.mock.MockRandom;
import org.schemaanalyst.test.testutil.mock.OneColumnMockDatabase;
import org.schemaanalyst.util.random.Random;

import java.util.Arrays;
import java.util.List;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertEquals;
import static org.schemaanalyst.test.testutil.Params.*;

/**
 * Created by phil on 01/03/2014.
 */
@RunWith(JUnitParamsRunner.class)
public class TestMatchClauseFixer {

    OneColumnMockDatabase database = new OneColumnMockDatabase();

    MatchClause oneColumnEqualsClause = new MatchClause(database.table, Arrays.asList(database.column), MatchClause.EMPTY_COLUMN_LIST, MatchClause.Mode.AND, true);

    MatchClause oneColumnNotEqualsClause = new MatchClause(database.table, MatchClause.EMPTY_COLUMN_LIST, Arrays.asList(database.column), MatchClause.Mode.AND, true);

    Object[] oneColumnTestValues() {
        return $(
                  $("Nothing to fix", oneColumnEqualsClause, d(r(1)), d(r(1)), EMPTY_i, EMPTY_i, i(1))
                , $("Change data value to match state", oneColumnEqualsClause, d(r(1)), d(r(2)), EMPTY_i, i(0), i(2))
                , $("Change first data value to match second data value", oneColumnEqualsClause, d(r(1, 2)), EMPTY_I, EMPTY_i, i(0, 0), i(2, 2))
                , $("Change second data value to match first data value", oneColumnEqualsClause, d(r(1, 2)), EMPTY_I, EMPTY_i, i(0, 1), i(1, 1))

                , $("Nothing to fix", oneColumnNotEqualsClause, d(r(1)), d(r(2)), EMPTY_i, EMPTY_i, i(1))
                , $("Change data value to not match state", oneColumnNotEqualsClause, d(r(1)), d(r(1)), i(3), i(0, 0), i(3))
                , $("Change first data value to not match second data value", oneColumnNotEqualsClause, d(r(1, 1)), EMPTY_I, i(3), i(0, 0), i(3, 1))
                , $("Change second data value to not match first data value", oneColumnNotEqualsClause, d(r(1, 1)), EMPTY_I, i(3), i(0, 1), i(1, 3))
        );
    }

    @Test
    @Parameters(method = "oneColumnTestValues")
    public void oneColumnTests(
            String message,
            MatchClause matchClause,
            Integer[] dataValues,
            Integer[] stateValues,
            int[] cellGeneratorValues,
            int[] randomValues,
            int[] changedDataValues) {

        Data data = database.createData(dataValues.length);
        Data state = database.createState(stateValues.length);

        database.setDataValues(dataValues);
        database.setStateValues(stateValues);

        RandomCellValueGenerator cellValueGenerator = new MockCellValueGenerator(cellGeneratorValues);
        Random random = new MockRandom(randomValues);

        MatchClauseChecker matchClauseChecker = new MatchClauseChecker(matchClause, false, data, state);
        matchClauseChecker.check();

        MatchClauseFixer matchClauseFixer = new MatchClauseFixer(matchClauseChecker, random, cellValueGenerator);
        matchClauseFixer.attemptFix();

        List<Cell> cells = data.getCells();
        for (int i = 0; i < cells.size(); i++) {
            assertEquals(message, ((NumericValue) cells.get(i).getValue()).get().intValue(), changedDataValues[i]);
        }
    }
}

