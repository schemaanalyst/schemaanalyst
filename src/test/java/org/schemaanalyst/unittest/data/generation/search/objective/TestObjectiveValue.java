package org.schemaanalyst.unittest.data.generation.search.objective;

import org.junit.Test;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestObjectiveValue {

    @Test
    public void bestOptimal() {
        ObjectiveValue objVal = new ObjectiveValue("");
        objVal.setValueToOptimal();
        assertTrue(objVal.isOptimal());
    }

    @Test
    public void bestOptimalFactoryMethod() {
        ObjectiveValue objVal = ObjectiveValue.optimalObjectiveValue("");
        assertTrue(objVal.isOptimal());
    }

    @Test
    public void zeroOptimal() {
        ObjectiveValue objVal = new ObjectiveValue("");
        objVal.setValue(0);
        assertTrue(objVal.isOptimal());
    }

    @Test
    public void worstNonOptimal() {
        ObjectiveValue objVal = new ObjectiveValue("");
        objVal.setValueToWorst();
        assertFalse(objVal.isOptimal());
    }

    @Test
    public void nonZeroNonOptimal() {
        ObjectiveValue objVal = new ObjectiveValue("");
        objVal.setValue(0.1);
        assertFalse(objVal.isOptimal());
    }

    @Test
    public void worstNonOptimalFactoryMethod() {
        ObjectiveValue objVal = ObjectiveValue.worstObjectiveValue("");
        assertFalse(objVal.isOptimal());
    }

    @Test
    public void bestBetterThanWorst() {
        ObjectiveValue objVal1 = new ObjectiveValue("Best");
        ObjectiveValue objVal2 = new ObjectiveValue("Worst");
        objVal1.setValueToOptimal();
        objVal2.setValueToWorst();
        assertTrue(objVal1.betterThan(objVal2));
        assertTrue(objVal2.worseThan(objVal1));
    }

    @Test
    public void betterThanAndWorseThan() {
        ObjectiveValue objVal1 = new ObjectiveValue("");
        objVal1.setValue(0.5);
        ObjectiveValue objVal2 = new ObjectiveValue("");
        objVal2.setValue(0.2);
        assertTrue(objVal2.betterThan(objVal1));
        assertTrue(objVal1.worseThan(objVal2));
    }

    @Test
    public void sameNotBetterOrWorse() {
        ObjectiveValue objVal1 = new ObjectiveValue("");
        objVal1.setValue(0.5);
        ObjectiveValue objVal2 = new ObjectiveValue("");
        objVal2.setValue(0.5);
        assertFalse(objVal2.betterThan(objVal1));
        assertFalse(objVal1.betterThan(objVal2));
    }
}
