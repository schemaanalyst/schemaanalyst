package org.schemaanalyst.data.generation.directedrandom;

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
                                      final Random random,
                                      final RandomCellValueGenerator cellValueGenerator,
                                      final boolean allowNull,
                                      final Data data,
                                      final Data state) {

        final PredicateChecker predicateChecker = PredicateCheckerFactory.instantiate(predicate, false, data, state);

        return new PredicateVisitor() {
            PredicateFixer predicateFixer;

            @Override
            public void visit(AndPredicate predicate) {

            }

            @Override
            public void visit(ExpressionPredicate predicate) {
                predicateFixer = new ExpressionPredicateFixer(
                        new ExpressionPredicateChecker(predicate, allowNull, data),
                        cellValueGenerator
                );
            }

            @Override
            public void visit(MatchPredicate predicate) {
                predicateFixer = new MatchPredicateFixer(
                        new MatchPredicateChecker(predicate, allowNull, data),
                        random,
                        cellValueGenerator
                );
            }

            @Override
            public void visit(NullPredicate predicate) {
                predicateFixer = new NullPredicateFixer(new NullPredicateChecker(predicate, data));
            }

            @Override
            public void visit(OrPredicate predicate) {

            }

            PredicateFixer instantiate() {
                predicate.accept(this);
                return predicateFixer;
            }
        }.instantiate();

    }
}
