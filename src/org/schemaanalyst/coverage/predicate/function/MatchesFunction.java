package org.schemaanalyst.coverage.predicate.function;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 19/01/2014.
 */
public class MatchesFunction extends Function {

    private Table otherTable;
    private List<Column> otherColumns;

    public MatchesFunction(Table table, List<Column> columns) {
        this(table, columns, table, columns);
    }

    public MatchesFunction(Table table, List<Column> columns,
                           Table otherTable, List<Column> otherColumns) {
        super(table, columns);
        this.otherTable = otherTable;
        this.otherColumns = new ArrayList<>(otherColumns);
    }

}
