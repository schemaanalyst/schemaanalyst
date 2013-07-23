package experiment;

import java.util.List;
import java.util.ArrayList;

public class ExperimentalResult {

    /**
     * The list of values for this result row; connected to the header of the
     * ExperimentalResults
     */
    private ArrayList<String> results;

    /**
     * Standard constructor
     */
    public ExperimentalResult() {
        results = new ArrayList<>();
    }

    /**
     * Return the results
     */
    public List<String> getResults() {
        return results;
    }

    /**
     * Add an experimental result at a designated location
     */
    public void addResult(int location, String result) {
        results.add(location, result);
    }
}