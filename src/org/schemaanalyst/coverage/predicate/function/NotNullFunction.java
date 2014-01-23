package org.schemaanalyst.coverage.predicate.function;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.Arrays;

/**
 * Created by phil on 21/01/2014.
 */
public class NotNullFunction extends Function {

    private Column column;

    public NotNullFunction(Table table, Column column) {
        super(table);
        this.column = column;
    }

    public String getName() {
        return "NotNull";
    }

    protected String argumentsToString() {
        return column.toString();
    }
}
