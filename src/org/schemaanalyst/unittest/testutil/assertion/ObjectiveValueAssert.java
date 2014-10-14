package org.schemaanalyst.unittest.testutil.assertion;

import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;

import static org.junit.Assert.assertTrue;

public class ObjectiveValueAssert {

    public static void assertOptimal(ObjectiveValue actual) {
        assertTrue("Objective value should be optimal, but was:\n" + actual + "\n", actual.isOptimal());
    }

    public static void assertNonOptimal(ObjectiveValue actual) {
        assertTrue("Objective value should not be optimal, but was:\n" + actual + "\n", !actual.isOptimal());
    }
    
    public static void assertWorst(ObjectiveValue actual) {
        assertTrue("Objective value should be worst, but was:\n" + actual + "\n", actual.isWorst());
    }
    
    public static void assertEquivalent(ObjectiveValue expected, ObjectiveValue actual) {
        assertTrue("Objective values should be equivalent:\n" + expected + "\nwas\n" + actual + "\n",
                   actual.getValue().compareTo(expected.getValue()) == 0);
    }
}
