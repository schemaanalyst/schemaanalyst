package org.schemaanalyst.coverage.predicate.function;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by phil on 22/01/2014.
 */
public abstract class RowCompareFunction extends Function {

    public enum Type {
        FOR_ALL_ROWS, THERE_EXISTS_ROW;

        public String toString() {
            // "for all / there exists a row in other"
            return (this == Type.FOR_ALL_ROWS ? "\u2200" : "\u2203") + "r\u2208O ";
        }
    }

    private Type type = Type.FOR_ALL_ROWS;
    private Table otherTable;
    private List<Column> otherColumns;

    public RowCompareFunction(Type type, Table table, Column... columns) {
        this(type, table, Arrays.asList(columns));
    }

    public RowCompareFunction(Type type, Table table, List<Column> columns) {
        this(type, table, columns, table, columns);
    }

    public RowCompareFunction(Type type, Table table, Column column,
                              Table otherTable, Column otherColumn) {
        this(type, table, Arrays.asList(column), otherTable, Arrays.asList(otherColumn));
    }

    public RowCompareFunction(Type type, Table table, List<Column> columns,
                              Table otherTable, List<Column> otherColumns) {
        super(table, columns);
        this.otherTable = otherTable;
        this.otherColumns = new ArrayList<>(otherColumns);
        this.type = type;
    }

    public String argumentsToString() {
        String args = "";

        if (!table.equals(otherTable)) {
            args += table + ": ";
        }

        args += StringUtils.join(columns);

        if (!table.equals(otherTable)) {
            args += " -> ";
            args += otherTable + ": ";
            args += StringUtils.join(otherColumns);
        }

        return args;
    }

    public String toString() {
        return "(" + type  + super.toString() + ")";
    }
}
