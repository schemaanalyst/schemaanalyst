package org.schemaanalyst.data.generation.directedrandom;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.logic.predicate.checker.MatchClauseChecker;
import org.schemaanalyst.logic.predicate.checker.MatchRecord;
import org.schemaanalyst.util.random.Random;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by phil on 27/02/2014.
 */
public class MatchClauseFixer extends Fixer {

    private MatchClauseChecker matchClauseChecker;
    private Random random;
    private RandomCellValueGenerator cellValueGenerator;

    public MatchClauseFixer(MatchClauseChecker matchClauseChecker,
                            Random random,
                            RandomCellValueGenerator cellValueGenerator) {
        this.matchClauseChecker = matchClauseChecker;
        this.random = random;
        this.cellValueGenerator = cellValueGenerator;
    }

    @Override
    public void attemptFix() {
        attemptFix(matchClauseChecker.getNonMatchingCells(), true);
        attemptFix(matchClauseChecker.getMatchingCells(), false);
    }

    private void attemptFix(List<MatchRecord> matchRecords, boolean attemptMatch) {

        for (MatchRecord matchRecord : matchRecords) {

            Row originalRow = matchRecord.getOriginalRow();

            int randomRowIndex = random.nextInt(matchRecord.getNumComparisonRows());
            Row alternativeRow = matchRecord.getComparisonRow(randomRowIndex);

            boolean modifyAlternativeCell = matchRecord.isModifiableRow(randomRowIndex) && random.nextBoolean();

            boolean isOr = matchClauseChecker.getClause().getMode().isOr();
            if (isOr) {
                // if it's an OR MatchClause, we only need to fix one pair of cells
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

    private void fixCells(Cell originalCell, Cell alternativeCell, boolean modifyAlternativeCell, boolean attemptMatch) {
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
    }
}
