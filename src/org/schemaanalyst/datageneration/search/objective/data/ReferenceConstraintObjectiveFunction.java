package org.schemaanalyst.datageneration.search.objective.data;

import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.row.RowRelationalObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.Column;

public class ReferenceConstraintObjectiveFunction extends
        ConstraintObjectiveFunction {

    private List<Column> referenceColumns;
    private Data state;
    private boolean allowNull;

    private List<Row> referenceRows;

    /**
     * Objective function for a reference (typically embedded in a FOREIGN KEY
     * constraint).
     * 
     * @param columns
     *            The columns of the constraint.
     * @param referenceColumns
     *            The reference columns of the constraint.
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
    public ReferenceConstraintObjectiveFunction(List<Column> columns,
            List<Column> referenceColumns, Data state, String description,
            boolean goalIsToSatisfy, boolean allowNull) {

        super(columns, description, goalIsToSatisfy);
        this.referenceColumns = referenceColumns;
        this.state = state;
        this.allowNull = allowNull;
    }

    public boolean isNullAllowed() {
        return allowNull;
    }
    
    public List<Row> getReferenceRows() {
        return referenceRows;
    }

    @Override
    protected void loadRows(Data data) {
        super.loadRows(data);

        // add all reference rows from data and the state
        referenceRows = data.getRows(referenceColumns);
        referenceRows.addAll(state.getRows(referenceColumns));
    }

    @Override
    protected ObjectiveValue evaluateRow(Row row) {
        return (referenceRows.size() > 0) 
                ? evaluateAgainstReferenceRows(row)
                : evaluateDefaultSatisfaction(row);
    }

    private MultiObjectiveValue evaluateAgainstReferenceRows(Row row) {
        String description = "Evaluating row with reference rows";

        MultiObjectiveValue rowObjVal = goalIsToSatisfy ? new BestOfMultiObjectiveValue(
                description) : new SumOfMultiObjectiveValue(description);

        for (Row referenceRow : referenceRows) {
            rowObjVal.add(RowRelationalObjectiveFunction.compute(row,
                    goalIsToSatisfy, referenceRow, allowNull));
        }

        return rowObjVal;
    }

    private ObjectiveValue evaluateDefaultSatisfaction(Row row) {
        String description = "No reference rows - evaluating NULL";

        // There are no reference rows.
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
                // NULL is not involved, it's only a valid solution
                // if the goal is to violate the constraint.
                : !goalIsToSatisfy;
        
        ObjectiveValue rowObjVal = validSolution 
                ? ObjectiveValue.optimalObjectiveValue(description) 
                : ObjectiveValue.worstObjectiveValue(description);

        return rowObjVal;
    }
}
