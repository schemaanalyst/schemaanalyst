package org.schemaanalyst.coverage.predicate.function;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.Arrays;

/**
 * Created by phil on 19/01/2014.
 */
public class NullFunction extends Function {

    public NullFunction(Table table, Column column) {
        super(table, Arrays.asList(column));
    }

    public String toString() {
        return "null(" + columns.get(0) + ")";
    }
}
