package org.schemaanalyst.coverage.predicate.function;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Scenario: We have one current row "C" to compare with a set of other rows "R", 1-r.
 * This function checks C is not equal to all R, 1-r, except for one row according the
 * columns individually in the lists "equalsCols" and "notEqualsCols" (with optional references).
 *
 * @author Phil McMinn
 *
 */
public class MatchFunction extends Function {

    public enum Mode {
        AND, OR;

        public String toString() {
            return (this == AND) ? "\u2227" : "\u2228";
        }
    }

    private Mode mode;
    private Table refTable;
    private List<Column> notEqualCols, equalCols, notEqualRefCols, equalRefCols;

    public MatchFunction(Table table, List<Column> equalCols, List<Column> notEqualCols, Mode mode) {
        this(table, equalCols, notEqualCols, table, equalCols, notEqualCols, mode);
    }

    public MatchFunction(Table table, List<Column> equalCols, List<Column> notEqualCols,
                         Table refTable, List<Column> equalRefCols, List<Column> notEqualRefCols, Mode mode) {
        super(table);

        this.equalCols = new ArrayList<>(equalCols);
        this.notEqualCols = new ArrayList<>(notEqualCols);

        this.refTable = refTable;
        this.equalRefCols = new ArrayList<>(equalRefCols);
        this.notEqualRefCols = new ArrayList<>(notEqualRefCols);

        this.mode = mode;
    }

    public List<Column> getColumns() {
        ArrayList<Column> cols = new ArrayList<>(equalCols);
        cols.addAll(notEqualCols);
        return cols;
    }

    public List<Column> getEqualColumns() {
        return new ArrayList<>(equalCols);
    }

    public List<Column> getNotEqualColumns() {
        return new ArrayList<>(notEqualCols);
    }

    public Table getReferenceTable() {
        return refTable;
    }

    public List<Column> getReferenceColumns() {
        ArrayList<Column> cols = new ArrayList<>(equalRefCols);
        cols.addAll(notEqualRefCols);
        return cols;
    }

    public List<Column> getEqualRefColumns() {
        return new ArrayList<>(equalRefCols);
    }

    public List<Column> getNotEqualRefColumns() {
        return new ArrayList<>(notEqualRefCols);
    }

    public boolean isAndMode() {
        return mode == Mode.AND;
    }

    private String colsToString(List<Column> cols, List<Column> refCols) {
        String colsStr = "(";

        if (!table.equals(refTable)) {
            colsStr += table + ": ";
        }

        colsStr += StringUtils.join(cols, ",");

        if (!table.equals(refTable)) {
            colsStr += " -> ";
            colsStr += refTable + ": ";
            colsStr += StringUtils.join(refCols, ",");
        }

        return colsStr + ")";
    }

    protected String argumentsToString() {
        String str = "";

        if (equalCols.size() > 0) {
            str += "=" + colsToString(equalCols, equalRefCols);
        }
        if (notEqualCols.size() > 0) {
            if (str.length() > 0) {
                str += ", ";
            }
            str += "\u2260" + colsToString(notEqualCols, notEqualRefCols);
        }

        return str;
    }

    public String getName() {
        return mode + "Match";
    }
}
