package org.schemaanalyst.testgeneration.criterion;

import org.schemaanalyst.testgeneration.criterion.predicate.Predicate;

import java.util.*;

/**
 * Created by phil on 18/07/2014.
 */
public abstract class CoverageCriterion {

    protected LinkedHashMap<Predicate, List<TestRequirementDescriptor>> requirements;

    public CoverageCriterion() {
        requirements = new LinkedHashMap<>();
    }

    public abstract void generateRequirements();

    protected void addRequirement(String id, String description, Predicate predicate) {
        List<TestRequirementDescriptor> descriptors = requirements.get(predicate);
        if (descriptors == null) {
            descriptors = new ArrayList<>();
        }
        descriptors.add(new TestRequirementDescriptor(id, description));
        requirements.put(predicate, descriptors);
    }

    public List<Predicate> getTestRequirements() {
        return new ArrayList<>(requirements.keySet());
    }

    public List<TestRequirementDescriptor> getDescriptors(Predicate predicate) {
        return requirements.get(predicate);
    }

    public int getNumRequirements() {
        return requirements.size();
    }

    public void printRequirements() {
        for (Predicate predicate : getTestRequirements()) {
            for (TestRequirementDescriptor descriptor : getDescriptors(predicate)) {
                System.out.println(descriptor);
            }
            System.out.println("-- " + predicate);
        }
        System.out.println("Total test requirements: " + getNumRequirements());
    }
}
