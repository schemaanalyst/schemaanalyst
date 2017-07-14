package org.schemaanalyst.data.generation.search;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.generation.cellinitialization.CellInitializer;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.search.objective.predicate.ComposedPredicateObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.ComposedPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.MatchPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;
import org.schemaanalyst.util.random.Random;

public class SwitcherAVS extends AlternatingValueSearch {

	protected RandomCellValueGenerator randomCellValueGenerator;
	private List<Predicate> matches = null;
	private Data state = null;
	private Data combained = null;
	private boolean foundmatches = false;

	public SwitcherAVS(Random random, CellInitializer startInitializer, CellInitializer restartInitializer,
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
			foundmatches = foundMatchePredicates();
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

	@Override
	protected boolean valueSearch(Cell cell) {
		boolean improvement = invertNullMove(cell);

		if (!cell.isNull()) {

			boolean copyImporvement = false;
			if (foundmatches) {
				if (copyMove(cell)) {
					improvement = true;
					copyImporvement = true;
				}
			}
			if (!copyImporvement) {
				if (valueSearch(cell.getValue())) {
					improvement = true;
				}
			}
		}

		return improvement;
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

	protected boolean foundMatchePredicates() {
		boolean found = false;

		if (this.objFun instanceof ComposedPredicateObjectiveFunction) {
			// Get Predicates and states
			ComposedPredicate predicate = (ComposedPredicate) ((ComposedPredicateObjectiveFunction) this.objFun).predicate;

			matches = getMatchPredicates(predicate);
			if (matches.size() > 0) {
				found = true;
				state = ((ComposedPredicateObjectiveFunction) this.objFun).state;
				combained = new Data();
				if (state.getCells().size() > 0)
					combained.appendData(state);
				if (data.getCells().size() > 0)
					combained.appendData(data);
			}
		}

		return found;
	}

	protected boolean copyMove(Cell cell) {
		boolean improvement = false;

		for (Predicate mp : matches) {
			MatchPredicate p = (MatchPredicate) mp;
			String cellColName = cell.getColumn().getName();
			for (Column refCol : p.getMatchingReferenceColumns()) {
				if (refCol.getName().equals(cellColName)) {
					List<Cell> cells = combained.getCells(p.getReferenceTable(), refCol);
					for (Cell c : cells) {
						Value orginalValue = cell.getValue().duplicate();
						cell.setValue(c.getValue());

						improvement = evaluate();
						if (!improvement) {
							cell.setValue(orginalValue);
						} else {
							improvement = true;
							break;
						}

					}
				}
			}

		}

		return improvement;
	}

	/**
	 * Get a list of match predicates
	 * 
	 * @param predicate
	 * @return predicates List of match predicates
	 */
	protected List<Predicate> getMatchPredicatesBAD(Predicate predicate) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (predicate instanceof ComposedPredicate) {
			for (Predicate p : ((ComposedPredicate) predicate).getSubPredicates()) {
				if (p instanceof ComposedPredicate) {
					for (Predicate p1 : ((ComposedPredicate) p).getSubPredicates()) {
						if (p1 instanceof ComposedPredicate) {
							for (Predicate p2 : ((ComposedPredicate) p1).getSubPredicates()) {
								if (p2 instanceof MatchPredicate) {
									predicates.add(p2);
								}
							}
						} else if (p1 instanceof MatchPredicate) {
							predicates.add(p1);
						}
					}
				} else if (p instanceof MatchPredicate) {
					predicates.add(p);
				}
			}
		} else if (predicate instanceof MatchPredicate) {
			predicates.add(predicate);
		}

		return predicates;
	}
}
