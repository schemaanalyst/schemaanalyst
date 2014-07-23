package org.schemaanalyst.data.generation.random;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.cellinitialization.CellInitializer;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.logic.predicate.checker.PredicateChecker;

/**
 * Created by phil on 12/03/2014.
 *
 * TODO - consolidate into SearchBasedDataGenerator
 *
 */
public class RandomDataGenerator { // extends DataGenerator {

    protected RandomCellValueGenerator randomCellValueGenerator;
    protected int maxEvaluations;
    protected CellInitializer cellInitializer;
    protected PredicateChecker predicateChecker;

    public RandomDataGenerator(int maxEvaluations,
                               RandomCellValueGenerator randomCellValueGenerator,
                               CellInitializer cellInitializer) {
        this.maxEvaluations = maxEvaluations;
        this.randomCellValueGenerator = randomCellValueGenerator;
        this.cellInitializer = cellInitializer;
    }

    /*
    @Override
    public DataGenerationReport generateData(Data data, Data state, Predicate predicate) {
        initialize(data, state, predicate);
        boolean success = predicateChecker.check();
        int evaluations = 0;
        while (!success && evaluations < maxEvaluations) {
            attemptFix(data);
            evaluations ++;
            success = predicateChecker.check();
        }

        return new DataGenerationReport(success, evaluations);
    }

    protected void initialize(Data data, Data state, Predicate predicate) {
        cellInitializer.initialize(data);
        predicateChecker = new PredicateChecker(predicate, data, state);
    }

    protected void attemptFix(Data data) {
        for (Cell cell : data.getCells()) {
            randomCellValueGenerator.generateCellValue(cell);
        }
    }
    */
}
