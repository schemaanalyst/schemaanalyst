package org.schemaanalyst.data.generation.altarkiz;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.DataGenerationReport;
import org.schemaanalyst.data.generation.cellinitialization.CellInitializer;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.random.RandomDataGenerator;
import org.schemaanalyst.data.generation.search.AlternatingValueSearch;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.PredicateChecker;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.PredicateCheckerFactory;
import org.schemaanalyst.util.random.Random;

/**
 * Created by phil on 26/02/2014.
 */
public class AltarkizDataGenerator extends RandomDataGenerator {

    private Random random;
    private AlternatingValueSearch avs;
    private PredicateChecker predicateChecker;
    private PredicateFixer predicateFixer;

    public AltarkizDataGenerator(Random random,
                                 int maxEvaluations,
                                 RandomCellValueGenerator cellValueGenerator,
                                 CellInitializer cellInitializer,
                                 AlternatingValueSearch avs) {
        super(maxEvaluations, cellValueGenerator, cellInitializer);
        this.random = random;
        this.avs = avs;
    }

    @Override
    public DataGenerationReport generateData(Data data, Data state, Predicate predicate) {
        initialize(data, state, predicate);
        boolean success = predicateChecker.check();
        int evaluations = 1;
        while (!success && evaluations < maxEvaluations) {
            attemptFix(data);
            evaluations ++;
            success = predicateChecker.check();
        }

        return new DataGenerationReport(success, evaluations);
    }

    @Override
    protected void initialize(Data data, Data state, Predicate predicate) {
        super.initialize(data, state, predicate);
        predicateChecker = PredicateCheckerFactory.instantiate(
                predicate, true, data, state
        );
        predicateFixer = PredicateFixerFactory.instantiate(
                predicateChecker, random, randomCellValueGenerator, avs);
    }

    @Override
    protected void attemptFix(Data data) {
        predicateFixer.attemptFix();
    }
}
