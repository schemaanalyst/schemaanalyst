package org.schemaanalyst.data.generation.search;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.search.objectivefunction.PredicateObjectiveFunction;
import org.schemaanalyst.datageneration.search.Search;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.logic.predicate.Predicate;

/**
 * Created by phil on 05/02/2014.
 */
public class SearchBasedDataGenerator extends DataGenerator {

    private Search<Data> search;

    public SearchBasedDataGenerator(Search<Data> search) {
        this.search = search;
    }

    @Override
    public SearchBasedDataGenerationReport generateData(Data data, Data state, Predicate predicate) {

        search.setObjectiveFunction(new PredicateObjectiveFunction(predicate, state));
        search.initialize();
        search.search(data);

        int numEvaluations = search.getNumEvaluations();
        ObjectiveValue bestObjectiveValue = search.getBestObjectiveValue();
        boolean success = bestObjectiveValue.isOptimal();

        if (!success) {
            data.copyValues(search.getBestCandidateSolution());
        }

        return new SearchBasedDataGenerationReport(
                success,
                numEvaluations,
                bestObjectiveValue);
    }
}
