package org.schemaanalyst.coverage.testgeneration.datageneration.handler;

import org.schemaanalyst.coverage.criterion.clause.MatchClause;
import org.schemaanalyst.coverage.testgeneration.datageneration.valuegeneration.CellValueGenerator;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.util.random.Random;
import org.schemaanalyst.util.tuple.Pair;

import java.util.ArrayList;
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
    private Random random;
    private CellValueGenerator cellValueGenerator;

    public MatchClauseHandler(MatchClause matchClause,
                              Data state,
                              Random random,
                              CellValueGenerator cellValueGenerator) {
        this.matchClause = matchClause;
        this.state = state;
        this.table = matchClause.getTable();
        this.referenceTable = matchClause.getReferenceTable();
        this.random = random;
        this.cellValueGenerator = cellValueGenerator;
    }

    @Override
    public boolean handle(Data data, boolean fix) {
        boolean satisfied = true;
        List<Row> rows = data.getRows(matchClause.getTable());

        if (rows.size() > 0) {
            ListIterator<Row> rowsIterator = rows.listIterator();

            while (rowsIterator.hasNext()) {
                int index = rowsIterator.nextIndex();
                Row row = rowsIterator.next();
                List<Row> compareRows = getCompareRows(data, index);

                if (compareRows.size() > 0) {

                    for (Row compareRow : compareRows) {
                        boolean result = compareRows(row, compareRow, fix);
                        if (!result) {
                            satisfied = false;
                        }
                    }
                }
            }
        }

        return satisfied;
    }

    private List<Row> getCompareRows(Data data, int index) {
        List<Row> compareRows = data.getRows(matchClause.getReferenceTable());
        if (table.equals(referenceTable)) {
            compareRows = compareRows.subList(0, index);
        }
        compareRows.addAll(state.getRows(matchClause.getReferenceTable()));
        return compareRows;
    }

    private boolean compareRows(Row row, Row compareRow, boolean fix) {

        boolean satisfied = true;

        boolean result = handleColumnLists(
                row, compareRow,
                matchClause.getMatchingColumns(),
                matchClause.getMatchingReferenceColumns(),
                true, fix);

        if (!result) {
            satisfied = false;
        }

        result = handleColumnLists(
                row, compareRow,
                matchClause.getNonMatchingColumns(),
                matchClause.getNonMatchingReferenceColumns(),
                false, fix);

        if (!result) {
            satisfied = false;
        }

        return satisfied;
    }

    private boolean handleColumnLists(Row row,
                                      Row compareRow,
                                      List<Column> cols,
                                      List<Column> refCols,
                                      boolean shouldBeEqual,
                                      boolean fix) {

        List<Pair<Cell>> unsatisfiedCellPairs = new ArrayList<>();

        Iterator<Column> colsIterator = cols.iterator();
        Iterator<Column> refColsIterator = refCols.iterator();

        while (colsIterator.hasNext()) {
            Column col = colsIterator.next();
            Column refCol = refColsIterator.next();

            Cell cell = row.getCell(col);

            if (compareRow.hasColumn(refCol)) {
                Cell refCell = compareRow.getCell(refCol);

                boolean valuesEqual =
                        RelationalExpressionChecker.check(
                                cell.getValue(), RelationalOperator.EQUALS, refCell.getValue(), true);

                if (shouldBeEqual != valuesEqual) {
                    unsatisfiedCellPairs.add(new Pair<>(cell, refCell));
                }
            }
        }

        boolean satisfied =
                (matchClause.getMode() == MatchClause.Mode.AND && unsatisfiedCellPairs.size() == 0) ||
                (matchClause.getMode() == MatchClause.Mode.OR && unsatisfiedCellPairs.size() != cols.size());

        if (!satisfied && fix) {
            fix(unsatisfiedCellPairs, shouldBeEqual);
        }

        return satisfied;
    }

    private void fix(List<Pair<Cell>> valuePairs, boolean shouldBeEqual) {
        if (matchClause.getMode() == MatchClause.Mode.OR) {
            Pair<Cell> randomPair = valuePairs.get(random.nextInt(valuePairs.size()));
            valuePairs = new ArrayList<>();
            valuePairs.add(randomPair);
        }

        if (shouldBeEqual) {
            makeEqual(valuePairs);
        } else {
            makeNonEqual(valuePairs);
        }
    }

    private void makeEqual(List<Pair<Cell>> valuePairs) {
        for (Pair<Cell> valuePair : valuePairs) {
            valuePair.getFirst().setValue(valuePair.getSecond().getValue().duplicate());
        }
    }

    private void makeNonEqual(List<Pair<Cell>> valuePairs) {
        for (Pair<Cell> valuePair : valuePairs) {
            cellValueGenerator.generateCellValue(valuePair.getFirst());
        }
    }
}
