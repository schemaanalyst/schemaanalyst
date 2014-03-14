package org.schemaanalyst._deprecated.datageneration.search.handler;

import org.schemaanalyst.data.Row;
import org.schemaanalyst._deprecated.datageneration.search.objective.data.NullColumnObjectiveFunction;

import java.util.List;

public class NullColumnHandler extends ColumnHandler<NullColumnObjectiveFunction> {
    
    public NullColumnHandler(NullColumnObjectiveFunction objFun) {
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
