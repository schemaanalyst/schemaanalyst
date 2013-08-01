package org.schemaanalyst.datageneration.search.objective.data;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;

public abstract class ConstraintObjectiveFunction extends ObjectiveFunction<Data> {
    
    protected String description;
    protected boolean goalIsToSatisfy;
    protected List<Row> rejectedRows; 
    protected Data data;
    
    public ConstraintObjectiveFunction(String description, boolean goalIsToSatisfy) {
        this.description = description;
        this.goalIsToSatisfy = goalIsToSatisfy;
        rejectedRows = new ArrayList<Row>();
    }
    
    @Override
    public ObjectiveValue evaluate(Data data) {
        // set the data
        this.data = data;
        
        // clear the rejected rows list
        rejectedRows.clear();
        
        // get the rows relevant for this constraint
        List<Row> dataRows = getDataRows(data);
        
        // anything to evaluate?
        if (dataRows.size() <= 0) {
            description +=  "(no data rows)";
            return goalIsToSatisfy 
                    ? ObjectiveValue.optimalObjectiveValue(description)
                    : ObjectiveValue.worstObjectiveValue(description);
        }        
        
        // do the evaluation
        return performEvaluation(dataRows);
    }    
    
    protected abstract List<Row> getDataRows(Data data);
    
    protected abstract ObjectiveValue performEvaluation(List<Row> dataRows);
    
    protected void classifyRow(ObjectiveValue objVal, Row row) {
        if (!objVal.isOptimal()) {
            rejectedRows.add(row);
        }
    }
    
    public List<Row> getRejectedRows() {
        return rejectedRows;
    }    
}
