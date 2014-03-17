package org.schemaanalyst.data.generation.search;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.ValueLibrary;
import org.schemaanalyst.data.generation.search.objectivefunction.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objectivefunction.ObjectiveValue;
import org.schemaanalyst.util.random.Random;

/**
 * Abstract class for representing a search algorithm
 *
 * @author Phil McMinn
 *
 */
public abstract class Search {

    protected Random random;
    protected int maxEvaluations;
    protected ValueLibrary valueLibrary;

    protected ObjectiveFunction<Data> objectiveFunction;
    protected ObjectiveValue bestObjectiveValue;
    protected Data bestCandidateSolution;
    protected int numEvaluations;
    protected int numRestarts;

    /**
     * Constructor.
     */
    public Search(Random random, int maxEvaluations, ValueLibrary valueLibrary) {
        this.random = random;
        this.maxEvaluations = maxEvaluations;
        this.valueLibrary = valueLibrary;
    }

    /**
     * Sets the objective function for the search.
     * @param objectiveFunction The objective function to be used to evaluate
     * candidate solutions during the search.
     */
    public void setObjectiveFunction(ObjectiveFunction<Data> objectiveFunction) {
        this.objectiveFunction = objectiveFunction;
    }

    /**
     * Initializes the search (by resetting the counters).
     */
    public void initialize() {
        numEvaluations = 0;
        numRestarts = 0;
        bestCandidateSolution = null;
        bestObjectiveValue = null;
    }

    /**
     * Performs the generation.
     * @param candidateSolution The candidateSolution to use as the basis for
     * the generation.
     */
    public abstract void search(Data candidateSolution);

    /**
     * Performs an objective function evaluation.
     * @return The objective value as a result of the objective function
     * evaluation.
     */
    protected ObjectiveValue evaluate(Data candidateSolution) {
        ObjectiveValue objectiveValue = objectiveFunction.evaluate(candidateSolution);

        if (bestObjectiveValue == null || objectiveValue.betterThan(bestObjectiveValue)) {
            bestObjectiveValue = objectiveValue;
            bestCandidateSolution = candidateSolution.duplicate();
        }

        numEvaluations ++;
        return objectiveValue;
    }

    /**
     * Returns the best objective value found by the search so far.
     * @return The best objective value found by the search so far.
     */
    public ObjectiveValue getBestObjectiveValue() {
        return bestObjectiveValue;
    }

    /**
     * Returns the candidate solution with the best objective value found by the
     * search so far.
     * @return The candidate solution with the best objective value found by the
     * search so far.
     */
    public Data getBestCandidateSolution() {
        return bestCandidateSolution;
    }

    /**
     * Returns the number of evaluations performed by the search.
     * @return The number of evaluations performed by the search.
     */
    public int getNumEvaluations() {
        return numEvaluations;
    }

    /**
     * Returns the number of restarts performed by the search.
     * @return The number of restarts performed by the search.
     */
    public int getNumRestarts() {
        return numRestarts;
    }
}
