package org.schemaanalyst.deprecated.datageneration.domainspecific;

import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.deprecated.datageneration.analyst.NotNullAnalyst;

public class NotNullHandler extends ConstraintHandler<NotNullAnalyst> {

    public NotNullHandler(NotNullAnalyst analyst,
            boolean goalIsToSatisfy) {
        super(analyst, goalIsToSatisfy);
    }

    @Override
    protected void attemptToSatisfy() {
        List<Cell> cells = analyst.getNullCells();

        for (Cell cell : cells) {
            cell.setNull(false);
        }
    }

    @Override
    protected void attemptToFalsify() {
        List<Cell> cells = analyst.getNotNullCells();

        for (Cell cell : cells) {
            cell.setNull(true);
        }
    }
}