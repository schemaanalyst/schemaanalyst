package org.schemaanalyst.data.generation.fixer;

import org.schemaanalyst.data.generation.checker.NullClauseChecker;
import org.schemaanalyst.data.Cell;

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
