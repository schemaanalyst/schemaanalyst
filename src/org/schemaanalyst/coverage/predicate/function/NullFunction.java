package org.schemaanalyst.coverage.predicate.function;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.Arrays;

/**
 * Created by phil on 19/01/2014.
 */
public class NullFunction extends Function {

    private Column column;

    public NullFunction(Table table, Column column) {
        super(table);
        this.column = column;
    }

    public String getName() {
        return "Null";
    }

    protected String argumentsToString() {
        return column.toString();
    }
}
