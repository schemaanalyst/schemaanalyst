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
    private String description;
    private boolean goalIsToSatisfy, nullAccepted;
    
    public UniqueObjectiveFunction(List<Column> columns, 
                                   Data state, 
                                   String description,
                                   boolean goalIsToSatisfy, 
                                   boolean nullAccepted) {
        this.columns = columns;
        this.state = state;
        this.description = description;
        this.goalIsToSatisfy = goalIsToSatisfy;
        this.nullAccepted = nullAccepted;
    }

    @Override
    protected ObjectiveValue performEvaluation(Data data) {
        
        List<Row> dataRows = data.getRows(columns);
        List<Row> stateRows = state.getRows(columns);

        // special case for negating and there being one or fewer rows in the data
        // note that we're only interested in the data and its evaluation against
        // itself and the state, values for non-unique issues in the state are ignored
        if (!goalIsToSatisfy && dataRows.size() <= 0) {
            return ObjectiveValue.worstObjectiveValue(description + "(nothing to negate row against)");
        }

        MultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);
        ListIterator<Row> dataRowsIterator = dataRows.listIterator();

        while (dataRowsIterator.hasNext()) {
            Row dataRow = dataRowsIterator.next();

            if (dataRowsIterator.hasNext() || stateRows.size() > 0) {
                String description = "Row " + dataRow;

                MultiObjectiveValue rowObjVal = goalIsToSatisfy
                        ? new SumOfMultiObjectiveValue(description)
                        : new BestOfMultiObjectiveValue(description);

                evaluateRowAgainstOtherRows(rowObjVal, dataRow, dataRows, dataRowsIterator.nextIndex());
                evaluateRowAgainstOtherRows(rowObjVal, dataRow, stateRows, 0);
                
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

        while (rowsIterator.hasNext()) {
            Row compareRow = rowsIterator.next();
            objVal.add(RowRelationalObjectiveFunction.compute(
                    row, !goalIsToSatisfy, compareRow, nullAccepted));
        }
    }    
}
