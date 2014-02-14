package org.schemaanalyst.coverage.search.objectivefunction;

import org.schemaanalyst.coverage.criterion.clause.MatchClause;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.*;
import org.schemaanalyst.datageneration.search.objective.value.RelationalValueObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by phil on 24/01/2014.
 */
public class MatchObjectiveFunction extends ObjectiveFunction<Data> {

    private MatchClause matchClause;
    private Data state;
    private Table table, referenceTable;

    public MatchObjectiveFunction(MatchClause matchClause, Data state) {
        this.matchClause = matchClause;
        this.state = state;
        this.table = matchClause.getTable();
        this.referenceTable = matchClause.getReferenceTable();
    }

    @Override
    public ObjectiveValue evaluate(Data data) {
        String description = matchClause.toString();
        List<Row> rows = data.getRows(matchClause.getTable());

        if (rows.size() > 0) {
            SumOfMultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);
            ListIterator<Row> rowsIterator = rows.listIterator();

            while (rowsIterator.hasNext()) {
                int index = rowsIterator.nextIndex();
                Row row = rowsIterator.next();
                List<Row> compareRows = getCompareRows(data, index);

                if (compareRows.size() > 0) {
                    BestOfMultiObjectiveValue rowObjVal = new BestOfMultiObjectiveValue();

                    for (Row compareRow : compareRows) {
                        rowObjVal.add(compareRows(row, compareRow));
                    }

                    objVal.add(rowObjVal);
                }
            }

            return objVal;
        }

        return ObjectiveValue.worstObjectiveValue(description);
    }

    protected List<Row> getCompareRows(Data data, int index) {
        List<Row> compareRows = data.getRows(matchClause.getReferenceTable());
        if (table.equals(referenceTable)) {
            compareRows = compareRows.subList(0, index);
        }
        compareRows.addAll(state.getRows(matchClause.getReferenceTable()));
        return compareRows;
    }

    protected ObjectiveValue compareRows(Row row, Row compareRow) {
        MultiObjectiveValue objVal =
                matchClause.getMode() == MatchClause.Mode.AND
                        ? new SumOfMultiObjectiveValue()
                        : new BestOfMultiObjectiveValue();

        evaluateColumnLists(
                row,
                compareRow,
                matchClause.getEqualColumns(),
                matchClause.getEqualRefColumns(),
                RelationalOperator.EQUALS,
                objVal);

        evaluateColumnLists(
                row,
                compareRow,
                matchClause.getNotEqualColumns(),
                matchClause.getNotEqualRefColumns(),
                RelationalOperator.NOT_EQUALS,
                objVal);

        return objVal;
    }

    protected void evaluateColumnLists(Row row,
                                       Row stateRow,
                                       List<Column> cols,
                                       List<Column> refCols,
                                       RelationalOperator op,
                                       MultiObjectiveValue objVal) {

        Iterator<Column> colsIterator = cols.iterator();
        Iterator<Column> refColsIterator = refCols.iterator();

        while (colsIterator.hasNext()) {
            Column col = colsIterator.next();
            Column refCol = refColsIterator.next();

            Value colValue = row.getCell(col).getValue();

            ObjectiveValue compareObjVal = ObjectiveValue.worstObjectiveValue();
            if (stateRow.hasColumn(refCol)) {
                Value refColValue = stateRow.getCell(refCol).getValue();

                compareObjVal =
                        RelationalValueObjectiveFunction.compute(
                                colValue,
                                op,
                                refColValue,
                                true);
            }

            objVal.add(compareObjVal);
        }
    }
}
