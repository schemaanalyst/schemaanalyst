package org.schemaanalyst.datageneration.search.objective.data;

import java.util.List;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.row.RowRelationalObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.Column;

public class ReferenceObjectiveFunction extends ConstraintObjectiveFunction {

    private List<Column> columns;
    private List<Column> referenceColumns;
    private Data state;
    private boolean nullAccepted;
    
    public ReferenceObjectiveFunction(List<Column> columns,
                                      List<Column> referenceColumns,
                                      Data state,
                                      String description,
                                      boolean goalIsToSatisfy,
                                      boolean nullAccepted) {
        super(description, goalIsToSatisfy);        
        this.columns = columns;
        this.referenceColumns = referenceColumns;
        this.state = state;
        this.nullAccepted = nullAccepted;
    }
    
    @Override
    protected List<Row> getDataRows(Data data) {
        return data.getRows(columns);
    }    
    
    @Override
    protected ObjectiveValue performEvaluation(List<Row> dataRows) {        
    	// The optimum corresponds to
    	// -- a success (goalIsToSatisfy) on EVERY row, or 
    	// -- a fail (!goalIsToSatisfy) on EVERY row.    	
        MultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);

        // add all reference rows from data and the state
        List<Row> referenceRows = data.getRows(referenceColumns);
        referenceRows.addAll(state.getRows(referenceColumns));

        for (Row dataRow : dataRows) {
            objVal.add(evaluateRow(dataRow, referenceRows, nullAccepted));
        }

        return objVal;
    }

    protected ObjectiveValue evaluateRow(Row row, List<Row> referenceRows, boolean allowNull) {
        String description = "Evaluating row with reference rows";        
        
        MultiObjectiveValue rowObjVal = goalIsToSatisfy
                    ? new BestOfMultiObjectiveValue(description)
                    : new SumOfMultiObjectiveValue(description);

        for (Row referenceRow : referenceRows) {
            rowObjVal.add(RowRelationalObjectiveFunction.compute(
                    row, goalIsToSatisfy, referenceRow, nullAccepted));
        }
        
        System.out.println("checking " + row + " is " + rowObjVal.isOptimal());
        classifyRow(rowObjVal, row);        
        return rowObjVal;
    }
}
