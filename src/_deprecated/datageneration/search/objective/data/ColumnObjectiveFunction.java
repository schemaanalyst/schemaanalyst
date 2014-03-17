package _deprecated.datageneration.search.objective.data;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import _deprecated.datageneration.search.objective.MultiObjectiveValue;
import _deprecated.datageneration.search.objective.ObjectiveFunction;
import _deprecated.datageneration.search.objective.ObjectiveValue;
import _deprecated.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.List;

public abstract class ColumnObjectiveFunction extends ObjectiveFunction<Data> {
    
    protected Table table;
    protected List<Column> columns;
    protected String description;
    protected boolean goalIsToSatisfy;
    
    protected List<Row> dataRows;
    protected MultiObjectiveValue objVal;
    protected List<Row> satisfyingRows, falsifyingRows;
    
    public ColumnObjectiveFunction(
            Table table, List<Column> columns, String description, boolean goalIsToSatisfy) {
        this.table = table;
        this.columns = columns;
        this.description = description;
        this.goalIsToSatisfy = goalIsToSatisfy;
        satisfyingRows = new ArrayList<>();
        falsifyingRows = new ArrayList<>();
    }    
    
    public boolean goalIsToSatisfy() {
        return goalIsToSatisfy;
    }
    
    @Override
    public ObjectiveValue evaluate(Data data) {
        satisfyingRows.clear();
        falsifyingRows.clear();
        
        loadRows(data);
        
        if (dataRows.isEmpty()) {
            description +=  "(no data rows)";
            return ObjectiveValue.worstObjectiveValue(description);
        }
        
        // The optimum corresponds to
        // -- a success (goalIsToSatisfy) on EVERY row, or 
        // -- a fail (!goalIsToSatisfy) on EVERY row.   
        // (Partially succeeding or failing rows could leave the database in an
        // inconsistent state unless knowledge about succeeding an failing rows
        // is taken into account.  It is assumed it won't be.)          
        objVal = new SumOfMultiObjectiveValue(description);
        
        for (Row row : dataRows) {
            ObjectiveValue rowObjVal = evaluateRow(row); 
            
            objVal.add(rowObjVal);
            
            boolean satisfying = 
                    (goalIsToSatisfy && rowObjVal.isOptimal()) ||
                    (!goalIsToSatisfy && !rowObjVal.isOptimal());
            
            if (satisfying) {
                satisfyingRows.add(row);
            } else {
                falsifyingRows.add(row);
            }                           
        }

        return objVal;        
    }
    
    protected void loadRows(Data data) {
        dataRows = data.getRows(table, columns);
    }
    
    protected abstract ObjectiveValue evaluateRow(Row row);
    
    public List<Row> getSatisfyingRows() {
        return satisfyingRows;
    }   
    
    public List<Row> getFalsifyingRows() {
        return falsifyingRows;
    }    
}
