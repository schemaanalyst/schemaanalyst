package org.schemaanalyst.data.generation.dravs;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.*;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.*;
import org.schemaanalyst.util.random.Random;

/**
 * Created by phil on 13/10/2014.
 */
public class PredicateFixerFactory {

    public static PredicateFixer instantiate(final Predicate predicate,
                                             final boolean allowNull,
                                             final Data data,
                                             final Data state,
                                             final Random random,
                                             final RandomCellValueGenerator randomCellValueGenerator,
                                             final SearchMini search) {
        PredicateChecker predicateChecker = PredicateCheckerFactory.instantiate(predicate, allowNull, data, state);
        return instantiate(predicateChecker, random, randomCellValueGenerator, search, state);
    }


    public static PredicateFixer instantiate(final PredicateChecker predicateChecker,
                                             final Random random,
                                             final RandomCellValueGenerator randomCellValueGenerator,
                                             final SearchMini search,
                                             final Data state) {
        return new PredicateVisitor() {
            PredicateFixer predicateFixer;

            @Override
            public void visit(AndPredicate predicate) {
                predicateFixer = new AndPredicateFixer(
                        (AndPredicateChecker) predicateChecker, random, randomCellValueGenerator, search, state);
            }

            @Override
            public void visit(ExpressionPredicate predicate) {
                predicateFixer = new ExpressionPredicateFixer(
                        (ExpressionPredicateChecker) predicateChecker, randomCellValueGenerator, search, state);
            }

            @Override
            public void visit(MatchPredicate predicate) {
                predicateFixer = new MatchPredicateFixer(
                        (MatchPredicateChecker) predicateChecker, random, randomCellValueGenerator, search, state);
            }

            @Override
            public void visit(NullPredicate predicate) {
                predicateFixer = new NullPredicateFixer(
                        (NullPredicateChecker) predicateChecker);
            }

            @Override
            public void visit(OrPredicate predicate) {
                predicateFixer = new OrPredicateFixer(
                        (OrPredicateChecker) predicateChecker, random, randomCellValueGenerator, search, state);
            }

            PredicateFixer instantiate() {
                predicateChecker.getPredicate().accept(this);
                return predicateFixer;
            }
        }.instantiate();

    }
}
