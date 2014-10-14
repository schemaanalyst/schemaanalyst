package org.schemaanalyst.unittest.data.generation.search.objective;

import org.junit.Test;
import org.schemaanalyst.data.generation.search.objective.DistanceObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;

import java.math.BigDecimal;

import static org.junit.Assert.assertNull;
import static org.schemaanalyst.unittest.testutil.assertion.BigDecimalAssert.assertEquals;

public class TestDistanceObjectiveValue {

    @Test
    public void testSetValueUsingDistance() {

        ObjectiveValue objVal = new ObjectiveValue("vanilla objective value");
        objVal.normalizeAndSetValue(BigDecimal.TEN);

        DistanceObjectiveValue distObjVal = new DistanceObjectiveValue("distance objective value");
        distObjVal.setValueUsingDistance(10);

        assertEquals("Objective values should be the same", distObjVal.getValue(), objVal.getValue());
        assertEquals("Distance should be 10", distObjVal.getDistance(), BigDecimal.TEN);

        distObjVal.setValueUsingDistance(10.0);
        assertEquals("Objective values should be the same", distObjVal.getValue(), objVal.getValue());

        distObjVal.setValue(1);
        assertNull("Distance should be null", distObjVal.getDistance());
        assertEquals("Objective value should be 1", distObjVal.getValue(), BigDecimal.ONE);
    }
}
