package org.schemaanalyst.test.coverage.testgeneration.datageneration.fixer;

import org.junit.Test;
import org.schemaanalyst.coverage.criterion.clause.MatchClause;
import org.schemaanalyst.coverage.testgeneration.datageneration.checker.MatchClauseChecker;
import org.schemaanalyst.coverage.testgeneration.datageneration.fixer.MatchClauseFixer;
import org.schemaanalyst.coverage.testgeneration.datageneration.valuegeneration.CellValueGenerator;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.test.testutil.mock.MockCell;
import org.schemaanalyst.test.testutil.mock.MockCellValueGenerator;
import org.schemaanalyst.test.testutil.mock.MockRandom;
import org.schemaanalyst.util.random.Random;
import org.schemaanalyst.util.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by phil on 01/03/2014.
 */
public class TestMatchClauseFixer {

    class MockMatchClauseChecker extends MatchClauseChecker {

        MockMatchClauseChecker(MatchClause matchClause,
                                      List<Cell> matchingCells,
                                      List<Pair<Cell>> nonMatchingCells) {
            super(matchClause, false, null);
            this.matchingCells = matchingCells;
            this.nonMatchingCells = nonMatchingCells;
        }
    }

    class MockMatchClause extends MatchClause {

        MockMatchClause(MatchClause.Mode mode) {
            super(new Table("dummytable"),
                    MatchClause.EMPTY_COLUMN_LIST,
                    MatchClause.EMPTY_COLUMN_LIST,
                    mode,
                    false);
        }
    }

    @Test
    public void testMatching() {
        // 1 match
        testMatching(MatchClause.Mode.AND, Arrays.asList(1), Arrays.asList(2), Arrays.asList(2), Arrays.asList(0));
        testMatching(MatchClause.Mode.OR, Arrays.asList(1), Arrays.asList(2), Arrays.asList(2), Arrays.asList(0));

        // multi-match
        testMatching(MatchClause.Mode.AND, Arrays.asList(10, 20), Arrays.asList(30, 40), Arrays.asList(30, 40), Arrays.asList(0));
        testMatching(MatchClause.Mode.OR, Arrays.asList(10, 20), Arrays.asList(10, 40), Arrays.asList(40), Arrays.asList(1));
    }

    public void testMatching(MatchClause.Mode mode,
                             List<Integer> initialValues, List<Integer> changedValues,
                             List<Integer> cellGeneratorValues, List<Integer> randomValues) {

        List<Cell> cells = new ArrayList<>();
        for (int initialValue : initialValues) {
            cells.add(new MockCell(new NumericValue(initialValue)));
        }

        CellValueGenerator cellValueGenerator = new MockCellValueGenerator(cellGeneratorValues);
        Random random = new MockRandom(randomValues);
        MatchClause matchClause = new MockMatchClause(mode);

        MatchClauseFixer fixer = new MatchClauseFixer(
                new MockMatchClauseChecker(
                        matchClause,
                        cells,
                        new ArrayList<Pair<Cell>>()),
                random,
                cellValueGenerator);

        fixer.attemptFix();

        for (int i=0; i < cells.size(); i++) {
            assertEquals((int) changedValues.get(i), ((NumericValue) cells.get(i).getValue()).get().intValue());
        }
    }

    @Test
    public void testNonMatching() {
        // 1 match
        testNonMatching(
                MatchClause.Mode.AND,
                Arrays.asList(1), Arrays.asList(1),
                Arrays.asList(1),
                Arrays.asList(0));
        testNonMatching(
                MatchClause.Mode.OR,
                Arrays.asList(1), Arrays.asList(1),
                Arrays.asList(1),
                Arrays.asList(0));

        // multi-match
        testNonMatching(
                MatchClause.Mode.AND,
                Arrays.asList(100, 200), Arrays.asList(20, 40),
                Arrays.asList(20, 40),
                Arrays.asList(0));

        testNonMatching(
                MatchClause.Mode.OR,
                Arrays.asList(100, 200), Arrays.asList(20, 40),
                Arrays.asList(100, 40),
                Arrays.asList(1));
    }

    public void testNonMatching(MatchClause.Mode mode,
                                List<Integer> initialValues,
                                List<Integer> initialRefValues,
                                List<Integer> changedValues,
                                List<Integer> randomValues) {

        List<Pair<Cell>> cellPairs = new ArrayList<>();
        for (int i=0; i < initialValues.size(); i++) {
            cellPairs.add(new Pair<>(
                    (Cell) new MockCell(new NumericValue(initialValues.get(i))),
                    (Cell) new MockCell(new NumericValue(initialRefValues.get(i)))));
        }

        Random random = new MockRandom(randomValues);
        MatchClause matchClause = new MockMatchClause(mode);

        MatchClauseFixer fixer = new MatchClauseFixer(
                new MockMatchClauseChecker(
                        matchClause,
                        new ArrayList<Cell>(),
                        cellPairs),
                random,
                null);

        fixer.attemptFix();

        for (int i=0; i < cellPairs.size(); i++) {
            Cell firstCell = cellPairs.get(i).getFirst();
            int actualValue = ((NumericValue) firstCell.getValue()).get().intValue();
            int expectedValue = changedValues.get(i);
            assertEquals("For pair no. " + i + " value should be " + expectedValue + " was " + actualValue, expectedValue, actualValue);
        }
    }

}
