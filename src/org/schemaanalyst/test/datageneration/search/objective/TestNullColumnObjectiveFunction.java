package org.schemaanalyst.test.datageneration.search.objective;

import org.junit.Test;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.constraint.NullColumnObjectiveFunction;
import org.schemaanalyst.test.testutil.mock.OneColumnMockDatabase;

import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertEquivalent;
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertOptimal;

public class TestNullColumnObjectiveFunction {

    OneColumnMockDatabase database;
    Data data;

    public TestNullColumnObjectiveFunction() {
        database = new OneColumnMockDatabase();
        data = database.createData(2);
    }

    NullColumnObjectiveFunction createObjFun(boolean satisfy) {
        return new NullColumnObjectiveFunction(database.column, "", satisfy);
    }

    @Test
    public void satisfy_AllNull() {
        NullColumnObjectiveFunction objFun = createObjFun(true);
        database.setDataValues(null, null);
        assertOptimal(objFun.evaluate(data));
    }

    @Test
    public void satisfy_OneNull() {
        NullColumnObjectiveFunction objFun = createObjFun(true);
        database.setDataValues(null, 1);

        ObjectiveValue actualObjVal = objFun.evaluate(data);
        SumOfMultiObjectiveValue expectedObjVal = new SumOfMultiObjectiveValue("Top level obj value");
        expectedObjVal.add(ObjectiveValue.worstObjectiveValue("One null value"));
        assertEquivalent(actualObjVal, expectedObjVal);
    }

    @Test
    public void negate_AllNotNull() {
        NullColumnObjectiveFunction objFun = createObjFun(false);
        database.setDataValues(1, 1);
        assertOptimal(objFun.evaluate(data));
    }

    @Test
    public void negate_OneNull() {
        NullColumnObjectiveFunction objFun = createObjFun(false);
        database.setDataValues(null, 1);

        ObjectiveValue actualObjVal = objFun.evaluate(data);
        SumOfMultiObjectiveValue expectedObjVal = new SumOfMultiObjectiveValue("Top level obj value");
        expectedObjVal.add(ObjectiveValue.worstObjectiveValue("One null value"));
        assertEquivalent(actualObjVal, expectedObjVal);
    }
}
