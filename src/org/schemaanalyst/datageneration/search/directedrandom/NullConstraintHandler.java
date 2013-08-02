package org.schemaanalyst.datageneration.search.directedrandom;

import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.data.NullConstraintObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.Column;

public class NullConstraintHandler extends ConstraintHandler<NullConstraintObjectiveFunction> {
    
    private Column column;
    
    public NullConstraintHandler(NullConstraintObjectiveFunction objFun) {
        super(objFun);
        column = objFun.getColumn();
    }
    
    protected void attemptToSatisfy(List<Row> rows) {
        handleColumn(rows, true);
    }
    
    protected void attemptToFalsify(List<Row> rows) {
        handleColumn(rows, false);
    }
    
    private void handleColumn(List<Row> rows, boolean setToNull) {
        for (Row row : rows) {
            Cell cell = row.getCell(column);
            cell.setNull(setToNull);
        }
    }    
}
