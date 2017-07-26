package org.schemaanalyst.data.generation.dravs;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.search.Search;
import org.schemaanalyst.data.generation.search.objective.predicate.MatchPredicateObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.predicate.PredicateObjectiveFunctionFactory;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.MatchPredicateChecker;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.MatchRecord;
import org.schemaanalyst.util.random.Random;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by phil on 27/02/2014.
 */
public class MatchPredicateFixer extends PredicateFixer {

	private MatchPredicateChecker matchPredicateChecker;
	private Random random;
	private RandomCellValueGenerator cellValueGenerator;
	private SearchMini<Data> search;
	private Data state;

	public MatchPredicateFixer(MatchPredicateChecker matchPredicateChecker, Random random,
			RandomCellValueGenerator cellValueGenerator, SearchMini search, Data state) {
		this.matchPredicateChecker = matchPredicateChecker;
		this.random = random;
		this.cellValueGenerator = cellValueGenerator;
		this.search = search;
		this.state = state;
	}

	@Override
	public void attemptFix(int eval) {
		attemptFix(matchPredicateChecker.getNonMatchingCells(), true);
		attemptFix(matchPredicateChecker.getMatchingCells(), false);
	}

	private void attemptFix(List<MatchRecord> matchRecords, boolean attemptMatch) {

		for (MatchRecord matchRecord : matchRecords) {

			Row originalRow = matchRecord.getOriginalRow();

			int randomRowIndex = random.nextInt(matchRecord.getNumComparisonRows());
			Row alternativeRow = matchRecord.getComparisonRow(randomRowIndex);

			boolean modifyAlternativeCell = matchRecord.isModifiableRow(randomRowIndex) && random.nextBoolean();

			boolean isOr = matchPredicateChecker.getPredicate().getMode().isOr();
			if (isOr) {
				// if it's an OR MatchPredicate, we only need to fix one pair of
				// cells
				int randomCellIndex = random.nextInt(originalRow.getNumCells());
				Cell originalCell = originalRow.getCell(randomCellIndex);
				Cell alternativeCell = alternativeRow.getCell(randomCellIndex);
				fixCells(originalCell, alternativeCell, modifyAlternativeCell, attemptMatch);
			} else {
				ListIterator<Cell> originalRowIterator = originalRow.getCells().listIterator();
				ListIterator<Cell> alternativeRowIterator = alternativeRow.getCells().listIterator();

				while (originalRowIterator.hasNext()) {
					Cell originalCell = originalRowIterator.next();
					Cell alternativeCell = alternativeRowIterator.next();
					fixCells(originalCell, alternativeCell, modifyAlternativeCell, attemptMatch);
				}
			}
		}
	}

	private void fixCells(Cell originalCell, Cell alternativeCell, boolean modifyAlternativeCell,
			boolean attemptMatch) {
		if (attemptMatch) {
			matchCells(originalCell, alternativeCell, modifyAlternativeCell);
		} else {
			mismatchCells(originalCell, alternativeCell, modifyAlternativeCell);
		}
	}

	private void matchCells(Cell originalCell, Cell alternativeCell, boolean modifyAlternativeCell) {
		Cell targetCell = modifyAlternativeCell ? alternativeCell : originalCell;
		Cell sourceCell = modifyAlternativeCell ? originalCell : alternativeCell;

		Value value = sourceCell.getValue();
		if (value == null) {
			targetCell.setNull(true);
		} else {
			targetCell.setValue(value.duplicate());
		}
	}

	private void mismatchCells(Cell originalCell, Cell alternativeCell, boolean modifyAlternativeCell) {
		cellValueGenerator.generateCellValue(modifyAlternativeCell ? alternativeCell : originalCell);
		// search.search(null, matchPredicateChecker, modifyAlternativeCell ?
		// alternativeCell : originalCell);
		// search.setObjectiveFunction(PredicateObjectiveFunctionFactory.createObjectiveFunction(matchPredicateChecker.getPredicate(),
		// state));
		// search.setObjectiveFunction(new MatchPredicateObjectiveFunction(matchPredicateChecker.getPredicate(), state));
		// search.initializeInner();
		// search.search(modifyAlternativeCell ? alternativeCell : originalCell, matchPredicateChecker);
	}
}
