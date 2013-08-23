package org.schemaanalyst.datageneration.search;

import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.termination.TerminationCriterion;
import org.schemaanalyst.util.Duplicator;

/**
 * Abstract class for representing a search
 *
 * @author Phil McMinn
 *
 */
public abstract class Search<T> {

    protected Duplicator<T> duplicator;
    protected ObjectiveFunction<T> objFun;
    protected Counter evaluationsCounter, restartsCounter;
    protected ObjectiveValue bestObjVal;
    protected T bestCandidateSolution;
    protected TerminationCriterion terminationCriterion;

    /**
     * Constructor
     *
     * @param random The instance of random to use during the search.
     */
    public Search(Duplicator<T> duplicator) {
        this.duplicator = duplicator;
        evaluationsCounter = new Counter("Number of evaluations");
        restartsCounter = new Counter("Number of restarts");
    }

    /**
     * Sets the termination criterion for the search.
     *
     * @param terminationCriterion The terminationCriterion to be used.
     */
    public void setTerminationCriterion(TerminationCriterion terminationCriterion) {
        this.terminationCriterion = terminationCriterion;
    }

    /**
     * Sets the objective function for the search.
     *
     * @param objectiveFunction The objective function to be used to evaluate
     * candidate solutions during the search.
     */
    public void setObjectiveFunction(ObjectiveFunction<T> objectiveFunction) {
        this.objFun = objectiveFunction;
    }

    /**
     * Initializes the search (by resetting the counters).
     */
    public void initialize() {
        evaluationsCounter.reset();
        restartsCounter.reset();
        bestCandidateSolution = null;
        bestObjVal = null;
    }

    /**
     * Performs the search.
     *
     * @param candidateSolution The candidateSolution to use as the basis for
     * the search.
     */
    public abstract void search(T candidateSolution);

    /**
     * Performs an objective function evaluation.
     *
     * @return The objective value as a result of the objective function
     * evaluation.
     */
    protected ObjectiveValue evaluate(T candidateSolution) {
        ObjectiveValue objVal = objFun.evaluate(candidateSolution);

        if (bestObjVal == null || objVal.betterThan(bestObjVal)) {
            bestObjVal = objVal;
            bestCandidateSolution = duplicator.duplicate(candidateSolution);
        }

        evaluationsCounter.increment();
        return objVal;
    }

    /**
     * Returns the best objective value found by the search so far.
     *
     * @return The best objective value found by the search so far.
     */
    public ObjectiveValue getBestObjectiveValue() {
        return bestObjVal;
    }

    /**
     * Returns the candidate solution with the best objective value found by the
     * search so far.
     *
     * @return The candidate solution with the best objective value found by the
     * search so far.
     */
    public T getBestCandidateSolution() {
        return bestCandidateSolution;
    }

    /**
     * Returns the evaluations counter used by the search.
     *
     * @return The evaluations counter used by the search.
     */
    public Counter getEvaluationsCounter() {
        return evaluationsCounter;
    }

    /**
     * Returns the number of evaluations performed by the search.
     *
     * @return The number of evaluations performed by the search.
     */
    public int getNumEvaluations() {
        return evaluationsCounter.getValue();
    }

    /**
     * Returns the restarts counter used by the search.
     *
     * @return The restarts counter used by the search.
     */
    public Counter getRestartsCounter() {
        return restartsCounter;
    }

    /**
     * Returns the number of restarts performed by the search.
     *
     * @return The number of restarts performed by the search.
     */
    public int getNumRestarts() {
        return restartsCounter.getValue();
    }
}
