package org.schemaanalyst.datageneration.search.directedrandom.data;

import java.util.List;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.data.ColumnObjectiveFunction;

public abstract class ColumnHandler<T extends ColumnObjectiveFunction> {
    
    protected T objFun;
    
    public ColumnHandler(T objFun) {
        this.objFun = objFun;
    }
    
    public void attemptToFindSolution(Data data) {
        if (!objFun.evaluate(data).isOptimal()) {       
            if (objFun.goalIsToSatisfy()) {
                attemptToSatisfy(objFun.getFalsifyingRows());
            } else {
                attemptToFalsify(objFun.getSatisfyingRows());
            }
        }
    }

    protected abstract void attemptToSatisfy(List<Row> rows);
    
    protected abstract void attemptToFalsify(List<Row> rows);
}
