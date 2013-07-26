package org.schemaanalyst.test.datageneration.search.objective;

import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertEquivalent;
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertOptimal;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.value.NullValueObjectiveFunction;

public class TestNullValueObjectiveFunction {

    @Test
    public void nullShouldBeNull() {
        assertOptimal(
                NullValueObjectiveFunction.compute(null, true));
    }

    @Test
    public void nullShouldntBeNull() {
        assertEquivalent(ObjectiveValue.worstObjectiveValue("Worst"),
                NullValueObjectiveFunction.compute(null, false));
    }

    @Test
    public void notNullShouldBeNull() {
        assertEquivalent(ObjectiveValue.worstObjectiveValue("Worst"),
                NullValueObjectiveFunction.compute(new NumericValue(), true));
    }

    @Test
    public void notNullShouldntBeNull() {
        assertOptimal(
                NullValueObjectiveFunction.compute(new NumericValue(), false));
    }
}
