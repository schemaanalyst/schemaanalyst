package org.schemaanalyst.datageneration.search.handler;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.datageneration.search.objective.data.ReferenceColumnObjectiveFunction;
import org.schemaanalyst.util.random.Random;

import java.util.List;

public class ReferenceColumnHandler extends ColumnHandler<ReferenceColumnObjectiveFunction> {

    private CellRandomiser cellRandomizer;
    private Random random;
    
    public ReferenceColumnHandler(ReferenceColumnObjectiveFunction objFun,
                            Random random,
                            CellRandomiser cellRandomizer) {
        super(objFun);
        this.random = random;
        this.cellRandomizer = cellRandomizer;
    }

    @Override
    protected void attemptToSatisfy(List<Row> rows) {
        List<Row> referenceRows = objFun.getReferenceRows();
        int numReferenceRows = referenceRows.size();

        if (numReferenceRows > 0) { 
            for (Row row : rows) {                
                int referenceIndex = random.nextInt(numReferenceRows - 1);
                Row referenceRow = referenceRows.get(referenceIndex);
                row.copyValues(referenceRow);
            }
        }
    }
    
    @Override
    protected void attemptToFalsify(List<Row> rows) {
        for (Row row : rows) {
            cellRandomizer.randomiseCells(row, objFun.isNullAllowed());
        }
    }
}