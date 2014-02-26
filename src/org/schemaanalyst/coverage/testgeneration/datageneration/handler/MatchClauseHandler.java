package org.schemaanalyst.coverage.testgeneration.datageneration.handler;

import org.schemaanalyst.coverage.criterion.clause.MatchClause;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by phil on 26/02/2014.
 */
public class MatchClauseHandler extends Handler {

    private MatchClause matchClause;
    private Data state;
    private Table table, referenceTable;

    public MatchClauseHandler(MatchClause matchClause, Data state) {
        this.matchClause = matchClause;
        this.state = state;
        this.table = matchClause.getTable();
        this.referenceTable = matchClause.getReferenceTable();
    }

    @Override
    public boolean handle(Data data, boolean fix) {
        boolean adjusted = false;
        List<Row> rows = data.getRows(matchClause.getTable());

        if (rows.size() > 0) {
            ListIterator<Row> rowsIterator = rows.listIterator();

            while (rowsIterator.hasNext()) {
                int index = rowsIterator.nextIndex();
                Row row = rowsIterator.next();
                List<Row> compareRows = getCompareRows(data, index);

                if (compareRows.size() > 0) {

                    for (Row compareRow : compareRows) {
                        boolean result = compareRows(row, compareRow);
                        if (result) {
                            adjusted = true;
                        }
                    }
                }
            }
        }

        return adjusted;
    }

    protected List<Row> getCompareRows(Data data, int index) {
        List<Row> compareRows = data.getRows(matchClause.getReferenceTable());
        if (table.equals(referenceTable)) {
            compareRows = compareRows.subList(0, index);
        }
        compareRows.addAll(state.getRows(matchClause.getReferenceTable()));
        return compareRows;
    }

    protected boolean compareRows(Row row, Row compareRow) {

        evaluateColumnLists(
                row,
                compareRow,
                matchClause.getEqualColumns(),
                matchClause.getEqualRefColumns(),
                true);

        evaluateColumnLists(
                row,
                compareRow,
                matchClause.getNotEqualColumns(),
                matchClause.getNotEqualRefColumns(),
                false);

        return false;
    }

    protected boolean evaluateColumnLists(Row row,
                                          Row compareRow,
                                          List<Column> cols,
                                          List<Column> refCols,
                                          boolean equals) {

        Iterator<Column> colsIterator = cols.iterator();
        Iterator<Column> refColsIterator = refCols.iterator();

        while (colsIterator.hasNext()) {
            Column col = colsIterator.next();
            Column refCol = refColsIterator.next();

            Value colValue = row.getCell(col).getValue();

            if (compareRow.hasColumn(refCol)) {
                Value refColValue = compareRow.getCell(refCol).getValue();

                /*
                compareObjVal =
                        RelationalValueObjectiveFunction.compute(
                                colValue,
                                op,
                                refColValue,
                                true);
                */
            }
        }

        return false;
    }
}
