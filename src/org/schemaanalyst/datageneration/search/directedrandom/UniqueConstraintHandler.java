package org.schemaanalyst.datageneration.search.directedrandom;

import java.util.List;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.datageneration.search.objective.data.UniqueConstraintObjectiveFunction;
import org.schemaanalyst.util.random.Random;

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
            cellRandomiser.randomiseCells(row, objFun.isNullAllowed());
        }        
    }
    
    protected void attemptToFalsify(List<Row> rows) {
        // the only way to make _all_ data rows "falsify" is to set them
        // to values from the state
        List<Row> stateRows = objFun.getStateRows();
        int numStateRows = stateRows.size();
        
        if (numStateRows > 0) {
            int maxIndex = numStateRows - 1;
            
            // cycle through the unique rows, making them non-unique
            for (Row row : rows) {
                
                // pick a row at random
                int rowIndex = random.nextInt(maxIndex);                
                Row stateRow = stateRows.get(rowIndex);

                // copy those values into the row
                row.copyValues(stateRow);
            }
        }  
        
        // it could be that some previously falsifying rows
        // now satisfy because they were non-unique with respect
        // to previous rows in the data rather than the state...
        // ... this is not currently handled in this cycle
        // but will be dealt with in the next.
    }    
}
