package org.schemaanalyst.data.generation.random;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.DataGenerationReport;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.cellinitialization.CellInitializer;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.directedrandom.PredicateFixer;
import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.logic.predicate.checker.PredicateChecker;
import org.schemaanalyst.util.random.Random;

/**
 * Created by phil on 12/03/2014.
 *
 * TODO - consolidate into SearchBasedDataGenerator
 *
 */
public class RandomDataGenerator extends DataGenerator {

    protected Random random;
    protected RandomCellValueGenerator cellValueGenerator;
    protected int maxEvaluations;
    protected CellInitializer cellInitializer;
    protected PredicateChecker predicateChecker;

    public RandomDataGenerator(Random random,
                                       RandomCellValueGenerator cellValueGenerator,
                                       int maxEvaluations,
                                       CellInitializer cellInitializer) {
        this.random = random;
        this.cellValueGenerator = cellValueGenerator;
        this.maxEvaluations = maxEvaluations;
        this.cellInitializer = cellInitializer;
    }

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
            cellValueGenerator.generateCellValue(cell);
        }
    }
}
