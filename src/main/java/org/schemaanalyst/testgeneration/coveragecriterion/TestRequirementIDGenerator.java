package org.schemaanalyst.testgeneration.coveragecriterion;

import java.util.HashMap;

/**
 * Created by phil on 18/07/2014.
 */
public class TestRequirementIDGenerator {

    public enum IDType {
        SCHEMA, TABLE;
    }

    private IDType type;
    private String id;
    private HashMap<String, Integer> counters;

    public TestRequirementIDGenerator(IDType type) {
        this.type = type;
        counters = new HashMap<>();
    }

    public void reset(IDType type, String id) {
        if (type.equals(this.type)) {
            this.id = id;

            if (!counters.containsKey(id)) {
                counters.put(id, 0);
            }
        }
    }

    public TestRequirementID nextID() {
        int counter = counters.get(id);
        counter ++;
        counters.put(id, counter);
        return new TestRequirementID(counter, id);
    }
}
