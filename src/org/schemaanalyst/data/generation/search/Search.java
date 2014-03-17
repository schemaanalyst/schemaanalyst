package org.schemaanalyst.data.generation.search;

import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.data.generation.search.termination.TerminationCriterion;
import org.schemaanalyst.util.Duplicator;

/**
 * Abstract class for representing a generation
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
     * @param duplicator the duplicator instance responsible for producing
     * duplicate solution instances.
     */
    public Search(Duplicator<T> duplicator) {
        this.duplicator = duplicator;
        evaluationsCounter = new Counter("Number of evaluations");
        restartsCounter = new Counter("Number of restarts");
    }

    /**
     * Sets the termination criterion for the generation.
     *
     * @param terminationCriterion The terminationCriterion to be used.
     */
    public void setTerminationCriterion(TerminationCriterion terminationCriterion) {
        this.terminationCriterion = terminationCriterion;
    }

    /**
     * Sets the objective function for the generation.
     *
     * @param objectiveFunction The objective function to be used to evaluate
     * candidate solutions during the generation.
     */
    public void setObjectiveFunction(ObjectiveFunction<T> objectiveFunction) {
        this.objFun = objectiveFunction;
    }

    /**
     * Initializes the generation (by resetting the counters).
     */
    public void initialize() {
        evaluationsCounter.reset();
        restartsCounter.reset();
        bestCandidateSolution = null;
        bestObjVal = null;
    }

    /**
     * Performs the generation.
     *
     * @param candidateSolution The candidateSolution to use as the basis for
     * the generation.
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
     * Returns the best objective value found by the generation so far.
     *
     * @return The best objective value found by the generation so far.
     */
    public ObjectiveValue getBestObjectiveValue() {
        return bestObjVal;
    }

    /**
     * Returns the candidate solution with the best objective value found by the
     * generation so far.
     *
     * @return The candidate solution with the best objective value found by the
     * generation so far.
     */
    public T getBestCandidateSolution() {
        return bestCandidateSolution;
    }

    /**
     * Returns the evaluations counter used by the generation.
     *
     * @return The evaluations counter used by the generation.
     */
    public Counter getEvaluationsCounter() {
        return evaluationsCounter;
    }

    /**
     * Returns the number of evaluations performed by the generation.
     *
     * @return The number of evaluations performed by the generation.
     */
    public int getNumEvaluations() {
        return evaluationsCounter.getValue();
    }

    /**
     * Returns the restarts counter used by the generation.
     *
     * @return The restarts counter used by the generation.
     */
    public Counter getRestartsCounter() {
        return restartsCounter;
    }

    /**
     * Returns the number of restarts performed by the generation.
     *
     * @return The number of restarts performed by the generation.
     */
    public int getNumRestarts() {
        return restartsCounter.getValue();
    }
}
