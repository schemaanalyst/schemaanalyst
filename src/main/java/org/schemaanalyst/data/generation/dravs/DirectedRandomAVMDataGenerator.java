package org.schemaanalyst.data.generation.dravs;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.DataGenerationReport;
import org.schemaanalyst.data.generation.cellinitialization.CellInitializer;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.random.RandomDataGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.PredicateChecker;
import org.schemaanalyst.util.random.Random;

/**
 * Created by phil on 26/02/2014.
 */
public class DirectedRandomAVMDataGenerator extends RandomDataGenerator {

	private Random random;
	private PredicateChecker predicateChecker;
	private PredicateFixer predicateFixer;
	private SearchMini<Data> search;

	public DirectedRandomAVMDataGenerator(Random random, int maxEvaluations,
			RandomCellValueGenerator cellValueGenerator, CellInitializer cellInitializer, SearchMini search) {
		super(maxEvaluations, cellValueGenerator, cellInitializer);
		this.random = random;
		this.search = search;
	}

	@Override
	public DataGenerationReport generateData(Data data, Data state, Predicate predicate) {
		// Init RAGTAG+AVM
		initialize(data, state, predicate);
		search.setMainData(data);
		boolean success = predicateChecker.check();
		int evaluations = 1;
		while (!success && evaluations < maxEvaluations) {
			search.initialize();

			attemptFix(data, evaluations);

			evaluations = evaluations + search.getNumEvaluations();
			evaluations++;
			success = predicateChecker.check();
		}

		return new DataGenerationReport(success, evaluations);
	}

	@Override
	protected void initialize(Data data, Data state, Predicate predicate) {
		super.initialize(data, state, predicate);
		predicateChecker = PredicateCheckerFactory.instantiate(predicate, true, data, state);
		predicateFixer = PredicateFixerFactory.instantiate(predicateChecker, random, randomCellValueGenerator, search,
				state);
	}

	@Override
	protected void attemptFix(Data data, int eval) {
		predicateFixer.attemptFix(eval);
	}
}
