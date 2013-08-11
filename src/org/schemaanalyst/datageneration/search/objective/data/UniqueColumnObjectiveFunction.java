package org.schemaanalyst.datageneration.search.objective.data;

import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.row.RelationalRowObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.Column;

public class UniqueColumnObjectiveFunction extends ColumnObjectiveFunction {

    private Data state;
    private boolean allowNull;

    private List<Row> stateRows;

    /**
     * Objective function for a set of unique columns (typically embedded in a
     * PRIMARY KEY or UNIQUE constraint).
     * 
     * @param columns
     *            The columns of the constraint.
     * @param state
     *            A data object corresponding to the current state of the
     *            database (i.e., data already committed).
     * @param description
     *            Some descriptive text to describe the objective function.
     * @param goalIsToSatisfy
     *            If set to true, the goal of the objective function will be to
     *            produce rows that all satisfy the constraint, else the goal is
     *            to produce rows that all falsify the constraint.
     * @param allowNull
     *            If set to true, NULL values are permissible as part or all of
     *            a solution.
     */
    public UniqueColumnObjectiveFunction(List<Column> columns, Data state,
            String description, boolean goalIsToSatisfy,
            boolean allowNull) {
        super(columns, description, goalIsToSatisfy);
        this.state = state;
        this.allowNull = allowNull;
    }

    public boolean isNullAllowed() {
        return allowNull;
    }
        
    public List<Row> getStateRows() {
        return stateRows;
    }
    
    @Override
    protected void loadRows(Data data) {
        super.loadRows(data);
        stateRows = state.getRows(columns);
    }

    @Override
    protected ObjectiveValue evaluateRow(Row row) {

        boolean haveRowsToCompareWith = 
                (satisfyingRows.size() > 0 || stateRows.size() > 0);

        return haveRowsToCompareWith 
                ? evaluateUniqueness(row)
                : evaluateDefaultSatisfaction(row);
    }

    private ObjectiveValue evaluateDefaultSatisfaction(Row row) {
        // This is the only row -- no accepted rows (so far) and no state.
        // All we need to do is check for null values ...

        boolean involvesNull = false;
        for (Cell cell : row.getCells()) {
            if (cell.getValue() == null) {
                involvesNull = true;
            }
        }

        boolean validSolution 
        = involvesNull
            // if NULL is involved, it's only a valid solution
            // if NULL is allowed.
            ? allowNull
            // NULL is not involved, it's a valid solution
            // if the goal is to satisfy the constraint since it's
            // the only row.
            : goalIsToSatisfy;
    
    ObjectiveValue rowObjVal = validSolution 
            ? ObjectiveValue.optimalObjectiveValue(description) 
            : ObjectiveValue.worstObjectiveValue(description);

        return rowObjVal;
    }

    private ObjectiveValue evaluateUniqueness(Row row) {
        String description = "No rows to compare with - evaluating NULL";

        MultiObjectiveValue rowObjVal = goalIsToSatisfy ? new SumOfMultiObjectiveValue(
                description) : new BestOfMultiObjectiveValue(description);

        // evaluate against previously accepted data rows
        evaluateRowAgainstOtherRows(rowObjVal, row, satisfyingRows);

        // evaluate against state rows
        evaluateRowAgainstOtherRows(rowObjVal, row, stateRows);

        return rowObjVal;
    }

    private void evaluateRowAgainstOtherRows(MultiObjectiveValue objVal,
            Row row, List<Row> compareRows) {

        for (Row compareRow : compareRows) {
            objVal.add(RelationalRowObjectiveFunction.compute(row,
                    !goalIsToSatisfy, compareRow, allowNull));
        }
    }
}
