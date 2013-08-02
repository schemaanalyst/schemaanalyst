package org.schemaanalyst.datageneration.search.directedrandom;

import java.util.List;
import java.util.Random;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.datageneration.search.objective.data.UniqueConstraintObjectiveFunction;

public class UniqueConstraintHandler extends ConstraintHandler<UniqueConstraintObjectiveFunction>{
    
    private Random random;
    private CellRandomiser cellRandomiser;

    
    public UniqueConstraintHandler(UniqueConstraintObjectiveFunction objFun, Random random, CellRandomiser cellRandomiser) {
        super(objFun);        
        this.random = random;
        this.cellRandomiser = cellRandomiser;
    }
    
    protected void attemptToSatisfy(List<Row> rows) {
        // try to generate unique values by trying random values        
        for (Row row : rows) {
            cellRandomiser.randomiseCells(row); //objFun.isNullAdmissableForSatisfy()); ??
        }        
    }
    
    protected void attemptToFalsify(List<Row> rows) {
        // the other rows we can pick a non-unique set of values from
        List<Row> otherRows = objFun.getSatisfyingRows();
        otherRows.addAll(objFun.getStateRows());

        // cycle through the unique rows, making them non-unique
        for (Row row : rows) {
            int numOtherRows = otherRows.size();

            if (numOtherRows > 0) {
                int maxIndex = numOtherRows - 1;

                // pick a row at random
                int rowIndex = maxIndex > 0
                        ? random.nextInt(maxIndex)
                        : 0;

                Row otherRow = otherRows.get(rowIndex);

                // copy those values into the row
                row.copyValues(otherRow);
            }
            otherRows.add(row);
        }        
    }    
}
