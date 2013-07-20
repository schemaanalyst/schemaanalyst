package org.schemaanalyst.test.testutil;

import static org.junit.Assert.assertTrue;

import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;

public class ObjectiveValueAssert {

    public static void assertOptimal(ObjectiveValue actual) {
        assertTrue("Expected optimal, was:\n" + actual + "\n", actual.isOptimal());
    }

    public static void assertEquivalent(ObjectiveValue expected, ObjectiveValue actual) {
        assertTrue("Expected:\n" + expected + "\nwas\n" + actual + "\n",
                actual.getValue().compareTo(expected.getValue()) == 0);
    }
}
