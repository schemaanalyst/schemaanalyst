package org.schemaanalyst.datageneration.search.handler;

import java.util.List;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.datageneration.search.objective.data.ExpressionColumnObjectiveFunction;

public class ExpressionColumnHandler extends ColumnHandler<ExpressionColumnObjectiveFunction> {

    private CellRandomiser cellRandomiser;
    
    public ExpressionColumnHandler(ExpressionColumnObjectiveFunction objFun, CellRandomiser cellRandomiser) {
        super(objFun);
        this.cellRandomiser = cellRandomiser;
    }
    
    protected void attemptToSatisfy(List<Row> rows) {
        randomiseRowValues(rows);
    }
    
    protected void attemptToFalsify(List<Row> rows) {
        randomiseRowValues(rows);
    }

    private void randomiseRowValues(List<Row> rows) {
        for (Row row : rows) {
            cellRandomiser.randomiseCells(row, objFun.isNullAllowed());
        }
    }
}
