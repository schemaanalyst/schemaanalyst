package org.schemaanalyst.coverage.testgeneration.datageneration.fixer;

import org.schemaanalyst.coverage.testgeneration.datageneration.checker.MatchClauseChecker;
import org.schemaanalyst.coverage.testgeneration.datageneration.checker.MatchRecord;
import org.schemaanalyst.coverage.testgeneration.datageneration.valuegeneration.CellValueGenerator;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.util.random.Random;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by phil on 27/02/2014.
 */
public class MatchClauseFixer extends Fixer {

    private MatchClauseChecker matchClauseChecker;
    private Random random;
    private CellValueGenerator cellValueGenerator;

    public MatchClauseFixer(MatchClauseChecker matchClauseChecker,
                            Random random,
                            CellValueGenerator cellValueGenerator) {
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

            boolean takeSecond = matchRecord.isModifiableRow(randomRowIndex) && random.nextBoolean();

            boolean isOr = matchClauseChecker.getClause().getMode().isOr();
            if (isOr) {
                // if it's an OR MatchClause, we only need to fix one pair of cells
                int randomCellIndex = random.nextInt(originalRow.getNumCells());
                Cell firstCell = originalRow.getCell(randomCellIndex);
                Cell secondCell = alternativeRow.getCell(randomCellIndex);
                fixCells(firstCell, secondCell, takeSecond, attemptMatch);
            } else {
                ListIterator<Cell> originalRowIterator = originalRow.getCells().listIterator();
                ListIterator<Cell> alternativeRowIterator = alternativeRow.getCells().listIterator();

                while (originalRowIterator.hasNext()) {
                    Cell firstCell = originalRowIterator.next();
                    Cell secondCell = alternativeRowIterator.next();
                    fixCells(firstCell, secondCell, takeSecond, attemptMatch);
                }
            }
        }
    }

    private void fixCells(Cell firstCell, Cell secondCell, boolean takeSecond, boolean attemptMatch) {
        if (attemptMatch) {
            matchCells(firstCell, secondCell, takeSecond);
        } else {
            mismatchCells(firstCell, secondCell, takeSecond);
        }
    }

    private void matchCells(Cell firstCell, Cell secondCell, boolean takeSecond) {
        if (takeSecond) {
            secondCell.setValue(firstCell.getValue().duplicate());
        } else {
            firstCell.setValue(secondCell.getValue().duplicate());
        }
    }

    private void mismatchCells(Cell firstCell, Cell secondCell, boolean takeSecond) {
        cellValueGenerator.generateCellValue(takeSecond ? secondCell : firstCell);
    }
}
