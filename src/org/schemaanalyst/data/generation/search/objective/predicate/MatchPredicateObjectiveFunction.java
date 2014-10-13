package org.schemaanalyst.data.generation.search.objective.predicate;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.generation.search.objective.*;
import org.schemaanalyst.data.generation.search.objective.value.RelationalValueObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.MatchPredicate;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by phil on 24/01/2014.
 */
public class MatchPredicateObjectiveFunction extends ObjectiveFunction<Data> {

    private MatchPredicate matchPredicate;
    private Data state;
    private Table table, referenceTable;
    private boolean forAll;

    public MatchPredicateObjectiveFunction(MatchPredicate matchPredicate, Data state) {
        this.matchPredicate = matchPredicate;
        this.state = state;
        this.table = matchPredicate.getTable();
        this.referenceTable = matchPredicate.getReferenceTable();

        // matches can hold for one row, non-matches need to hold for all rows
        this.forAll = matchPredicate.getNonMatchingColumns().size() > 0;
    }

    @Override
    public ObjectiveValue evaluate(Data data) {
        String description = matchPredicate.toString();
        List<Row> rows = data.getRows(matchPredicate.getTable());

        if (rows.size() > 0) {
            SumOfMultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);
            ListIterator<Row> rowsIterator = rows.listIterator();

            while (rowsIterator.hasNext()) {
                Row row = rowsIterator.next();
                int index = rowsIterator.previousIndex();
                List<Row> compareRows = getCompareRows(data, index);

                if (compareRows.size() > 0) {

                    // if the predicate holds for all rows, we need a SumOf...
                    // else if it's for one row, we need a BestOf..
                    MultiObjectiveValue rowObjVal = (forAll)
                            ? new SumOfMultiObjectiveValue()
                            : new BestOfMultiObjectiveValue();

                    for (Row compareRow : compareRows) {
                        rowObjVal.add(compareRows(row, compareRow));
                    }

                    objVal.add(rowObjVal);
                }

                // // PM: Legacy code from when this used to be specified at a predicate level
                // // PM: It's now the responsibility of the test data generator to ensure a row exists.
                //
                // else if (matchPredicate.getRequiresComparisonRow()) {
                //  objVal.add(ObjectiveValue.worstObjectiveValue("Comparison row not available"));
                // }
            }

            return objVal;
        }

        return ObjectiveValue.worstObjectiveValue(description);
    }

    private List<Row> getCompareRows(Data data, int index) {
        List<Row> compareRows = data.getRows(matchPredicate.getReferenceTable());
        if (table.equals(referenceTable)) {
            if (!matchPredicate.colsAreRefCols()) {
                // include the current record  -- this is an FK to the same table
                index ++;
            }
            compareRows = compareRows.subList(0, index);
        }
        compareRows.addAll(state.getRows(matchPredicate.getReferenceTable()));
        return compareRows;
    }

    private ObjectiveValue compareRows(Row row, Row compareRow) {
        MultiObjectiveValue objVal =
                matchPredicate.getMode() == MatchPredicate.Mode.AND
                        ? new SumOfMultiObjectiveValue()
                        : new BestOfMultiObjectiveValue();

        evaluateColumnLists(
                row,
                compareRow,
                matchPredicate.getMatchingColumns(),
                matchPredicate.getMatchingReferenceColumns(),
                RelationalOperator.EQUALS,
                objVal);

        evaluateColumnLists(
                row,
                compareRow,
                matchPredicate.getNonMatchingColumns(),
                matchPredicate.getNonMatchingReferenceColumns(),
                RelationalOperator.NOT_EQUALS,
                objVal);

        return objVal;
    }

    private void evaluateColumnLists(Row row,
                                     Row compareRow,
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

            // TODO: check: how can the compare row NOT have the column ??
            if (compareRow.hasColumn(refCol)) {
                Value refColValue = compareRow.getCell(refCol).getValue();

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
