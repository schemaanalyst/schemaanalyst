/*
 */
package org.schemaanalyst.mutation.reports.records;

import java.util.EnumMap;

/**
 * Stores the summary information about the results of analysis for a given
 * mutation type.
 *
 * @author chris
 */
public class MutationTestRecord {

    private EnumMap<MutationTestAttribute, Integer> values;

    /**
     * Default constructor that initialises all values to 0.
     */
    public MutationTestRecord() {
        values = new EnumMap<>(MutationTestAttribute.class);
        for (MutationTestAttribute attribute : MutationTestAttribute.values()) {
            values.put(attribute, 0);
        }
    }

    /**
     * Increments the value for a given attribute.
     *
     * @param attribute The attribute to increment
     */
    public void increment(MutationTestAttribute attribute) {
        values.put(attribute, values.get(attribute) + 1);
    }

    /**
     * Increments the value for the attribute with the given name.
     *
     * @param attributeName The name of the attribute to increment
     */
    public void increment(String attributeName) {
        MutationTestAttribute attribute = MutationTestAttribute.getByName(attributeName);
        increment(attribute);
    }

    /**
     * Returns the value accumulated for a given attribute.
     *
     * @param attribute The attribute
     * @return The accumulated value
     */
    public int countOf(MutationTestAttribute attribute) {
        return values.get(attribute);
    }

    /**
     * Returns the value for the attribute with the given name.
     *
     * @param attributeName The name of the attribute to get the value for
     * @return The accumulated value
     */
    public int countOf(String attributeName) {
        MutationTestAttribute attribute = MutationTestAttribute.getByName(attributeName);
        return countOf(attribute);
    }
}
