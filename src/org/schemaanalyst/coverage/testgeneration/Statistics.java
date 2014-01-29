package org.schemaanalyst.coverage.testgeneration;

import java.util.HashMap;

/**
 * Created by phil on 24/01/2014.
 */
public class Statistics {

    private HashMap<String, String> stats;

    public String getStatistic(String name) {
        return stats.get(name);
    }

    public void addStatistic(String name, String value) {
        stats.put(name, value);
    }

    public void addStatistic(String name, int value) {
        stats.put(name, "" + value);
    }

    public void addStatistic(String name, double value) {
        stats.put(name, "" + value);
    }

    public void addStatistic(String name, boolean value) {
        stats.put(name, value ? "true" : "false");
    }

    public HashMap<String, String> getStatistics() {
        return new HashMap<>(stats);
    }
}
