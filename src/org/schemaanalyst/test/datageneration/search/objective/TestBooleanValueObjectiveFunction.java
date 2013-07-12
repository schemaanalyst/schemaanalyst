package org.schemaanalyst.test.datageneration.search.objective;

import org.junit.Test;
import org.schemaanalyst.data.BooleanValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.relationalpredicate.BooleanValueObjectiveFunction;

import static org.schemaanalyst.logic.RelationalOperator.EQUALS;
import static org.schemaanalyst.test.junit.ObjectiveValueAssert.assertEquivalent;
import static org.schemaanalyst.test.junit.ObjectiveValueAssert.assertOptimal;

public class TestBooleanValueObjectiveFunction {

    protected final static BooleanValue TRUE = new BooleanValue(true);
    protected final static BooleanValue FALSE = new BooleanValue(false);

    @Test
    public void equals_TrueEqualsTrue() {
        assertOptimal(
                BooleanValueObjectiveFunction.compute(TRUE, EQUALS, TRUE));
    }

    @Test
    public void equals_TrueEqualsFalse() {
        assertEquivalent(ObjectiveValue.worstObjectiveValue("Worst"),
                BooleanValueObjectiveFunction.compute(TRUE, EQUALS, FALSE));
    }

    @Test
    public void equals_FalseEqualsTrue() {
        assertEquivalent(ObjectiveValue.worstObjectiveValue("Worst"),
                BooleanValueObjectiveFunction.compute(FALSE, EQUALS, TRUE));
    }

    @Test
    public void equals_FalseEqualsFalse() {
        assertOptimal(
                BooleanValueObjectiveFunction.compute(FALSE, EQUALS, FALSE));
    }
}
