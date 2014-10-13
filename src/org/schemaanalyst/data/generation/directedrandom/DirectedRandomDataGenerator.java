package org.schemaanalyst.data.generation.directedrandom;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.cellinitialization.CellInitializer;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.random.RandomDataGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;
import org.schemaanalyst.util.random.Random;

/**
 * Created by phil on 26/02/2014.
 */
public class DirectedRandomDataGenerator extends RandomDataGenerator {

    private Random random;
    private PredicateFixer predicateFixer;

    public DirectedRandomDataGenerator(Random random,
                                       int maxEvaluations,
                                       RandomCellValueGenerator cellValueGenerator,
                                       CellInitializer cellInitializer) {
        super(maxEvaluations, cellValueGenerator, cellInitializer);
        this.random = random;
    }

    @Override
    protected void initialize(Data data, Data state, Predicate predicate) {
        super.initialize(data, state, predicate);
        predicateFixer = PredicateFixerFactory.instantiate(
                predicate, random, randomCellValueGenerator, true, data, state
        );
    }

    @Override
    protected void attemptFix(Data data) {
        predicateFixer.attemptFix();
    }
}
