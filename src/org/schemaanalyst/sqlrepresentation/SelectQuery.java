package org.schemaanalyst.sqlrepresentation;

import org.schemaanalyst.sqlrepresentation.expression.Expression;

/**
 * Created by phil on 10/11/2014.
 */
public class SelectQuery extends Query {

    // this is for joined tables
    // I propose each table is added separately with its type of join
    // protected List<Table> joinedTables;

    public SelectQuery(Table table) {
        super(table);
    }

    public SelectQuery(Table table, Expression whereClause) {
        super(table, whereClause);
    }

}
