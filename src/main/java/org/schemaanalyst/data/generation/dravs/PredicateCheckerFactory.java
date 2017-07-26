package org.schemaanalyst.data.generation.dravs;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.AndPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.ExpressionPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.MatchPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.NullPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.OrPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.PredicateVisitor;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.AndPredicateChecker;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.ExpressionPredicateChecker;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.MatchPredicateChecker;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.NullPredicateChecker;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.OrPredicateChecker;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.PredicateChecker;

public class PredicateCheckerFactory {
	public static PredicateChecker instantiate(final Predicate predicate, final boolean allowNull, final Data data,
			final Data state) {

		return new PredicateVisitor() {
			PredicateChecker predicateChecker;

			@Override
			public void visit(AndPredicate predicate) {
				predicateChecker = new AndPredicateChecker(predicate, allowNull, data, state);
			}

			@Override
			public void visit(ExpressionPredicate predicate) {
				predicateChecker = new ExpressionPredicateChecker(predicate, allowNull, data);
			}

			@Override
			public void visit(MatchPredicate predicate) {
				predicateChecker = new MatchPredicateChecker(predicate, allowNull, data, state);
			}

			@Override
			public void visit(NullPredicate predicate) {
				predicateChecker = new NullPredicateChecker(predicate, data);
			}

			@Override
			public void visit(OrPredicate predicate) {
				predicateChecker = new OrPredicateChecker(predicate, allowNull, data, state);
			}

			PredicateChecker instantiate() {
				predicate.accept(this);
				return predicateChecker;
			}
		}.instantiate();
	}
}
