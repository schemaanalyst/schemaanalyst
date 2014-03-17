package org.schemaanalyst.data.generation.directedrandom;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.CellValueGenerator;
import org.schemaanalyst.data.generation.DataGenerationReport;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.logic.predicate.checker.PredicateChecker;
import org.schemaanalyst.util.random.Random;

/**
 * Created by phil on 26/02/2014.
 */
public class DirectedRandomDataGenerator extends DataGenerator {

    private Random random;
    private CellValueGenerator cellValueGenerator;
    private int maxEvaluations;

    public DirectedRandomDataGenerator(Random random, CellValueGenerator cellValueGenerator, int maxEvaluations) {
        this.random = random;
        this.cellValueGenerator = cellValueGenerator;
        this.maxEvaluations = maxEvaluations;
    }

    @Override
    public DataGenerationReport generateData(Data data, Data state, Predicate predicate) {
        PredicateChecker predicateChecker = new PredicateChecker(predicate, data, state);
        PredicateFixer predicateFixer = new PredicateFixer(predicateChecker, random, cellValueGenerator);

        // use a start initialiser?

        boolean success = predicateChecker.check();
        int evaluations = 0;
        while (!success && evaluations < maxEvaluations) {
            predicateFixer.attemptFix();
            evaluations ++;
            success = predicateChecker.check();
        }

        return new DataGenerationReport(success, evaluations);
    }
}
