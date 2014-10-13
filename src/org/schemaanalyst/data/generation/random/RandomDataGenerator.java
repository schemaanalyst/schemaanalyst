package org.schemaanalyst.data.generation.random;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.DataGenerationReport;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.cellinitialization.CellInitializer;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.predicate.PredicateObjectiveFunctionFactory;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;

/**
 * Created by phil on 12/03/2014.
 *
 * TODO - consolidate into SearchBasedDataGenerator or reintroduce PredicateChecking.
 *
 */
public class RandomDataGenerator extends DataGenerator {

    protected RandomCellValueGenerator randomCellValueGenerator;
    protected int maxEvaluations;
    protected CellInitializer cellInitializer;;
    protected ObjectiveFunction<Data> objectiveFunction;

    public RandomDataGenerator(int maxEvaluations,
                               RandomCellValueGenerator randomCellValueGenerator,
                               CellInitializer cellInitializer) {
        this.maxEvaluations = maxEvaluations;
        this.randomCellValueGenerator = randomCellValueGenerator;
        this.cellInitializer = cellInitializer;
    }

    @Override
    public DataGenerationReport generateData(Data data, Data state, Predicate predicate) {
        initialize(data, state, predicate);
        boolean success = objectiveFunction.evaluate(data).isOptimal();
        int evaluations = 1;
        while (!success && evaluations < maxEvaluations) {
            attemptFix(data);
            evaluations ++;
            success = objectiveFunction.evaluate(data).isOptimal();
        }

        return new DataGenerationReport(success, evaluations);
    }

    protected void initialize(Data data, Data state, Predicate predicate) {
        cellInitializer.initialize(data);
        objectiveFunction = PredicateObjectiveFunctionFactory.createObjectiveFunction(predicate, state);
    }

    protected void attemptFix(Data data) {
        for (Cell cell : data.getCells()) {
            randomCellValueGenerator.generateCellValue(cell);
        }
    }
}
