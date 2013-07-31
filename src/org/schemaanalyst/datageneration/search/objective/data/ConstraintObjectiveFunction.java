package org.schemaanalyst.datageneration.search.objective.data;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;

public abstract class ConstraintObjectiveFunction extends ObjectiveFunction<Data> {
    
    protected List<Row> acceptedRows, rejectedRows; 
    
    @Override
    public ObjectiveValue evaluate(Data data) {
        acceptedRows = new ArrayList<Row>();
        rejectedRows = new ArrayList<Row>();
        return performEvaluation(data);
    }    
    
    protected abstract ObjectiveValue performEvaluation(Data data);
    
    protected void classifyRow(ObjectiveValue objVal, Row row) {
        if (objVal.isOptimal()) {
            acceptedRows.add(row);
        } else {
            rejectedRows.add(row);
        }
    }
    
    public List<Row> getAcceptedRows() {
        return acceptedRows;
    }
    
    public List<Row> getRejectedRows() {
        return rejectedRows;
    }    
}
