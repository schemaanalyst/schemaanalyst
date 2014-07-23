package org.schemaanalyst.testgeneration.criterion;

import org.schemaanalyst.testgeneration.criterion.predicate.Predicate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 23/07/2014.
 */
public class TestRequirements {

    private List<TestRequirement> testRequirements;

    public TestRequirements() {
        this.testRequirements = new ArrayList<>();
    }

    public List<TestRequirement> getTestRequirements() {
        return new ArrayList<>(testRequirements);
    }

    public void addTestRequirement(String id, String msg, Predicate predicate) {
        addTestRequirement(new TestRequirement(id, msg, predicate));
    }

    public void addTestRequirement(TestRequirement testRequirement) {
        testRequirements.add(testRequirement);
    }

    public int size() {
        return testRequirements.size();
    }
}
