package org.schemaanalyst.unittest.data.generation.directedrandom;

import org.junit.Test;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.generation.directedrandom.NullPredicateFixer;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.NullPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.NullPredicateChecker;
import org.schemaanalyst.unittest.testutil.mock.MockCell;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by phil on 01/03/2014.
 */
public class TestNullPredicateFixer {

    class MockNullPredicateChecker extends NullPredicateChecker {

        public MockNullPredicateChecker(boolean satisfy, List<Cell> nonComplyingCells) {
            super(new NullPredicate(null, null, satisfy), null);
            this.nonComplyingCells = nonComplyingCells;
        }
    }

    @Test
    public void testSetToNull() {
        Cell cell = new MockCell(new NumericValue(10));

        NullPredicateFixer fixer = new NullPredicateFixer(
                new MockNullPredicateChecker(true, Arrays.asList(cell)));

        fixer.attemptFix();

        assertEquals(null, cell.getValue());
    }

    @Test
    public void testSetToNotNull() {
        Value value = new NumericValue(10);
        Cell cell = new MockCell(value);
        cell.setNull(true);

        NullPredicateFixer fixer = new NullPredicateFixer(
                new MockNullPredicateChecker(false, Arrays.asList(cell)));

        fixer.attemptFix();

        assertEquals(value, cell.getValue());
    }
}
