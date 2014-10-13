package org.schemaanalyst.unittest.data.generation.directedrandom;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.directedrandom.MatchPredicateFixer;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.MatchPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.MatchPredicateChecker;
import org.schemaanalyst.unittest.testutil.mock.MockCellValueGenerator;
import org.schemaanalyst.unittest.testutil.mock.MockRandom;
import org.schemaanalyst.unittest.testutil.mock.OneColumnMockDatabase;
import org.schemaanalyst.util.random.Random;

import java.util.Arrays;
import java.util.List;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertEquals;
import static org.schemaanalyst.unittest.testutil.Params.*;

/**
 * Created by phil on 01/03/2014.
 */
@RunWith(JUnitParamsRunner.class)
public class TestMatchPredicateFixer {

    OneColumnMockDatabase database = new OneColumnMockDatabase();

    MatchPredicate oneColumnEqualsPredicate = new MatchPredicate(database.table, Arrays.asList(database.column), MatchPredicate.EMPTY_COLUMN_LIST, MatchPredicate.Mode.AND);

    MatchPredicate oneColumnNotEqualsPredicate = new MatchPredicate(database.table, MatchPredicate.EMPTY_COLUMN_LIST, Arrays.asList(database.column), MatchPredicate.Mode.AND);

    Object[] oneColumnTestValues() {
        return $(
                $("Nothing to fix", oneColumnEqualsPredicate, d(r(1)), d(r(1)), EMPTY_i, EMPTY_i, i(1))
                , $("Change data value to match state", oneColumnEqualsPredicate, d(r(1)), d(r(2)), EMPTY_i, i(0), i(2))
                , $("Change first data value to match second data value", oneColumnEqualsPredicate, d(r(1, 2)), EMPTY_I, EMPTY_i, i(0, 0), i(2, 2))
                , $("Change second data value to match first data value", oneColumnEqualsPredicate, d(r(1, 2)), EMPTY_I, EMPTY_i, i(0, 1), i(1, 1))

                , $("Nothing to fix", oneColumnNotEqualsPredicate, d(r(1)), d(r(2)), EMPTY_i, EMPTY_i, i(1))
                , $("Change data value to not match state", oneColumnNotEqualsPredicate, d(r(1)), d(r(1)), i(3), i(0, 0), i(3))
                , $("Change first data value to not match second data value", oneColumnNotEqualsPredicate, d(r(1, 1)), EMPTY_I, i(3), i(0, 0), i(3, 1))
                , $("Change second data value to not match first data value", oneColumnNotEqualsPredicate, d(r(1, 1)), EMPTY_I, i(3), i(0, 1), i(1, 3))
        );
    }

    @Test
    @Parameters(method = "oneColumnTestValues")
    public void oneColumnTests(
            String message,
            MatchPredicate matchPredicate,
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

        MatchPredicateChecker matchPredicateChecker = new MatchPredicateChecker(matchPredicate, false, data, state);
        matchPredicateChecker.check();

        MatchPredicateFixer matchPredicateFixer = new MatchPredicateFixer(matchPredicateChecker, random, cellValueGenerator);
        matchPredicateFixer.attemptFix();

        List<Cell> cells = data.getCells();
        for (int i = 0; i < cells.size(); i++) {
            assertEquals(message, ((NumericValue) cells.get(i).getValue()).get().intValue(), changedDataValues[i]);
        }
    }
}

