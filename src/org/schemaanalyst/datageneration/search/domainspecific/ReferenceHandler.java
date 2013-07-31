package org.schemaanalyst.datageneration.search.domainspecific;

import java.util.List;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.deprecated.datageneration.analyst.ReferenceAnalyst;
import org.schemaanalyst.util.random.Random;

public class ReferenceHandler extends ConstraintHandler<ReferenceAnalyst> {

    protected boolean allowNull;
    protected CellRandomiser cellRandomizer;
    protected Random random;

    public ReferenceHandler(ReferenceAnalyst analyst,
            boolean goalIsToSatisfy,
            boolean allowNull,
            CellRandomiser cellRandomizer,
            Random random) {
        super(analyst, goalIsToSatisfy);
        this.allowNull = allowNull;
        this.cellRandomizer = cellRandomizer;
        this.random = random;
    }

    @Override
    protected void attemptToSatisfy() {
        List<Row> nonReferencingRows = analyst.getNonReferencingRows();
        List<Row> referenceRows = analyst.getReferenceRows();
        int numReferenceRows = referenceRows.size();

        for (Row row : nonReferencingRows) {
            int referenceIndex = random.nextInt(numReferenceRows - 1);
            Row referenceRow = referenceRows.get(referenceIndex);
            row.copyValues(referenceRow);
        }
    }

    @Override
    protected void attemptToFalsify() {
        List<Row> referencingRows = analyst.getReferencingRows();

        // cycle through the referencing rows, mucking them up
        for (Row row : referencingRows) {
            cellRandomizer.randomizeCells(row);
        }
    }
}