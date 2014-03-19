package org.schemaanalyst.data.generation.directedrandom;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.DataGenerationReport;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.cellinitialization.CellInitializer;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.random.RandomDataGenerator;
import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.logic.predicate.checker.PredicateChecker;
import org.schemaanalyst.util.random.Random;

/**
 * Created by phil on 26/02/2014.
 */
public class DirectedRandomDataGenerator extends RandomDataGenerator {

    private PredicateFixer predicateFixer;

    public DirectedRandomDataGenerator(Random random,
                                       RandomCellValueGenerator cellValueGenerator,
                                       int maxEvaluations,
                                       CellInitializer cellInitializer) {
        super(random, cellValueGenerator, maxEvaluations, cellInitializer);
    }

    @Override
    protected void initialize(Data data, Data state, Predicate predicate) {
        super.initialize(data, state, predicate);
        predicateFixer = new PredicateFixer(predicateChecker, random, cellValueGenerator);
    }

    @Override
    protected void attemptFix(Data data) {
        predicateFixer.attemptFix();
    }
}
