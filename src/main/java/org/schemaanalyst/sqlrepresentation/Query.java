package org.schemaanalyst.sqlrepresentation;

import org.schemaanalyst.sqlrepresentation.expression.Expression;

/**
 * Created by phil on 10/11/2014.
 */
public abstract class Query {

    protected Table table;
    protected Expression whereClause;

    public Query(Table table) {
        this.table = table;
    }

    public Query(Table table, Expression whereClause) {
        this.whereClause = whereClause;
    }

    public Table getTable() {
        return table;
    }

    public Expression getWhereClause() {
        return whereClause;
    }
}
