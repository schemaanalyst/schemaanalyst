package org.schemaanalyst.coverage.predicate.function;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.Arrays;

/**
 * Created by phil on 19/01/2014.
 */
public class NullFunction extends Function {

    private Column column;
    private boolean satisfy;

    public NullFunction(Table table, Column column, boolean satisfy) {
        super(table);
        this.column = column;
        this.satisfy = satisfy;
    }

    public Column getColumn() {
        return column;
    }

    public boolean getSatisfy() {
        return satisfy;
    }

    public String getName() {
        return (!satisfy ? "\u00AC" : "") + "Null";
    }

    protected String argumentsToString() {
        return column.toString();
    }
}
