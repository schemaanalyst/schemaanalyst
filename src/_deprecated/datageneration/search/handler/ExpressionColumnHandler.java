package _deprecated.datageneration.search.handler;

import _deprecated.datageneration.cellrandomisation.CellRandomiser;
import _deprecated.datageneration.search.objective.data.ExpressionColumnObjectiveFunction;
import org.schemaanalyst.data.Row;

import java.util.List;

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
