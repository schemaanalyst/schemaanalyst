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

    public void addTestRequirements(TestRequirements testRequirements) {
        this.testRequirements.addAll(testRequirements.getTestRequirements());
    }

    public void addTestRequirement(TestRequirement testRequirement) {
        testRequirements.add(testRequirement);

        // updated the list, now sort it
        Collections.sort(testRequirements);
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

                Set<TestRequirement> testRequirementsForPredicate = reducedSet.get(predicate);
                Iterator<TestRequirement> testRequirementsForPredicateIterator = testRequirementsForPredicate.iterator();

                TestRequirement testRequirement = testRequirementsForPredicateIterator.next();
                while (testRequirementsForPredicateIterator.hasNext()) {
                    TestRequirement duplicateTestRequirement = testRequirementsForPredicateIterator.next();

                    // ensure that if a comparison row is ever required, the test requirement used requires one
                    if (duplicateTestRequirement.getRequiresComparisonRow()) {
                        testRequirement.setRequiresComparisonRow(true);
                    }

                    if (testRequirement.getResult() != duplicateTestRequirement.getResult()) {
                        throw new CoverageCriterionException("Test requirements have same predicate but not same result");
                    }
                    testRequirement.addDescriptors(duplicateTestRequirement.getDescriptors());
                }
                testRequirements.add(testRequirement);
            }

            // updated the list, now sort it
            Collections.sort(testRequirements);
        }
    }

    public void filterInfeasible() {
        List<TestRequirement> filteredList = new ArrayList<>();

        for (TestRequirement testRequirement : testRequirements) {
            if (testRequirement.getPredicate().reduce().isTriviallyInfeasible()) {
                LOGGER.fine("Filtered infeasible requirement: " + testRequirement);
            } else {
                filteredList.add(testRequirement);
            }
        }

        if (filteredList.size() < testRequirements.size()) {
            testRequirements = filteredList;

            // updated the list, now sort it
            Collections.sort(testRequirements);
        }
    }

    public int size() {
        return testRequirements.size();
    }
}
