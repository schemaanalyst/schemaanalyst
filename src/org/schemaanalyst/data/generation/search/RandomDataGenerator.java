package org.schemaanalyst.data.generation.search;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.CellValueGenerator;
import org.schemaanalyst.data.generation.DataGenerationReport;
import org.schemaanalyst.data.generation.DataGenerator;
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
    private CellValueGenerator cellValueGenerator;
    private int maxEvaluations;

    public RandomDataGenerator(Random random, CellValueGenerator cellValueGenerator, int maxEvaluations) {
        this.random = random;
        this.cellValueGenerator = cellValueGenerator;
        this.maxEvaluations = maxEvaluations;
    }

    @Override
    public DataGenerationReport generateData(Data data, Data state, Predicate predicate) {
        PredicateChecker predicateChecker = new PredicateChecker(predicate, data, state);

        // use a start initialiser?

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
