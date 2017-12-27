package org.schemaanalyst.data.generation.domino;

import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.search.AlternatingValueSearch;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.OrPredicateChecker;
import org.schemaanalyst.util.random.Random;

/**
 * Created by phil on 13/10/2014.
 * Updated by Abdullah Summer/Fall 2017
 */
 
public class OrPredicateFixer extends ComposedPredicateFixer {

    private Random random;

    public OrPredicateFixer(OrPredicateChecker orPredicateChecker,
                            Random random,
                            RandomCellValueGenerator cellValueGenerator,
                            AlternatingValueSearch avs) {
        super(orPredicateChecker, random, cellValueGenerator, avs);
        this.random = random;
    }

    @Override
    public void attemptFix() {
        int randomFixerIndex = random.nextInt(predicateFixers.size());
        PredicateFixer predicateFixer = predicateFixers.get(randomFixerIndex);
        predicateFixer.attemptFix();
    }
}
