package org.schemaanalyst.coverage.predicate.function;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.List;

/**
 * Created by phil on 19/01/2014.
 */
public class DistinctFunction extends RowCompareFunction {

    public DistinctFunction(Table table, Column... columns) {
        super(Type.FOR_ALL_ROWS, table, columns);
    }

    public DistinctFunction(Table table, List<Column> columns) {
        super(Type.FOR_ALL_ROWS, table, columns);
    }

    public DistinctFunction(Table table, Column column, Table otherTable, Column otherColumn) {
        super(Type.FOR_ALL_ROWS, table, column, otherTable, otherColumn);
    }

    public DistinctFunction(Table table, List<Column> columns, Table otherTable, List<Column> otherColumns) {
        super(Type.FOR_ALL_ROWS, table, columns, otherTable, otherColumns);
    }

    public String getName() {
        return "Distinct";
    }
}
