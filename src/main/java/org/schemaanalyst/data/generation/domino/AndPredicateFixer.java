package org.schemaanalyst.data.generation.domino;

import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.search.AlternatingValueSearch;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.AndPredicateChecker;
import org.schemaanalyst.util.random.Random;

/**
 * Created by phil on 13/10/2014.
 * Updated by Abdullah Summer/Fall 2017
 */
public class AndPredicateFixer extends ComposedPredicateFixer {

    public AndPredicateFixer(AndPredicateChecker andPredicateChecker,
                             Random random,
                             RandomCellValueGenerator cellValueGenerator,
                             AlternatingValueSearch avs) {
        super(andPredicateChecker, random, cellValueGenerator, avs);
    }

    @Override
    public void attemptFix() {
        for (PredicateFixer predicateFixer : predicateFixers) {
            predicateFixer.attemptFix();
        }
    }
}
