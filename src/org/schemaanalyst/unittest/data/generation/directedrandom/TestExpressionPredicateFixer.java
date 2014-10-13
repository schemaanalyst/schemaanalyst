package org.schemaanalyst.unittest.data.generation.directedrandom;

import org.junit.Test;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.generation.directedrandom.ExpressionPredicateFixer;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.ExpressionPredicateChecker;
import org.schemaanalyst.unittest.testutil.mock.MockCell;
import org.schemaanalyst.unittest.testutil.mock.MockCellValueGenerator;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by phil on 01/03/2014.
 */
public class TestExpressionPredicateFixer {

    class MockExpressionPredicateChecker extends ExpressionPredicateChecker {

        List<Cell> nonComplyingCells;

        public MockExpressionPredicateChecker(List<Cell> nonComplyingCells) {
            super(null, true, null);
            this.nonComplyingCells = nonComplyingCells;
        }

        public List<Cell> getNonComplyingCells() {
            return nonComplyingCells;
        }
    }

    @Test
    public void testChangeCells() {

        Cell cell1 = new MockCell(new NumericValue(10));
        Cell cell2 = new MockCell(new NumericValue(20));

        MockCellValueGenerator cellValueGenerator = new MockCellValueGenerator(Arrays.asList(500, 1000));

        ExpressionPredicateFixer fixer = new ExpressionPredicateFixer(
                new MockExpressionPredicateChecker(Arrays.asList(cell1, cell2)),
                cellValueGenerator);

        fixer.attemptFix();

        assertEquals(500, ((NumericValue) cell1.getValue()).get().intValue());
        assertEquals(1000, ((NumericValue) cell2.getValue()).get().intValue());
    }
}
