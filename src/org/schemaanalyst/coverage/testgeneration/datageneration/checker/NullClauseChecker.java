package org.schemaanalyst.coverage.testgeneration.datageneration.checker;

import org.schemaanalyst.coverage.criterion.clause.NullClause;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 27/02/2014.
 */
public class NullClauseChecker extends ClauseChecker<NullClause> {

    private NullClause nullClause;
    private Data data;
    protected List<Cell> nonComplyingCells;

    public NullClauseChecker(NullClause nullClause, Data data) {
        this.nullClause = nullClause;
        this.data = data;
    }

    public NullClause getClause() {
        return nullClause;
    }

    public List<Cell> getNonComplyingCells() {
        return nonComplyingCells;
    }

    @Override
    public boolean check() {
        nonComplyingCells = new ArrayList<>();

        // do the check
        List<Row> rows = data.getRows(nullClause.getTable());
        if (rows.size() > 0) {
            for (Row row : rows) {
                Cell cell = row.getCell(nullClause.getColumn());
                if (nullClause.getSatisfy() != cell.isNull()) {
                    nonComplyingCells.add(cell);
                }
            }
        }

        return (nonComplyingCells.size() == 0);
    }
}
