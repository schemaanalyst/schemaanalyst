package org.schemaanalyst.coverage.predicate.function;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.Arrays;

/**
 * Created by phil on 19/01/2014.
 */
public class NotNullFunction extends Function {

    public NotNullFunction(Table table, Column column) {
        super(table, Arrays.asList(column));
    }
}
