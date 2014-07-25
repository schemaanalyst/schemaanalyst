package org.schemaanalyst.testgeneration.coveragecriterion;

import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by phil on 23/07/2014.
 */
public class TestRequirements {

    private final static Logger LOGGER = Logger.getLogger(TestRequirements.class.getName());

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

    public void reduce() {
        Map<Predicate, Set<TestRequirement>> reducedSet = new HashMap<>();

        for (TestRequirement testRequirement : testRequirements) {
            Predicate predicate = testRequirement.getPredicate().reduce();

            Set<TestRequirement> predicateRequirements = reducedSet.get(predicate);
            if (predicateRequirements == null) {
                predicateRequirements = new HashSet<>();
            }
            predicateRequirements.add(testRequirement);

            reducedSet.put(predicate, predicateRequirements);
        }

        LOGGER.fine("Reduced requirements = " + reducedSet.keySet().size());

        if (reducedSet.keySet().size() < testRequirements.size()) {
            testRequirements = new ArrayList<>();

            for (Predicate predicate : reducedSet.keySet()) {
                TestRequirement testRequirement = new TestRequirement(predicate);

                Set<TestRequirement> testRequirementsForPredicate = reducedSet.get(predicate);
                Iterator<TestRequirement> testRequirementsForPredicateIterator = testRequirementsForPredicate.iterator();
                while (testRequirementsForPredicateIterator.hasNext()) {
                    testRequirement.addDescriptors(testRequirementsForPredicateIterator.next().getDescriptors());
                }

                testRequirements.add(testRequirement);
            }
        }
    }

    public void filterInfeasible() {
        List<TestRequirement> filteredList = new ArrayList<>();

        for (TestRequirement testRequirement : testRequirements) {
            if (testRequirement.getPredicate().reduce().isInfeasible()) {
                LOGGER.fine("Filtered infeasible requirement: " + testRequirement);
            } else {
                filteredList.add(testRequirement);
            }
        }

        if (filteredList.size() < testRequirements.size()) {
            testRequirements = filteredList;
        }
    }

    public int size() {
        return testRequirements.size();
    }
}
