package org.schemaanalyst.coverage.predicate.function;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by phil on 19/01/2014.
 */
public class DistinctFunction extends Function {

    public DistinctFunction(Table table, Column... columns) {
        super(table, Arrays.asList(columns));
    }

    public DistinctFunction(Table table, List<Column> columns) {
        super(table, columns);
    }

}
