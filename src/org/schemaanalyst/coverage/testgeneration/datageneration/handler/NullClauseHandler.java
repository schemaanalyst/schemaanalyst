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
    public boolean handle(Data data) {
        boolean adjusted = false;
        List<Row> rows = data.getRows(nullClause.getTable());

        if (rows.size() > 0) {

            for (Row row : rows) {
                Cell cell = row.getCell(nullClause.getColumn());

                if (nullClause.getSatisfy() != cell.isNull()) {
                    cell.setNull(nullClause.getSatisfy());
                    adjusted = true;
                }
            }
        }

        return adjusted;
    }
}
