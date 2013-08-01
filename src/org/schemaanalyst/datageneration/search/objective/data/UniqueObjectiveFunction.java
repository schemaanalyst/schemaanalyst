package org.schemaanalyst.datageneration.search.objective.data;

import java.util.List;
import java.util.ListIterator;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.row.RowRelationalObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.Column;

public class UniqueObjectiveFunction extends ConstraintObjectiveFunction {

    private List<Column> columns;
    private Data state;
    private boolean nullAccepted;
    
    public UniqueObjectiveFunction(List<Column> columns, 
                                   Data state, 
                                   String description,
                                   boolean goalIsToSatisfy, 
                                   boolean nullAccepted) {
        super(description, goalIsToSatisfy);
        this.columns = columns;
        this.state = state;
        this.nullAccepted = nullAccepted;
    }
    
    @Override
    protected List<Row> getDataRows(Data data) {
        return data.getRows(columns);
    }

    @Override
    protected ObjectiveValue performEvaluation(List<Row> dataRows) {
        
        List<Row> stateRows = state.getRows(columns);

        // The optimum corresponds to
        // -- a success (goalIsToSatisfy) on EVERY row, or 
        // -- a fail (!goalIsToSatisfy) on EVERY row.   
        // (Partially succeeding or failing rows could leave the database in an
        // inconsistent state unless knowledge about succeeding an failing rows
        // is taken into account.  It is assumed it won't be.)        
        MultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);
                
        ListIterator<Row> dataRowsIterator = dataRows.listIterator(dataRows.size());
        boolean haveStateRows = stateRows.size() > 0;
        
        // we work backwards up the list of rows in the data to see if it
        // clashes with an earlier row (order matters with uniqueness of INSERTs).
        while (dataRowsIterator.hasPrevious()) {
            Row dataRow = dataRowsIterator.previous();
            boolean haveDataRows = dataRowsIterator.hasPrevious();
            
            if (haveDataRows || haveStateRows) {
                String description = "Row " + dataRow;

                MultiObjectiveValue rowObjVal = goalIsToSatisfy
                        ? new SumOfMultiObjectiveValue(description)
                        : new BestOfMultiObjectiveValue(description);

                // evaluate against previous data rows
                evaluateRowAgainstOtherRows(rowObjVal, dataRow, dataRows, dataRowsIterator.nextIndex());

                // evaluate against state rows
                evaluateRowAgainstOtherRows(rowObjVal, dataRow, stateRows, stateRows.size());
                
                objVal.add(rowObjVal);
                classifyRow(rowObjVal, dataRow);
            }
        }

        return objVal;
    }

    private void evaluateRowAgainstOtherRows(
            MultiObjectiveValue objVal,
            Row row, List<Row> otherRows, int fromIndex) {
        
        ListIterator<Row> rowsIterator = otherRows.listIterator(fromIndex);

        while (rowsIterator.hasPrevious()) {
            Row compareRow = rowsIterator.previous();
            objVal.add(RowRelationalObjectiveFunction.compute(
                    row, !goalIsToSatisfy, compareRow, nullAccepted));
        }
    }    
    
    protected void classifyRow(ObjectiveValue objVal, Row row) {
        if (!objVal.isOptimal()) {
            // always add rejected rows at the beginning of the 
            // list since we're traversing in reverse order
            rejectedRows.add(0, row);
        }
    }    
}
