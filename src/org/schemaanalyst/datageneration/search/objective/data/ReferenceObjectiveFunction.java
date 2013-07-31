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
    private String description;
    private boolean goalIsToSatisfy, nullAccepted;
    
    public ReferenceObjectiveFunction(List<Column> columns,
                                      List<Column> referenceColumns,
                                      Data state,
                                      String description,
                                      boolean goalIsToSatisfy,
                                      boolean nullAccepted) {
        this.columns = columns;
        this.referenceColumns = referenceColumns;
        this.state = state;
        this.description = description;
        this.goalIsToSatisfy = goalIsToSatisfy;
        this.nullAccepted = nullAccepted;
    }

    @Override
    protected ObjectiveValue performEvaluation(Data data) {        
        List<Row> dataRows = data.getRows(columns);
        
        // special case for negating and there being one or fewer rows in the data
        // note that we're only interested in the data and its evaluation against
        // itself and the state, values for non-unique issues in the state are ignored
        if (!goalIsToSatisfy && dataRows.size() <= 0) {
            return ObjectiveValue.worstObjectiveValue(description + "(nothing to negate row against)");
        }        
        
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
        
        classifyRow(rowObjVal, row);        
        return rowObjVal;
    }
}
