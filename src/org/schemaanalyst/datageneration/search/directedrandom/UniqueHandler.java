package org.schemaanalyst.datageneration.search.directedrandom;

import java.util.List;
import java.util.Random;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.datageneration.search.objective.data.UniqueObjectiveFunction;

public class UniqueHandler {
    
    UniqueObjectiveFunction objFun;
    Random random;
    CellRandomiser randomiser;
    
    public UniqueHandler(UniqueObjectiveFunction objFun, Random random, CellRandomiser randomiser) {
        this.objFun = objFun;
        this.random = random;
        this.randomiser = randomiser;
    }

    protected void attemptToSatisfy() {
        List<Row> nonUniqueRows = objFun.getFalsifyingRows();

        for (Row row : nonUniqueRows) {
            randomiser.randomiseCells(row, objFun.isNullAdmissableForSatisfy());
        }        
    }

    protected void attemptToFalsify() {
        List<Row> uniqueRows = objFun.getSatisfyingRows();

        // the other rows we can pick a non-unique set of values from
        List<Row> otherRows = objFun.getFalsifyingRows();
        otherRows.addAll(objFun.getStateRows());

        // cycle through the unique rows, making them non-unique
        for (Row row : uniqueRows) {
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
