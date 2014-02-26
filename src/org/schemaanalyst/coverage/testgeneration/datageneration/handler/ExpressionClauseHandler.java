package org.schemaanalyst.coverage.testgeneration.datageneration.handler;

import org.schemaanalyst.coverage.criterion.clause.ExpressionClause;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;

import java.util.List;

/**
 * Created by phil on 26/02/2014.
 */
public class ExpressionClauseHandler extends Handler {

    private ExpressionClause expressionClause;

    public ExpressionClauseHandler(ExpressionClause expressionClause) {
        this.expressionClause = expressionClause;
    }

    @Override
    public boolean handle(Data data) {
        String description = expressionClause.toString();
        List<Row> rows = data.getRows(expressionClause.getTable());

        if (rows.size() > 0) {


            for (Row row : rows) {

            }


        }

        return false;
    }
}
