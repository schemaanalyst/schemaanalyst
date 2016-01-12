package org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.MatchPredicate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by phil on 12/03/2014.
 */
public class MatchPredicateChecker extends PredicateChecker {

    private MatchPredicate matchPredicate;
    private boolean allowNull;
    private Data data, state;
    private List<MatchRecord> nonMatchingCells;
    private List<MatchRecord> matchingCells;

    public MatchPredicateChecker(MatchPredicate matchPredicate, boolean allowNull, Data data, Data state) {
        this.matchPredicate = matchPredicate;
        this.allowNull = allowNull;
        this.data = data;
        this.state = state;
    }

    @Override
    public MatchPredicate getPredicate() {
        return matchPredicate;
    }

    public List<MatchRecord> getNonMatchingCells() {
        return nonMatchingCells;
    }

    public List<MatchRecord> getMatchingCells() {
        return matchingCells;
    }

    private List<Row> getDataRows(int index) {
        List<Row> compareRows = data.getRows(matchPredicate.getReferenceTable());
        if (matchPredicate.tableIsRefTable()) {
            if (!matchPredicate.colsAreRefCols()) {
                // include the current record  -- this is an FK to the same table
                index ++;
            }
            compareRows = compareRows.subList(0, index);
        }
        return compareRows;
    }

    private List<Row> getStateRows() {
        return state.getRows(matchPredicate.getReferenceTable());
    }

    @Override
    public boolean check() {
        nonMatchingCells = new ArrayList<>();
        matchingCells = new ArrayList<>();
        List<Row> rows = data.getRows(matchPredicate.getTable());
        List<Row> stateRows = getStateRows();

        if (rows.size() > 0) {
            ListIterator<Row> rowsIterator = rows.listIterator();

            while (rowsIterator.hasNext()) {
                Row row = rowsIterator.next();

                int index = rowsIterator.previousIndex();
                List<Row> dataRows = getDataRows(index);

                int numCompareRows = dataRows.size() + stateRows.size();
                if (numCompareRows > 0) {
                    checkRow(row, stateRows, dataRows);
                }
            }

            boolean result = nonMatchingCells.size() == 0 && matchingCells.size() == 0;
            return result;
        }

        return false;
    }

    private void checkRow(Row row, List<Row> stateRows, List<Row> dataRows) {

        int numNonCompliantRows = 0;

        List<Row> matchingStateRows = new ArrayList<>();
        List<Row> nonMatchingStateRows = new ArrayList<>();
        for (Row stateRow : stateRows) {
            if (!checkRowPair(row, stateRow, matchingStateRows, nonMatchingStateRows)) {
                numNonCompliantRows ++;
            }
        }

        List<Row> matchingDataRows = new ArrayList<>();
        List<Row> nonMatchingDataRows = new ArrayList<>();
        for (Row dataRow : dataRows) {
            if (!checkRowPair(row, dataRow, matchingDataRows, nonMatchingDataRows)) {
                numNonCompliantRows ++;
            }
        }

        int numRows = dataRows.size() + stateRows.size();
        boolean everyRowNonCompliant = (numRows == numNonCompliantRows);

        if (numNonCompliantRows > 0 && everyRowNonCompliant) {

            if (nonMatchingStateRows.size() > 0 || nonMatchingDataRows.size() > 0) {
                MatchRecord nonMatch = new MatchRecord(row.reduceRow(matchPredicate.getMatchingColumns()));
                for (Row stateRow : nonMatchingStateRows) {
                    nonMatch.addUnmodifiableComparison(stateRow.reduceRow(matchPredicate.getMatchingReferenceColumns()));
                }
                for (Row dataRow : nonMatchingDataRows) {
                    nonMatch.addModifiableComparison(dataRow.reduceRow(matchPredicate.getMatchingReferenceColumns()));
                }
                nonMatchingCells.add(nonMatch);
            }

            if (matchingStateRows.size() > 0 || matchingDataRows.size() > 0) {
                MatchRecord match = new MatchRecord(row.reduceRow(matchPredicate.getNonMatchingColumns()));
                for (Row stateRow : matchingStateRows) {
                    match.addUnmodifiableComparison(stateRow.reduceRow(matchPredicate.getNonMatchingReferenceColumns()));
                }
                for (Row dataRow : matchingDataRows) {
                    match.addModifiableComparison(dataRow.reduceRow(matchPredicate.getNonMatchingReferenceColumns()));
                }
                matchingCells.add(match);
            }
        }
    }

    private boolean checkRowPair(Row row, Row compareRow, List<Row> matchingRows, List<Row> nonMatchingRows) {

        boolean matchResult = checkColumnLists(
                row,
                compareRow,
                matchPredicate.getMatchingColumns(),
                matchPredicate.getMatchingReferenceColumns(),
                true);

        if (!matchResult) {
            nonMatchingRows.add(compareRow);
        }

        boolean nonMatchResult = checkColumnLists(
                row,
                compareRow,
                matchPredicate.getNonMatchingColumns(),
                matchPredicate.getNonMatchingReferenceColumns(),
                false);

        if (!nonMatchResult) {
            matchingRows.add(compareRow);
        }

        return matchResult && nonMatchResult;
    }

    private boolean checkColumnLists(Row row,
                                     Row compareRow,
                                     List<Column> cols,
                                     List<Column> refCols,
                                     boolean shouldMatch) {

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
                RelationalOperator op = shouldMatch ? RelationalOperator.EQUALS : RelationalOperator.NOT_EQUALS;

                boolean thisPairingSatisfied = new RelationalChecker(
                        cell.getValue(),
                        op,
                        referenceCell.getValue(),
                        allowNull).check();

                if (thisPairingSatisfied) {
                    onePairingSatisfied = true;
                } else {
                    allPairingsSatisfied = false;
                }
            }

            return (matchPredicate.getMode().isAnd() && allPairingsSatisfied) ||
                   (matchPredicate.getMode().isOr() && onePairingSatisfied);
        }

        return true;
    }

    @Override
    public String getInfo() {
        boolean result = check();
        String info = "Match clause: " + matchPredicate + "\n";
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
