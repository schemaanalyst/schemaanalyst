package org.schemaanalyst.test.datageneration.search.objective;

import org.junit.Test;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.constraint.checkcondition.InCheckConditionObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.relationalpredicate.ValueObjectiveFunction;
import org.schemaanalyst.deprecated.sqlrepresentation.checkcondition.InCheckCondition;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.test.mock.OneColumnMockDatabase;

import static org.schemaanalyst.logic.RelationalOperator.EQUALS;
import static org.schemaanalyst.logic.RelationalOperator.NOT_EQUALS;
import static org.schemaanalyst.test.junit.ObjectiveValueAssert.assertEquivalent;

public class TestInCheckPredicateObjectiveFunction {

    OneColumnMockDatabase database;
    Data data;
    boolean satisfy;
    InCheckConditionObjectiveFunction objFun;
    InCheckCondition inPredicate;

    public TestInCheckPredicateObjectiveFunction() {
        // set up database		
        database = new OneColumnMockDatabase();
        data = database.createData(2);
    }

    void setup(boolean satisfy, int... values) {
        this.satisfy = satisfy;
        this.inPredicate = new InCheckCondition(database.column, values);
        this.objFun = new InCheckConditionObjectiveFunction(inPredicate, database.table, null, "", satisfy, false);
    }

    void test(Integer... values) {
        database.setDataValues(values);
        MultiObjectiveValue expected = new SumOfMultiObjectiveValue("Top level");

        for (Integer value : values) {
            MultiObjectiveValue rowObjVal;
            RelationalOperator op;

            if (satisfy) {
                rowObjVal = new BestOfMultiObjectiveValue("Row value compared to In values");
                op = EQUALS;
            } else {
                rowObjVal = new SumOfMultiObjectiveValue("Row value compared to In values");
                op = NOT_EQUALS;
            }

            NumericValue numericalValue = new NumericValue();
            if (value != null) {
                numericalValue.set(value);
            } else {
                numericalValue = null;
            }

            for (Value inValue : inPredicate.getValues()) {
                rowObjVal.add(ValueObjectiveFunction.compute(numericalValue, op, inValue));
            }

            expected.add(rowObjVal);
        }

        ObjectiveValue actual = objFun.evaluate(data);
        assertEquivalent(expected, actual);
    }

    @Test
    public void satisfy_AllInSet() {
        setup(true, 1, 2, 3);
        test(1, 2);
    }

    @Test
    public void satisfy_NoneInSet() {
        setup(true, 1, 2, 3);
        test(4, 5);
    }

    @Test
    public void satisfy_OneInSet() {
        setup(true, 1, 2, 3);
        test(3, 4);
    }

    @Test
    public void satisfy_OneNull() {
        setup(true, 1, 2, 3);
        test(2, null);
    }

    @Test
    public void satisfy_TwoNulls() {
        setup(true, 1, 2, 3);
        test(null, null);
    }

    @Test
    public void negate_AllInSet() {
        setup(false, 1, 2, 3);
        test(1, 2);
    }

    @Test
    public void negate_NoneInSet() {
        setup(false, 1, 2, 3);
        test(-1, -1);
    }

    @Test
    public void negate_OneInSet() {
        setup(false, 1, 2, 3);
        test(-1, 2);
    }

    @Test
    public void negate_OneNull() {
        setup(false, 1, 2, 3);
        test(null, -1);
    }

    @Test
    public void negate_TwoNulls() {
        setup(false, 1, 2, 3);
        test(null, null);
    }
}
