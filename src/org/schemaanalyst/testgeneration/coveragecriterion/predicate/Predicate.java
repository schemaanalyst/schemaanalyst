package org.schemaanalyst.testgeneration.coveragecriterion.predicate;

import org.schemaanalyst.sqlrepresentation.Table;

/**
 * Created by phil on 18/07/2014.
 */
public abstract class Predicate {

    protected Table table;

    public Predicate(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return table;
    }
}
