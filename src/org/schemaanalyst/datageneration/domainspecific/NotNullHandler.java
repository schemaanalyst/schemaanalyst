package org.schemaanalyst.datageneration.domainspecific;

import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.datageneration.analyst.NotNullAnalyst;

public class NotNullHandler extends ConstraintHandler<NotNullAnalyst> {

    public NotNullHandler(NotNullAnalyst analyst,
            boolean goalIsToSatisfy) {
        super(analyst, goalIsToSatisfy);
    }

    protected void attemptToSatisfy() {
        List<Cell> cells = analyst.getNullCells();

        for (Cell cell : cells) {
            cell.setNull(false);
        }
    }

    protected void attemptToFalsify() {
        List<Cell> cells = analyst.getNotNullCells();

        for (Cell cell : cells) {
            cell.setNull(true);
        }
    }
}