package org.schemaanalyst.coverage.predicate.function;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 19/01/2014.
 */
public abstract class Function {

    protected Table table;
    protected List<Column> columns;

    public Function(Table table, List<Column> columns) {
        this.table = table;
        this.columns = new ArrayList<>(columns);
    }
}
