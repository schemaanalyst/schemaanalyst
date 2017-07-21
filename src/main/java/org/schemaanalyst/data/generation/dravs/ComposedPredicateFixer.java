package org.schemaanalyst.data.generation.dravs;

import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.search.Search;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.ComposedPredicateChecker;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.PredicateChecker;
import org.schemaanalyst.util.random.Random;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 13/10/2014.
 */
public abstract class ComposedPredicateFixer extends PredicateFixer {

    protected List<PredicateFixer> predicateFixers;

    public ComposedPredicateFixer(ComposedPredicateChecker composedPredicateChecker,
                                  Random random,
                                  RandomCellValueGenerator cellValueGenerator, 
                                  SearchMini search) {
        predicateFixers = new ArrayList<>();
        for (PredicateChecker predicateChecker : composedPredicateChecker.getPredicateCheckers()) {
            predicateFixers.add(PredicateFixerFactory.instantiate(predicateChecker, random, cellValueGenerator, search));
        }
    }
}
