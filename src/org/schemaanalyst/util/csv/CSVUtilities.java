/*
 */
package org.schemaanalyst.util.csv;

import java.util.Collection;

/**
 *
 * @author Chris J. Wright
 */
public class CSVUtilities {
    
    /**
     * Converts any collection that can be iterated into a CSV format, with a
     * given separator.
     *
     * @param values The collection to iterate over
     * @param seperator The separator to use between values
     * @return The CSV format string
     */
    public static String convertIterableToCSV(Iterable<String> values, String seperator) {
        StringBuilder output = new StringBuilder();
        boolean first = true;

        for (String value : values) {
            if (first) {
                first = false;
            } else {
                output.append(seperator);
            }
            output.append(value);
        }

        return output.toString();
    }
    
}
