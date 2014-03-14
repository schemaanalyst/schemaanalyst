package org.schemaanalyst.data.generation.directedrandom.fixer;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.logic.predicate.checker.NullClauseChecker;

/**
 * Created by phil on 27/02/2014.
 */
public class NullClauseFixer extends Fixer {

    private NullClauseChecker nullClauseChecker;

    public NullClauseFixer(NullClauseChecker nullClauseChecker) {
        this.nullClauseChecker = nullClauseChecker;
    }

    @Override
    public void attemptFix() {
        boolean setToNull = nullClauseChecker.getClause().getSatisfy();

        for (Cell cell : nullClauseChecker.getNonComplyingCells()) {
            cell.setNull(setToNull);
        }
    }
}
