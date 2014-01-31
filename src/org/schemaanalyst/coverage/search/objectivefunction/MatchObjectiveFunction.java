package org.schemaanalyst.coverage.search.objectivefunction;

import org.schemaanalyst.coverage.criterion.clause.MatchClause;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.*;
import org.schemaanalyst.datageneration.search.objective.value.RelationalValueObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;

import java.util.Iterator;
import java.util.List;

/**
 * Created by phil on 24/01/2014.
 */
public class MatchObjectiveFunction extends ObjectiveFunction<Row> {

    private MatchClause matchClause;
    private Data state;

    public MatchObjectiveFunction(MatchClause matchClause, Data state) {
        this.matchClause = matchClause;
        this.state = state;
    }

    @Override
    public ObjectiveValue evaluate(Row row) {
        BestOfMultiObjectiveValue objVal = new BestOfMultiObjectiveValue();
        List<Row> stateRows = state.getRows(matchClause.getReferenceTable());

        for (Row stateRow : stateRows) {
            objVal.add(compareRows(row, stateRow));
        }

        return objVal;
    }

    protected ObjectiveValue compareRows(Row row, Row stateRow) {
        MultiObjectiveValue objVal =
                matchClause.isAndMode()
                        ? new SumOfMultiObjectiveValue()
                        : new BestOfMultiObjectiveValue();

        evaluateColumnLists(
                row,
                stateRow,
                matchClause.getEqualColumns(),
                matchClause.getEqualRefColumns(),
                RelationalOperator.EQUALS,
                objVal);

        evaluateColumnLists(
                row,
                stateRow,
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

        Iterator<Column> colIt = cols.iterator();
        Iterator<Column> refColIt = refCols.iterator();

        while (colIt.hasNext()) {
            Column col = colIt.next();
            Column refCol = refColIt.next();

            Value colValue = row.getCell(col).getValue();
            Value refColValue = stateRow.getCell(refCol).getValue();

            ObjectiveValue compareObjVal =
                    RelationalValueObjectiveFunction.compute(
                            colValue,
                            op,
                            refColValue,
                            true);

            objVal.add(compareObjVal);
        }
    }
}
