package org.schemaanalyst.coverage.testgeneration.datageneration.checker;

import org.schemaanalyst.coverage.criterion.clause.MatchClause;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.util.tuple.MixedPair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by phil on 27/02/2014.
 */
public class MatchClauseChecker extends ClauseChecker<MatchClause> {

    private MatchClause matchClause;
    private boolean allowNull;
    private Data data, state;
    private boolean compliant;
    private List<MixedPair<Row, List<Row>>> nonMatchingCells;
    private List<Cell> matchingCells;

    public MatchClauseChecker(MatchClause matchClause, boolean allowNull, Data data) {
        this(matchClause, allowNull, data, new Data());
    }

    public MatchClauseChecker(MatchClause matchClause, boolean allowNull, Data data, Data state) {
        this.matchClause = matchClause;
        this.allowNull = allowNull;
        this.data = data;
        this.state = state;
    }

    public MatchClause getClause() {
        return matchClause;
    }

    public List<MixedPair<Row, List<Row>>> getNonMatchingCells() {
        return nonMatchingCells;
    }

    public List<Cell> getMatchingCells() {
        return matchingCells;
    }

    private List<Row> getCompareRows(Data data, int index) {
        List<Row> compareRows = data.getRows(matchClause.getReferenceTable());
        if (matchClause.involvesOneTable()) {
            compareRows = compareRows.subList(0, index);
        }
        compareRows.addAll(state.getRows(matchClause.getReferenceTable()));
        return compareRows;
    }

    @Override
    public boolean check() {
        compliant = true;
        nonMatchingCells = new ArrayList<>();
        matchingCells = new ArrayList<>();

        // do the check
        List<Row> rows = data.getRows(matchClause.getTable());
        ListIterator<Row> rowsIterator = rows.listIterator();

        while (rowsIterator.hasNext()) {
            int index = rowsIterator.nextIndex();
            Row row = rowsIterator.next();
            List<Row> compareRows = getCompareRows(data, index);

            checkMatching(row, compareRows);
            checkNonMatching(row, compareRows);

        }

        return compliant;
    }

    private void checkMatching(Row row, List<Row> compareRows) {

        List<Column> matchingColumns = matchClause.getMatchingColumns();
        List<Column> matchingReferenceColumns = matchClause.getMatchingReferenceColumns();

        List<Row> nonCompliantRows = checkRow(
                row,
                compareRows,
                matchingColumns,
                matchingReferenceColumns,
                true);

        if (nonCompliantRows.size() > 0) {
            Row reducedRow = row.reduceRow(matchingColumns);
            List<Row> reducedNonCompliantRows = new ArrayList<>();
            for (Row nonCompliantRow : nonCompliantRows) {
                reducedNonCompliantRows.add(nonCompliantRow.reduceRow(matchingReferenceColumns));
            }

            nonMatchingCells.add(new MixedPair(reducedRow, reducedNonCompliantRows));
        }
    }


    private void checkNonMatching(Row row, List<Row> compareRows) {

        List<Column> nonMatchingColumns = matchClause.getNonMatchingColumns();
        List<Column> nonMatchingReferenceColumns = matchClause.getNonMatchingReferenceColumns();

        List<Row> nonCompliantRows = checkRow(
                row,
                compareRows,
                nonMatchingColumns,
                nonMatchingReferenceColumns,
                false);

        if (nonCompliantRows.size() > 0) {
            matchingCells.addAll(row.getCells(nonMatchingColumns));
        }
    }

    private List<Row> checkRow(Row row,
                               List<Row> compareRows,
                               List<Column> cols,
                               List<Column> refCols,
                               boolean shouldMatch) {

        List<Row> nonCompliantRows = new ArrayList<>();

        for (Row compareRow : compareRows) {
            Iterator<Column> colsIterator = cols.iterator();
            Iterator<Column> refColsIterator = refCols.iterator();

            boolean allPairingsSatisfied = true;
            boolean onePairingSatisfied = false;

            while (colsIterator.hasNext()) {
                Column col = colsIterator.next();
                Column refCol = refColsIterator.next();

                Cell cell = row.getCell(col);
                Cell refCell = compareRow.getCell(refCol);

                boolean valuesEqual = new RelationalChecker(
                        cell.getValue(),
                        RelationalOperator.EQUALS,
                        refCell.getValue(),
                        allowNull).check();

                boolean thisPairingSatisfied = (shouldMatch != valuesEqual);

                if (thisPairingSatisfied) {
                    onePairingSatisfied = true;
                } else {
                    allPairingsSatisfied = false;
                }
            }

            boolean satisfied =
                    (matchClause.getMode().isAnd() && allPairingsSatisfied) ||
                            (matchClause.getMode().isOr() && onePairingSatisfied);

            if (!satisfied) {
                nonCompliantRows.add(row);
            }
        }

        return nonCompliantRows;
    }
}
