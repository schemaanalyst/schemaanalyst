/*
 */
package org.schemaanalyst.mutation.reports.records;

import java.util.HashMap;

/**
 *
 * Stores the summary information about the results of analysis for multiple
 * mutation types.
 *
 * @author chris
 */
public class MutationTestSummary {

    private HashMap<String, MutationTestRecord> summaryMap;

    /**
     * Default constructor.
     */
    public MutationTestSummary() {
        summaryMap = new HashMap<>();
    }

    //TODO: Add method to process a MutantReport
    /**
     * Increments the attribute value of the appropriate MutationTestRecord.
     *
     * @param mutationType The mutation type
     * @param attribute The attribute to increment
     */
    private void increment(String mutationType, MutationTestAttribute attribute) {
        MutationTestRecord record = fetch(mutationType);
        record.increment(attribute);
    }

    /**
     * Retrieves the cumulative count of an attribute for the given mutation
     * type.
     *
     * @param mutationType The mutation type
     * @param attribute The attribute to increment
     * @return The cumulative count
     */
    private int countOf(String mutationType, MutationTestAttribute attribute) {
        MutationTestRecord record = fetch(mutationType);
        return record.countOf(attribute);
    }

    /**
     * Returns a value from the backing collection, or adds an item if one does
     * not already exist.
     *
     * @param mutationType The mutation type
     * @return The mapped record
     */
    private MutationTestRecord fetch(String mutationType) {
        if (!summaryMap.containsKey(mutationType)) {
            summaryMap.put(mutationType, new MutationTestRecord());
        }
        return summaryMap.get(mutationType);
    }
}
