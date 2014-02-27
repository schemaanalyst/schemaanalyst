package org.schemaanalyst.coverage.testgeneration.datageneration.handler;

import org.schemaanalyst.coverage.criterion.clause.NullClause;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;

import java.util.List;

/**
 * Created by phil on 26/02/2014.
 */
public class NullClauseHandler extends Handler {

    private NullClause nullClause;

    public NullClauseHandler(NullClause nullClause) {
        this.nullClause = nullClause;
    }

    @Override
    protected boolean handle(Data data, boolean fix) {
        boolean satisfies = true;
        List<Row> rows = data.getRows(nullClause.getTable());

        if (rows.size() > 0) {
            for (Row row : rows) {
                Cell cell = row.getCell(nullClause.getColumn());

                if (nullClause.getSatisfy() != cell.isNull()) {
                    if (fix) {
                        cell.setNull(nullClause.getSatisfy());
                    }

                    satisfies = false;
                }
            }
        }

        return satisfies;
    }
}
