package org.schemaanalyst.datageneration.search.directedrandom;

import java.util.List;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.data.ConstraintObjectiveFunction;

public abstract class ConstraintHandler<T extends ConstraintObjectiveFunction> {

    protected T objFun;
    
    public ConstraintHandler(T objFun) {
        this.objFun = objFun;
    }
        
    public boolean attemptToFulfill(Data data) {
        ObjectiveValue objVal = objFun.evaluate(data);
        
        if (!objVal.isOptimal()) {
            List<Row> rejectedRows = objFun.getFalsifyingRows();
            if (objFun.goalIsToSatisfy()) {
                attemptToSatisfy(rejectedRows);
            } else {
                attemptToFalsify(rejectedRows);
            }
        }
        
        return objFun.evaluate(data).isOptimal();
    }
    
    protected abstract void attemptToSatisfy(List<Row> rejectedRows);
    
    protected abstract void attemptToFalsify(List<Row> rejectedRows);
}
