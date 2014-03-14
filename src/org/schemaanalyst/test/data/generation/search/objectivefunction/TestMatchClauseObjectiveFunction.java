package org.schemaanalyst.test.data.generation.search.objectivefunction;

import junitparams.JUnitParamsRunner;
import org.junit.runner.RunWith;

/**
 * Created by phil on 24/01/2014.
 */

@RunWith(JUnitParamsRunner.class)
public class TestMatchClauseObjectiveFunction {
/*
    Object[] testValues() {
        return $(
                $(r(1, 1), d(r(1, 1)), true, true, MatchClause.Mode.AND, true),
                $(r(1, 2), d(r(3, 4)), true, true, MatchClause.Mode.AND, false),

                $(r(1, 1), d(r(1, 1)), false, false, MatchClause.Mode.AND, false),
                $(r(1, 1), d(r(2, 2)), false, false, MatchClause.Mode.AND, true),

                $(r(1, 1), d(r(1, 1)), false, true, MatchClause.Mode.AND, false),
                $(r(1, 2), d(r(2, 2)), false, true, MatchClause.Mode.AND, true),

                $(r(1, 1), d(r(1, 1)), false, false, MatchClause.Mode.AND, false),
                $(r(1, 2), d(r(3, 4)), false, false, MatchClause.Mode.AND, true),

                $(r(NULL, 1), d(r(1, 1)), false, true, MatchClause.Mode.AND, true),
                $(r(NULL, NULL), d(r(1, 1)), false, true, MatchClause.Mode.AND, true)
        );
    }

    @Test
    @Parameters(method = "testValues")
    public void test(Integer[] rowValues, Integer[] stateValues,
                     boolean firstEqual, boolean secondEqual, MatchClause.Mode mode,
                     boolean success) {
        TwoColumnMockDatabase database = new TwoColumnMockDatabase();

        Data data = database.createData(1);
        database.setDataValues(d(rowValues));
        Row row = new Row(data.getCells());

        Data state = database.createState(stateValues.length / 2);
        database.setStateValues(stateValues);

        List<Column> equalCols = new ArrayList<>();
        List<Column> notEqualCols = new ArrayList<>();

        if (firstEqual) {
            equalCols.add(database.column1);
        } else {
            notEqualCols.add(database.column1);
        }

        if (secondEqual) {
            equalCols.add(database.column2);
        } else {
            notEqualCols.add(database.column2);
        }

        MatchClause mf = new MatchClause(database.table, equalCols, notEqualCols, mode, false);

        MatchClauseObjectiveFunction mof = new MatchClauseObjectiveFunction(mf, state);
        ObjectiveValue objVal = mof.evaluate(row);

        if (success) {
            assertOptimal(objVal);
        } else {
            assertNonOptimal(objVal);
        }
    }
*/
}
