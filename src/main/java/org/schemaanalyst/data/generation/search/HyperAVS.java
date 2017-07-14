package org.schemaanalyst.data.generation.search;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.cellinitialization.CellInitializer;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.directedrandom.PredicateFixer;
import org.schemaanalyst.data.generation.directedrandom.PredicateFixerFactory;
import org.schemaanalyst.data.generation.search.objective.predicate.ComposedPredicateObjectiveFunction;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.ComposedPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.MatchPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.PredicateChecker;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.PredicateCheckerFactory;
import org.schemaanalyst.util.random.Random;

public class HyperAVS extends AlternatingValueSearch {

	private PredicateChecker predicateChecker;
	private PredicateFixer predicateFixer;
	protected RandomCellValueGenerator randomCellValueGenerator;

	
	public HyperAVS(Random random, CellInitializer startInitializer,
			CellInitializer restartInitializer, RandomCellValueGenerator randomCellValueGenerator) {
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
        	
        	copyMatches();

            alternateThroughValues();

            if (!terminationCriterion.satisfied()) {
                restartsCounter.increment();
            }

            if (!terminationCriterion.satisfied()) {
                restartInitialiser.initialize(data);
                lastObjVal = null;
                evaluate();
            }
        }
    }
    
    public void copyMatches() {
    	
    	if (this.objFun instanceof ComposedPredicateObjectiveFunction) {
    		// Get Predicates and states
    		Predicate predicate = ((ComposedPredicateObjectiveFunction) this.objFun).predicate;
    		Data state = ((ComposedPredicateObjectiveFunction) this.objFun).state;
    		
    		// copy by predicates
    		this.copyMatchesByPredicate(predicate, state);
    	}
    }
    
    public void copyMatchesByPredicate(Predicate predicate, Data state) {
		boolean success = false;

		if (predicate instanceof MatchPredicate) {

			// System.err.println(((MatchPredicate) p));
			predicateChecker = PredicateCheckerFactory.instantiate(predicate, true, data, state);

			predicateFixer = PredicateFixerFactory.instantiate(predicateChecker, random,
					randomCellValueGenerator);

			success = predicateChecker.check();

			while (!success && this.getNumEvaluations() < 100000) {
				attemptFix(data);
				this.evaluationsCounter.increment();
				success = predicateChecker.check();
			}
		}
    	
    	// iterate through predicates
		if (predicate instanceof ComposedPredicate) {
			// Get sub predicates
			for (Predicate p : ((ComposedPredicate) predicate).getSubPredicates()) {
				// Check a predicate
				this.copyMatchesByPredicate(p, state);
			}
		}
    	
    	
    }
    
	protected void attemptFix(Data data) {
		predicateFixer.attemptFix();
	}

}
