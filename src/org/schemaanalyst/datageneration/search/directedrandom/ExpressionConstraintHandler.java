package org.schemaanalyst.datageneration.search.directedrandom;

import java.util.List;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.datageneration.search.objective.data.ExpressionConstraintObjectiveFunction;

public class ExpressionConstraintHandler extends ConstraintHandler<ExpressionConstraintObjectiveFunction> {

    private CellRandomiser cellRandomiser;
    
    public ExpressionConstraintHandler(ExpressionConstraintObjectiveFunction objFun, CellRandomiser cellRandomiser) {
        super(objFun);
        this.cellRandomiser = cellRandomiser;
    }
    
    protected void attemptToSatisfy(List<Row> rows) {
        randomiseRows(rows);
    }
    
    protected void attemptToFalsify(List<Row> rows) {
        randomiseRows(rows);
    }
    
    private void randomiseRows(List<Row> rows) {
        for (Row row : rows) {
            cellRandomiser.randomiseCells(row); //objFun.isNullAdmissableForSatisfy());
        }
    }        
    
}
