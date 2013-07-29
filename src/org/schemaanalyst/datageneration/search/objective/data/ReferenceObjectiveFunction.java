package org.schemaanalyst.datageneration.search.objective.data;

import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.value.NullValueObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;

import static org.schemaanalyst.logic.RelationalOperator.EQUALS;
import static org.schemaanalyst.logic.RelationalOperator.NOT_EQUALS;

public class ReferenceObjectiveFunction extends ObjectiveFunction<Data> {

    protected List<Column> columns;
    protected List<Column> referenceColumns;
    protected RelationalOperator op;
    protected Data state;
    protected String description;
    protected boolean goalIsToSatisfy, allowNull;

    public ReferenceObjectiveFunction(List<Column> columns,
            List<Column> referenceColumns,
            Data state,
            String description,
            boolean goalIsToSatisfy,
            boolean allowNull) {
        this.columns = columns;
        this.referenceColumns = referenceColumns;
        this.state = state;
        this.description = description;
        this.goalIsToSatisfy = goalIsToSatisfy;
        this.allowNull = allowNull;

        this.op = goalIsToSatisfy ? EQUALS : NOT_EQUALS;
    }

    @Override
    public ObjectiveValue evaluate(Data data) {
        MultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);

        List<List<Cell>> rows = data.getCells(columns);

        // add all reference rows from data and the state
        List<List<Cell>> referenceRows = data.getCells(referenceColumns);
        referenceRows.addAll(state.getCells(referenceColumns));

        for (List<Cell> row : rows) {
            objVal.add(evaluateRow(row, referenceRows, allowNull));
        }

        return objVal;
    }

    protected ObjectiveValue evaluateRow(List<Cell> row, List<List<Cell>> referenceRows, boolean allowNull) {

        MultiObjectiveValue rowObjVal;

        if (allowNull) {
            rowObjVal = new BestOfMultiObjectiveValue("Allowing for nulls");
            rowObjVal.add(evaluateRow(row, referenceRows, false));

            for (Cell cell : row) {
                rowObjVal.add(NullValueObjectiveFunction.compute(cell.getValue(), true));
            }
        } else {
            String description = "Evaluating row with reference rows";

            rowObjVal = goalIsToSatisfy
                    ? new BestOfMultiObjectiveValue(description)
                    : new SumOfMultiObjectiveValue(description);

            for (List<Cell> referenceRow : referenceRows) {
                rowObjVal.add(CellListObjectiveFunction.compute(row, op, referenceRow));
            }
        }

        return rowObjVal;
    }
}
