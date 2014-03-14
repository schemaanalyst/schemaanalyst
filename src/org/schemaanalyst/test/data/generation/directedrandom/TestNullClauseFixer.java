package org.schemaanalyst.test.data.generation.directedrandom;

import org.junit.Test;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.generation.directedrandom.NullClauseFixer;
import org.schemaanalyst.logic.predicate.checker.NullClauseChecker;
import org.schemaanalyst.logic.predicate.clause.NullClause;
import org.schemaanalyst.test.testutil.mock.MockCell;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by phil on 01/03/2014.
 */
public class TestNullClauseFixer {

    class MockNullClauseChecker extends NullClauseChecker {

        public MockNullClauseChecker(boolean satisfy, List<Cell> nonComplyingCells) {
            super(new NullClause(null, null, satisfy), null);
            this.nonComplyingCells = nonComplyingCells;
        }
    }

    @Test
    public void testSetToNull() {
        Cell cell = new MockCell(new NumericValue(10));

        NullClauseFixer fixer = new NullClauseFixer(
                new MockNullClauseChecker(true, Arrays.asList(cell)));

        fixer.attemptFix();

        assertEquals(null, cell.getValue());
    }

    @Test
    public void testSetToNotNull() {
        Value value = new NumericValue(10);
        Cell cell = new MockCell(value);
        cell.setNull(true);

        NullClauseFixer fixer = new NullClauseFixer(
                new MockNullClauseChecker(false, Arrays.asList(cell)));

        fixer.attemptFix();

        assertEquals(value, cell.getValue());
    }
}
