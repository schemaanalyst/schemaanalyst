package org.schemaanalyst.coverage.testgeneration.datageneration.fixer;

import org.schemaanalyst.coverage.testgeneration.datageneration.checker.MatchClauseChecker;
import org.schemaanalyst.coverage.testgeneration.datageneration.valuegeneration.CellValueGenerator;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.util.random.Random;
import org.schemaanalyst.util.tuple.MixedPair;
import org.schemaanalyst.util.tuple.Pair;

import java.util.ArrayList;
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
        boolean isOr = matchClauseChecker.getClause().getMode().isOr();

        attemptFixNonMatchingCells(isOr);
        attemptFixMatchingCells(isOr);
    }

    private void attemptFixNonMatchingCells(boolean isOr) {
        boolean isAnd = !isOr;
        List<MixedPair<Row, List<Row>>> nonMatchingCells = matchClauseChecker.getNonMatchingCells();

        for (MixedPair<Row, List<Row>> pair: nonMatchingCells) {
            // get the initial row
            Row row = pair.getFirst();

            // get the reference row
            List<Row> alternativeRows = pair.getSecond();
            Row alternativeRow = alternativeRows.get(random.nextInt(alternativeRows.size()));

            // if it's an OR MatchClause, we only need to fix up one pair of cells
            // so work out a random index
            int orIndex = -1;
            if (isOr) {
                orIndex = random.nextInt(row.getNumCells());
            }

            ListIterator<Cell> cellIterator = row.getCells().listIterator();
            ListIterator<Cell> alternativeCellIterator = alternativeRow.getCells().listIterator();

            while (cellIterator.hasNext()) {
                boolean matchCells = isAnd || orIndex == cellIterator.nextIndex();

                Cell firstCell = cellIterator.next();
                Cell secondCell = alternativeCellIterator.next();

                if (matchCells) {
                    firstCell.setValue(secondCell.getValue().duplicate());
                }
            }
        }
    }

    private void attemptFixMatchingCells(boolean isOr) {
        List<Cell> matchingCells = matchClauseChecker.getMatchingCells();

        if (isOr && matchingCells.size() > 1) {
            int randomCellIndex = random.nextInt(matchingCells.size());

            Cell randomCell = matchingCells.get(randomCellIndex);
            matchingCells = new ArrayList<>();
            matchingCells.add(randomCell);
        }

        for (Cell cell : matchingCells) {
            cellValueGenerator.generateCellValue(cell);
        }
    }
}
