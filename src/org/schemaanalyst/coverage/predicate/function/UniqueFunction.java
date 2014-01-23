package org.schemaanalyst.coverage.predicate.function;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.List;

/**
 * Created by phil on 23/01/2014.
 */
public class UniqueFunction extends Function {

    private Table refTable;
    private List<Column> cols, refCols;

    public UniqueFunction(Table table, List<Column> cols) {
        this(table, cols, table, cols);
    }

    public UniqueFunction(Table table, List<Column> cols, Table refTable, List<Column> refCols) {
        super(table);
        this.cols = cols;
        this.refTable = refTable;
        this.refCols = refCols;
    }

    @Override
    public String getName() {
        return "Distinct";
    }

    @Override
    protected String argumentsToString() {
        String colsStr = "";

        if (!table.equals(refTable)) {
            colsStr += table + ": ";
        }

        colsStr += StringUtils.join(cols, ",");

        if (!table.equals(refTable)) {
            colsStr += " -> ";
            colsStr += refTable + ": ";
            colsStr += StringUtils.join(refCols, ",");
        }

        return colsStr + "";
    }
}
