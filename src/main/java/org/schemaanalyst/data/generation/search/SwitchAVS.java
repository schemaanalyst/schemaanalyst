package org.schemaanalyst.data.generation.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.generation.cellinitialization.CellInitializer;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.directedrandom.PredicateFixer;
import org.schemaanalyst.data.generation.directedrandom.PredicateFixerFactory;
import org.schemaanalyst.data.generation.search.fixer.MatchPredicateFixer;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.ComposedPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.MatchPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.MatchPredicateChecker;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.PredicateChecker;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.PredicateCheckerFactory;
import org.schemaanalyst.util.random.Random;

public class SwitchAVS extends AlternatingValueSearch {

	protected RandomCellValueGenerator randomCellValueGenerator;
	private Data state = null;
	private List<Predicate> matches = null;
	private PredicateChecker predicateChecker;
	private PredicateFixer predicateFixer;
	private Predicate predicate;

	public SwitchAVS(Random random, CellInitializer startInitializer, CellInitializer restartInitializer,
			RandomCellValueGenerator randomCellValueGenerator) {
		super(random, startInitializer, restartInitializer);
		this.randomCellValueGenerator = randomCellValueGenerator;
	}

	@Override
	public void search(Data data) {
		// set up
		this.data = data;
		cells = data.getCells();

		// start
		startInitialiser.initialize(data);
		lastObjVal = null;
		evaluate();

		// main loop
		while (!terminationCriterion.satisfied()) {

			predicate = objFun.getpredicate();
			state = objFun.getState();
			
			//predicateChecker = PredicateCheckerFactory.instantiate(predicate, true, data, state);
			//predicateFixer = PredicateFixerFactory.instantiate(predicateChecker, random, randomCellValueGenerator);
			
			// Get Match predicates
			matches = getMatchPredicates(predicate);
			// Make a copy move
			//switcher();
			copyMove();
			
			//evaluate();

			alternateThroughValues();

			if (!terminationCriterion.satisfied()) {
				restartsCounter.increment();
			}

			if (!terminationCriterion.satisfied()) {
				restartInitialiser.initialize(data);
				lastObjVal = null;
				evaluate();
			}
			
			//
		}
	}

	protected boolean switcher() {
		boolean improvement = predicateChecker.check();

		Data original = data.duplicate();
		attemptFix(data);
		this.evaluationsCounter.increment();
		improvement = evaluate();
		if (!improvement) {
			data = original.duplicate();
		}

		return improvement;
	}

	protected void attemptFix(Data data) {
		predicateFixer.attemptFix();
	}
	
	/**
	 * Get a list of match predicates
	 * 
	 * @param predicate
	 * @return predicates List of match predicates
	 */
	protected List<Predicate> getMatchPredicates(Predicate predicate) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (predicate instanceof MatchPredicate) {
			predicates.add(predicate);
		}
		
		if (predicate instanceof ComposedPredicate) {
			for (Predicate p : ((ComposedPredicate) predicate).getSubPredicates()) {
				predicates.addAll(this.getMatchPredicates(p));
			}
		}

		return predicates;
	}
	
	protected boolean copyMove() {
		boolean copySuccess = false;

		for (Predicate mp : matches) {
			MatchPredicate p = (MatchPredicate) mp;
			MatchPredicateChecker checker = new MatchPredicateChecker(p, true, data, state);
			//checker.check();
			Data original = data.duplicate();
			if (checker.check()) {
				MatchPredicateFixer fixer = new MatchPredicateFixer(checker, random, randomCellValueGenerator);
				fixer.attemptFix();
				if (checker.check()) {
					copySuccess = true;
				} else {
					data = original.duplicate();
				}
			}
		}
		
		copySuccess = evaluate();

		return copySuccess;
	}
	


}
