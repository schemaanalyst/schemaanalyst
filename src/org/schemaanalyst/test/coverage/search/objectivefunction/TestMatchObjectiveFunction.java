package org.schemaanalyst.test.coverage.search.objectivefunction;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.coverage.criterion.clause.MatchClause;
import org.schemaanalyst.coverage.search.objectivefunction.MatchObjectiveFunction;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.test.testutil.mock.TwoColumnMockDatabase;

import java.util.ArrayList;
import java.util.List;

import static junitparams.JUnitParamsRunner.$;
import static org.schemaanalyst.test.testutil.Params.*;
import static org.schemaanalyst.test.testutil.assertion.ObjectiveValueAssert.assertNonOptimal;
import static org.schemaanalyst.test.testutil.assertion.ObjectiveValueAssert.assertOptimal;

/**
 * Created by phil on 24/01/2014.
 */

@RunWith(JUnitParamsRunner.class)
public class TestMatchObjectiveFunction {

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

        MatchObjectiveFunction mof = new MatchObjectiveFunction(mf, state);
        ObjectiveValue objVal = mof.evaluate(row);

        if (success) {
            assertOptimal(objVal);
        } else {
            assertNonOptimal(objVal);
        }
    }

}
