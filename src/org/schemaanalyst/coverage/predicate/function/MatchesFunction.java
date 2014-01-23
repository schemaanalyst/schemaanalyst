package org.schemaanalyst.coverage.predicate.function;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.List;

/**
 * Created by phil on 19/01/2014.
 */
public class MatchesFunction extends RowCompareFunction {

    public MatchesFunction(Table table, Column... columns) {
        super(Type.THERE_EXISTS_ROW, table, columns);
    }

    public MatchesFunction(Table table, List<Column> columns) {
        super(Type.THERE_EXISTS_ROW, table, columns);
    }

    public MatchesFunction(Table table, Column column, Table otherTable, Column otherColumn) {
        super(Type.THERE_EXISTS_ROW, table, column, otherTable, otherColumn);
    }

    public MatchesFunction(Table table, List<Column> columns, Table otherTable, List<Column> otherColumns) {
        super(Type.THERE_EXISTS_ROW, table, columns, otherTable, otherColumns);
    }


    public String getName() {
        return "Matches";
    }
}
