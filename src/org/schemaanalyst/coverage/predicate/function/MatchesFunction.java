package org.schemaanalyst.coverage.predicate.function;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by phil on 19/01/2014.
 */
public class MatchesFunction extends Function {

    private Table otherTable;
    private List<Column> otherColumns;

    public MatchesFunction(Table table, Column... columns) {
        this(table, Arrays.asList(columns));
    }

    public MatchesFunction(Table table, List<Column> columns) {
        this(table, columns, table, columns);
    }

    public MatchesFunction(Table table, List<Column> columns,
                           Table otherTable, List<Column> otherColumns) {
        super(table, columns);
        this.otherTable = otherTable;
        this.otherColumns = new ArrayList<>(otherColumns);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("matches(");
        sb.append(table + ": ");
        sb.append(StringUtils.join(columns));

        if (!table.equals(otherTable)) {
            sb.append(" -> ");
            sb.append(otherTable + ": ");
            sb.append(StringUtils.join(otherColumns));
        }

        sb.append(")");
        return sb.toString();
    }
}
