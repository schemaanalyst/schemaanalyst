package org.schemaanalyst.datageneration.search.directedrandom;

import java.util.List;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.data.ConstraintObjectiveFunction;

public abstract class ConstraintHandler<T extends ConstraintObjectiveFunction> {
    
    protected T objFun;
    
    public ConstraintHandler(T objFun) {
        this.objFun = objFun;
    }
    
    public void attemptToFindSolution() {
        if (objFun.goalIsToSatisfy()) {
            attemptToSatisfy(objFun.getFalsifyingRows());
        } else {
            attemptToFalsify(objFun.getSatisfyingRows());
        }
    }

    protected abstract void attemptToSatisfy(List<Row> rows);
    
    protected abstract void attemptToFalsify(List<Row> rows);
}
