package org.schemaanalyst.data.generation.domino;

import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.search.AlternatingValueSearch;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.*;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.*;
import org.schemaanalyst.util.random.Random;

/**
 * Created by phil on 13/10/2014.
 * Updated by Abdullah Summer/Fall 2017
 */
 
public class PredicateFixerFactory {

    public static PredicateFixer instantiate(final PredicateChecker predicateChecker,
                                             final Random random,
                                             final RandomCellValueGenerator randomCellValueGenerator,
                                             final AlternatingValueSearch avs) {
        return new PredicateVisitor() {
            PredicateFixer predicateFixer;

            @Override
            public void visit(AndPredicate predicate) {
                predicateFixer = new AndPredicateFixer(
                        (AndPredicateChecker) predicateChecker, random, randomCellValueGenerator, avs);
            }

            @Override
            public void visit(ExpressionPredicate predicate) {
                if (avs == null) {
                    predicateFixer = new RandomExpressionPredicateFixer(
                            (ExpressionPredicateChecker) predicateChecker, randomCellValueGenerator);
                } else {
                    predicateFixer = new AVSExpressionPredicateFixer(
                            (ExpressionPredicateChecker) predicateChecker, avs);
                }
            }

            @Override
            public void visit(MatchPredicate predicate) {
                predicateFixer = new MatchPredicateFixer(
                        (MatchPredicateChecker) predicateChecker, random, randomCellValueGenerator);
            }

            @Override
            public void visit(NullPredicate predicate) {
                predicateFixer = new NullPredicateFixer(
                        (NullPredicateChecker) predicateChecker);
            }

            @Override
            public void visit(OrPredicate predicate) {
                predicateFixer = new OrPredicateFixer(
                        (OrPredicateChecker) predicateChecker, random, randomCellValueGenerator, avs);
            }

            PredicateFixer instantiate() {
                predicateChecker.getPredicate().accept(this);
                return predicateFixer;
            }
        }.instantiate();

    }
}
