package org.schemaanalyst.coverage.testgeneration.datageneration.checker;

import org.schemaanalyst.coverage.criterion.clause.MatchClause;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;

import java.util.*;

/**
 * Created by phil on 27/02/2014.
 */
public class MatchClauseChecker extends ClauseChecker<MatchClause> {

    private MatchClause matchClause;
    private boolean allowNull;
    private Data data, state;
    private List<MatchRecord> nonMatchingCells;
    private List<MatchRecord> matchingCells;

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

    public List<MatchRecord> getNonMatchingCells() {
        return nonMatchingCells;
    }

    public List<MatchRecord> getMatchingCells() {
        return matchingCells;
    }

    private List<Row> getDataRows(int index) {
        List<Row> compareRows = data.getRows(matchClause.getReferenceTable());
        if (matchClause.involvesOneTable()) {
            compareRows = compareRows.subList(0, index);
        }
        return compareRows;
    }

    private List<Row> getStateRows() {
        return state.getRows(matchClause.getReferenceTable());
    }

    @Override
    public boolean check() {

        nonMatchingCells = new ArrayList<>();
        matchingCells = new ArrayList<>();

        List<Row> rows = data.getRows(matchClause.getTable());

        if (rows.size() > 0) {
            boolean compareRowAlwaysPresent = true;
            ListIterator<Row> rowsIterator = rows.listIterator();

            while (rowsIterator.hasNext()) {
                Row row = rowsIterator.next();

                List<Row> dataRows = getDataRows(rowsIterator.nextIndex() - 1);
                List<Row> stateRows = getStateRows();
                int numCompareRows = dataRows.size() + stateRows.size();
                if (numCompareRows == 0) {
                    if (matchClause.requiresComparisonRow()) {
                        compareRowAlwaysPresent = false;
                    }
                } else {
                    checkMatching(row, dataRows, stateRows);
                    checkNonMatching(row, dataRows, stateRows);
                }
            }

            return compareRowAlwaysPresent && nonMatchingCells.size() == 0 && matchingCells.size() == 0;
        }

        return false;
    }

    private void checkMatching(Row row, List<Row> dataRows, List<Row> stateRows) {
        MatchRecord matchRecord = checkRows(
                row,
                dataRows,
                stateRows,
                matchClause.getMatchingColumns(),
                matchClause.getMatchingReferenceColumns(),
                true);
        if (matchRecord != null) {
            nonMatchingCells.add(matchRecord);
        }
    }

    private void checkNonMatching(Row row, List<Row> dataRows, List<Row> stateRows) {
        MatchRecord matchRecord = checkRows(
                row,
                dataRows,
                stateRows,
                matchClause.getNonMatchingColumns(),
                matchClause.getNonMatchingReferenceColumns(),
                false);
        if (matchRecord != null) {
            matchingCells.add(matchRecord);
        }
    }

    private MatchRecord checkRows(Row row,
                                  List<Row> dataRows,
                                  List<Row> stateRows,
                                  List<Column> columns,
                                  List<Column> referenceColumns,
                                  boolean checkMatch) {

        List<Row> nonCompliantStateRows = checkRow(
                row,
                stateRows,
                columns,
                referenceColumns,
                checkMatch);

        List<Row> nonCompliantDataRows = checkRow(
                row,
                dataRows,
                columns,
                referenceColumns,
                checkMatch);

        int numRows = dataRows.size() + stateRows.size();
        int numNonCompliantRows = nonCompliantDataRows.size() + nonCompliantStateRows.size();
        boolean everyRowNonCompliant = (numRows == numNonCompliantRows);

        MatchRecord matchRecord = null;

        if (numNonCompliantRows > 0 && everyRowNonCompliant) {

            matchRecord = new MatchRecord(row.reduceRow(columns));
            for (Row stateRow : nonCompliantStateRows) {
                matchRecord.addUnmodifiableComparison(stateRow.reduceRow(referenceColumns));
            }
            for (Row dataRow : nonCompliantDataRows) {
                matchRecord.addModifableComparison(dataRow.reduceRow(referenceColumns));
            }
        }

        return matchRecord;
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

            if (colsIterator.hasNext()) {
                boolean allPairingsSatisfied = true;
                boolean onePairingSatisfied = false;

                while (colsIterator.hasNext()) {
                    Column column = colsIterator.next();
                    Column referenceColumn = refColsIterator.next();

                    Cell cell = row.getCell(column);
                    Cell referenceCell = compareRow.getCell(referenceColumn);

                    boolean valuesEqual = new RelationalChecker(
                            cell.getValue(),
                            RelationalOperator.EQUALS,
                            referenceCell.getValue(),
                            allowNull).check();

                    boolean thisPairingSatisfied = (shouldMatch == valuesEqual);

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
                    nonCompliantRows.add(compareRow);
                }
            }
        }

        return nonCompliantRows;
    }

    @Override
    public String getInfo() {
        boolean result = check();
        String info = "Match clause: " + matchClause + "\n";
        info += "\t* Success: " + result + "\n";
        if (!result) {
            info += "\t* Matching cells: " + getMatchRecordsInfo(matchingCells) + "\n";
            info += "\t* Non-matching cells: " + getMatchRecordsInfo(nonMatchingCells) + "\n";
        }
        return info;
    }

    private String getMatchRecordsInfo(List<MatchRecord> matchRecords) {
        if (matchRecords.size() == 0) {
            return "<none>";
        }
        String info = "\n";
        for (MatchRecord matchRecord : matchRecords) {
            info += getMatchRecordInfo(matchRecord);
        }
        return info;
    }

    private String getMatchRecordInfo(MatchRecord matchRecord) {
        String str = "\t\t* Original row: " + matchRecord.getOriginalRow() + "\n";
        str += "\t\t\t* Data compare row(s): " + getCompareRowsInfo(matchRecord.getModifiableComparisons()) + "\n";
        str += "\t\t\t* State compare row(s): " + getCompareRowsInfo(matchRecord.getUnmodifiableComparisons()) + "\n";
        return str;
    }

    private String getCompareRowsInfo(List<Row> compareRows) {
        int numRows = compareRows.size();
        if (numRows == 0) {
            return "<none>";
        } else if (numRows == 1) {
            return compareRows.get(0).toString();
        } else {
            String str = "";
            boolean first = true;
            for (Row row : compareRows) {
                if (first) {
                    first = false;
                } else {
                    str += "\n";
                }
                str += "\t\t\t\t* " + row;
            }
            return str;
        }
    }
}
