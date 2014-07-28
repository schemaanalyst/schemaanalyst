package org.schemaanalyst.data.generation.search;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.predicate.PredicateObjectiveFunctionFactory;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;

/**
 * Created by phil on 05/02/2014.
 */
public class SearchBasedDataGenerator extends DataGenerator {

    private Search<Data> search;

    public SearchBasedDataGenerator(Search search) {
        this.search = search;
    }

    @Override
    public SearchBasedDataGenerationReport generateData(Data data, Data state, Predicate predicate) {

        search.setObjectiveFunction(PredicateObjectiveFunctionFactory.createObjectiveFunction(predicate, state));
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
