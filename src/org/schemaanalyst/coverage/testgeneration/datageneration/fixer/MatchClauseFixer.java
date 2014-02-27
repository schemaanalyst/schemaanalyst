package org.schemaanalyst.coverage.testgeneration.datageneration.fixer;

import org.schemaanalyst.coverage.criterion.clause.MatchClause;
import org.schemaanalyst.coverage.testgeneration.datageneration.checker.MatchClauseChecker;
import org.schemaanalyst.coverage.testgeneration.datageneration.valuegeneration.CellValueGenerator;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.util.random.Random;
import org.schemaanalyst.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 27/02/2014.
 */
public class MatchClauseFixer extends Fixer {

    private MatchClauseChecker matchClauseChecker;
    private Random random;
    private CellValueGenerator cellValueGenerator;

    public MatchClauseFixer(MatchClauseChecker matchClauseChecker, Random random, CellValueGenerator cellValueGenerator) {
        this.matchClauseChecker = matchClauseChecker;
        this.random = random;
        this.cellValueGenerator = cellValueGenerator;
    }

    @Override
    public void attemptFix() {
        boolean isOr = matchClauseChecker.getMatchClause().getMode().isOr();

        attemptFixNonMatchingCells(isOr);
        attemptFixMatchingCells(isOr);
    }

    private void attemptFixNonMatchingCells(boolean isOr) {
        List<Pair<Cell>> nonMatchingCells = matchClauseChecker.getNonMatchingCells();

        if (isOr && nonMatchingCells.size() > 1) {
            Pair<Cell> randomPair = nonMatchingCells.get(random.nextInt(nonMatchingCells.size()));
            nonMatchingCells = new ArrayList<>();
            nonMatchingCells.add(randomPair);
        }

        for (Pair<Cell> cellPair : nonMatchingCells) {
            cellPair.getFirst().setValue(cellPair.getSecond().getValue().duplicate());
        }
    }

    private void attemptFixMatchingCells(boolean isOr) {
        List<Cell> matchingCells = matchClauseChecker.getMatchingCells();

        if (isOr && matchingCells.size() > 1) {
            Cell randomCell = matchingCells.get(random.nextInt(matchingCells.size()));
            matchingCells = new ArrayList<>();
            matchingCells.add(randomCell);
        }

        for (Cell cell : matchingCells) {
            cellValueGenerator.generateCellValue(cell);
        }
    }
}
