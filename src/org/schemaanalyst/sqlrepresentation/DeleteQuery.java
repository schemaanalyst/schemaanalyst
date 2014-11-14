package org.schemaanalyst.sqlrepresentation;

import org.schemaanalyst.sqlrepresentation.expression.Expression;

/**
 * Created by phil on 10/11/2014.
 */
public class DeleteQuery extends Query {

    public DeleteQuery(Table table) {
        super(table);
    }

    public DeleteQuery(Table table, Expression whereClause) {
        super(table, whereClause);
    }
}
