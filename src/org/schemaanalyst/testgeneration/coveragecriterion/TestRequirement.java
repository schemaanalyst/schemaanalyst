package org.schemaanalyst.testgeneration.coveragecriterion;

import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 23/07/2014.
 */
public class TestRequirement {

    private List<TestRequirementDescriptor> descriptors;
    private Predicate predicate;

    public TestRequirement(TestRequirementDescriptor descriptor, Predicate predicate) {
        descriptors = new ArrayList<>();
        descriptors.add(descriptor);
        this.predicate = predicate;
    }

    public TestRequirement(String id, String description, Predicate predicate) {
        this(new TestRequirementDescriptor(id, description), predicate);
    }

    public List<TestRequirementDescriptor> getDescriptors() {
        return new ArrayList<>(descriptors);
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public String toString() {
        String str = "";
        for (TestRequirementDescriptor trd : descriptors) {
            str += trd + "\n";
        }
        str += predicate;
        return str;
    }
}
