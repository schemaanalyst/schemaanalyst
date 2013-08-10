package org.schemaanalyst.datageneration.search.directedrandom;

import java.util.List;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.data.NullConstraintObjectiveFunction;

public class NullConstraintHandler extends ConstraintHandler<NullConstraintObjectiveFunction> {
    
    public NullConstraintHandler(NullConstraintObjectiveFunction objFun) {
        super(objFun);
    }
    
    protected void attemptToSatisfy(List<Row> rows) {
        setRowsToNull(rows, true);
    }
    
    protected void attemptToFalsify(List<Row> rows) {
        setRowsToNull(rows, false);
    }

    private void setRowsToNull(List<Row> rows, boolean setToNull) {
        for (Row row : rows) {
            row.getCells().get(0).setNull(setToNull);
        }
    }       
}
