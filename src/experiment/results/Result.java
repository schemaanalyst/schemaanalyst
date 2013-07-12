/*
 */
package experiment.results;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores the results of an experiment, in a way independent of the target
 * output format.
 *
 * @author chris
 */
public class Result {

    private HashMap<String, String> results;

    /**
     * Constructs an empty result object.
     */
    public Result() {
        results = new HashMap<>();
    }

    /**
     * Stores a new value in this Result
     *
     * @param attribute The attribute the value corresponds to
     * @param value The value to store
     */
    public void addValue(String attribute, String value) {
        results.put(attribute, value);
    }

    /**
     * Provides access to the values stored in this Result. Note, the collection
     * returned is not a copy, therefore changes to this will also occur in the
     * Result object itself.
     *
     * @return The values stored
     */
    public Map<String, String> getValues() {
        return results;
    }

    /**
     * Replace the map of values stored in this Result.
     *
     * @param results The values to store
     */
    public void setValues(HashMap<String, String> results) {
        this.results = results;
    }
}
