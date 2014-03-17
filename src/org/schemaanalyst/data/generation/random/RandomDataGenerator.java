package org.schemaanalyst.data.generation.random;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.DataGenerationReport;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
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

    private Random random;
    private RandomCellValueGenerator cellValueGenerator;
    private int maxEvaluations;

    public RandomDataGenerator(Random random, RandomCellValueGenerator cellValueGenerator, int maxEvaluations) {
        this.random = random;
        this.cellValueGenerator = cellValueGenerator;
        this.maxEvaluations = maxEvaluations;
    }

    @Override
    public DataGenerationReport generateData(Data data, Data state, Predicate predicate) {
        PredicateChecker predicateChecker = new PredicateChecker(predicate, data, state);

        boolean success = predicateChecker.check();
        int evaluations = 0;
        while (!success && evaluations < maxEvaluations) {

            for (Cell cell : data.getCells()) {
                cellValueGenerator.generateCellValue(cell);
            }

            evaluations ++;
            success = predicateChecker.check();
        }

        return new DataGenerationReport(success, evaluations);
    }
}
