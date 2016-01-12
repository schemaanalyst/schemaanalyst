/*
 */
package org.schemaanalyst.util.csv;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A single row to be stored for output in a CSV format.
 * 
 * @author Chris J. Wright
 */
public class CSVResult {
    private LinkedHashMap<String, Object> results;

    /**
     * Default constructor.
     */
    public CSVResult() {
        results = new LinkedHashMap<>();
    }
    
    /**
     * Add a value to the result.
     * 
     * @param attribute The header name for the value.
     * @param value The value.
     * @return The CSVResult for chaining.
     */
    public CSVResult addValue(String attribute, Object value) {
        results.put(attribute, value);
        return this;
    }
    
    /**
     * Get the values in this result.
     * 
     * @return The values.
     */
    public Map<String, Object> getValues() {
        return Collections.unmodifiableMap(results);
    }
    
    @Override
    /**
     * {@inheritDoc}
     */
    public String toString() {
        return StringUtils.join(results.values(), ",");
    }
}
