package org.schemaanalyst.data.generation.dravs;

import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.search.Search;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.AndPredicateChecker;
import org.schemaanalyst.util.random.Random;

/**
 * Created by phil on 13/10/2014.
 */
public class AndPredicateFixer extends ComposedPredicateFixer {

    public AndPredicateFixer(AndPredicateChecker andPredicateChecker,
                             Random random,
                             RandomCellValueGenerator cellValueGenerator,
                             SearchMini search) {
        super(andPredicateChecker, random, cellValueGenerator, search);
    }

    @Override
    public void attemptFix() {
        for (PredicateFixer predicateFixer : predicateFixers) {
            predicateFixer.attemptFix();
        }
    }
}
