package org.schemaanalyst.coverage.predicate.function;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 19/01/2014.
 */
public abstract class Function {

    protected Table table;

    public Function(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return table;
    }

    public abstract String getName();

    protected abstract String argumentsToString();

    public String toString() {
        return getName() + "(" + argumentsToString() + ")";
    }
}
