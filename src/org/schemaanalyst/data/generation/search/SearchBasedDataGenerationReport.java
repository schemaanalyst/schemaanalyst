package org.schemaanalyst.data.generation.search;

import org.schemaanalyst.data.generation.DataGenerationReport;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;

/**
 * Created by phil on 14/03/2014.
 */
public class SearchBasedDataGenerationReport extends DataGenerationReport {

    private ObjectiveValue bestObjectiveValue;

    public SearchBasedDataGenerationReport(boolean success, int numEvaluations, ObjectiveValue bestObjectiveValue) {
        super(success, numEvaluations);
        this.bestObjectiveValue = bestObjectiveValue;
    }

    public ObjectiveValue getBestObjectiveValue() {
        return bestObjectiveValue;
    }
}
