package org.schemaanalyst.coverage.testgeneration.datageneration.checker;

import org.schemaanalyst.coverage.criterion.clause.MatchClause;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.util.tuple.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by phil on 27/02/2014.
 */
public class MatchClauseChecker extends Checker {

    private MatchClause matchClause;
    private Data data, state;
    private List<Pair<Cell>> nonMatchingCells;
    private List<Cell> matchingCells;

    public MatchClauseChecker(MatchClause matchClause, Data data) {
        this(matchClause, data, new Data());
    }

    public MatchClauseChecker(MatchClause matchClause, Data data, Data state) {
        this.matchClause = matchClause;
        this.data = data;
        this.state = state;
    }

    public MatchClause getMatchClause() {
        return matchClause;
    }

    public List<Pair<Cell>> getNonMatchingCells() {
        return nonMatchingCells;
    }

    public List<Cell> getMatchingCells() {
        return matchingCells;
    }

    @Override
    public boolean check() {
        nonMatchingCells = new ArrayList<>();
        matchingCells = new ArrayList<>();

        // do the check
        List<Row> rows = data.getRows(matchClause.getTable());
        ListIterator<Row> rowsIterator = rows.listIterator();

        while (rowsIterator.hasNext()) {
            int index = rowsIterator.nextIndex();
            Row row = rowsIterator.next();
            List<Row> compareRows = getCompareRows(data, index);

            for (Row compareRow : compareRows) {
                checkRows(row, compareRow);
            }
        }

        return (nonMatchingCells.size() == 0 && matchingCells.size() == 0);
    }

    private List<Row> getCompareRows(Data data, int index) {
        List<Row> compareRows = data.getRows(matchClause.getReferenceTable());
        if (matchClause.involvesOneTable()) {
            compareRows = compareRows.subList(0, index);
        }
        compareRows.addAll(state.getRows(matchClause.getReferenceTable()));
        return compareRows;
    }

    private void checkRows(Row row, Row compareRow) {

        checkColumnLists(
                row, compareRow,
                matchClause.getMatchingColumns(),
                matchClause.getMatchingReferenceColumns(),
                true);

        checkColumnLists(
                row, compareRow,
                matchClause.getNonMatchingColumns(),
                matchClause.getNonMatchingReferenceColumns(),
                false);
    }

    private void checkColumnLists(Row row,
                                  Row compareRow,
                                  List<Column> cols,
                                  List<Column> refCols,
                                  boolean shouldMatch) {

        Iterator<Column> colsIterator = cols.iterator();
        Iterator<Column> refColsIterator = refCols.iterator();

        while (colsIterator.hasNext()) {
            Column col = colsIterator.next();
            Column refCol = refColsIterator.next();

            Cell cell = row.getCell(col);

            if (compareRow.hasColumn(refCol)) {
                Cell refCell = compareRow.getCell(refCol);

                boolean valuesEqual = new RelationalChecker(
                        cell.getValue(), RelationalOperator.EQUALS, refCell.getValue(), true).check();

                if (shouldMatch != valuesEqual) {
                    if (shouldMatch) {
                        nonMatchingCells.add(new Pair<>(cell, refCell));
                    } else {
                        matchingCells.add(cell);
                    }
                }
            }
        }
    }
}
