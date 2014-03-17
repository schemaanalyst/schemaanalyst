package _deprecated.datageneration.search;

import _deprecated.datageneration.TestCase;
import _deprecated.datageneration.search.objective.ObjectiveValue;

public class SearchTestCase<E> extends TestCase<E> {

    private int numEvaluations, numRestarts;
    private ObjectiveValue bestObjectiveValue;

	public SearchTestCase(String description) {
        super(description);
        numEvaluations = 0;
        numRestarts = 0;
    }

    public int getNumEvaluations() {
        return numEvaluations;
    }

    public void setNumEvaluations(int numEvaluations) {
        this.numEvaluations = numEvaluations;
    }

    public int getNumRestarts() {
        return numRestarts;
    }

    public void setNumRestarts(int numRestarts) {
        this.numRestarts = numRestarts;
    }

    public void setBestObjectiveValue(ObjectiveValue bestObjectiveValue) {
        this.bestObjectiveValue = bestObjectiveValue;
    }

    public ObjectiveValue getBestObjectiveValue() {
        return bestObjectiveValue;
    }		
}
