package org.schemaanalyst.testgeneration;

import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.predicate.PredicateObjectiveFunction;
import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.logic.predicate.checker.PredicateChecker;
import org.schemaanalyst.testgeneration.coveragecriterion_old.requirements.Requirements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 24/02/2014.
 */
public class CoverageReport {

    private TestSuite testSuite;
    private Requirements requirements;
    private List<Predicate> coveredRequirements;
    private List<Predicate> uncoveredRequirements;

    public CoverageReport(TestSuite testSuite, Requirements requirements) {
        this.testSuite = testSuite;
        this.requirements = requirements;
        computeCoverage();
    }

    private void computeCoverage() {
        coveredRequirements = new ArrayList<>();
        uncoveredRequirements = new ArrayList<>();

        for (Predicate predicate : requirements.getPredicates()) {
            boolean coveredPredicate = false;
            for (TestCase testCase : testSuite.getTestCases()) {
                if (testCaseSatisfiesPredicate(testCase, predicate)) {
                    coveredPredicate = true;
                    coveredRequirements.add(predicate);

                    // It would be more efficient to break here, but continuing allows
                    // for more cross-comparison between checkers and objective functions
                    // allowing us to trap any bugs:

                    // break;
                }
            }
            if (!coveredPredicate) {
                uncoveredRequirements.add(predicate);
            }
        }
    }

    private boolean testCaseSatisfiesPredicate(TestCase testCase, Predicate predicate) {

        PredicateChecker predicateChecker = new PredicateChecker(predicate, testCase.getData(), testCase.getState());
        boolean checkerResult = predicateChecker.check();

        PredicateObjectiveFunction objFun = new PredicateObjectiveFunction(predicate, testCase.getState());
        ObjectiveValue objVal = objFun.evaluate(testCase.getData());
        boolean objectiveFunctionResult = objVal.isOptimal();

        if (checkerResult != objectiveFunctionResult) {

            // TODO: a better formatted message here...
            throw new CoverageException(
                    "Disagreement in result for predicate:\n" + predicate + "with test case:\n" + testCase +
                            "\nChecker: " + checkerResult + "\nObj Fun: " + objectiveFunctionResult +
                            "\nChecker info:\n " + predicateChecker.getInfo() +
                            "\nObj Fun info:\n " + objVal);
        }

        return checkerResult;
    }

    public List<Predicate> getCoveredRequirements() {
        return new ArrayList<>(coveredRequirements);
    }

    public List<Predicate> getUncoveredRequirements() {
        return new ArrayList<>(uncoveredRequirements);
    }

    public double getCoverage() {
        return coveredRequirements.size() / (double) (coveredRequirements.size() + uncoveredRequirements.size());
    }

    public int getNumRequirementsCovered() {
        return coveredRequirements.size();
    }

    public int getNumRequirementsUncovered() {
        return uncoveredRequirements.size();
    }

    public int getNumRequirements() {
        return requirements.size();
    }
}
