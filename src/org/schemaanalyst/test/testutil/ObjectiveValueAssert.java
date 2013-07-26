package org.schemaanalyst.test.testutil;

import static org.junit.Assert.assertTrue;

import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;

public class ObjectiveValueAssert {

    public static void assertOptimal(ObjectiveValue actual) {
        assertTrue("Objective value should be optimal, but was:\n" + actual + "\n", actual.isOptimal());
    }

    public static void assertNonOptimal(ObjectiveValue actual) {
        assertTrue("Objective value should not be optimal, but was:\n" + actual + "\n", !actual.isOptimal());
    }

    public static void assertEquivalent(ObjectiveValue expected, ObjectiveValue actual) {
        assertTrue("Objective values should be equivalent:\n" + expected + "\nwas\n" + actual + "\n",
                actual.getValue().compareTo(expected.getValue()) == 0);
    }
}
