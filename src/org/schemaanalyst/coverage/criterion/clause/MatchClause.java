package org.schemaanalyst.coverage.criterion.clause;

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
public class MatchClause extends Clause {

    public enum Mode {
        AND, OR;

        public String toString() {
            return (this == AND) ? "\u2227" : "\u2228";
        }
    }

    private Table refTable;
    private List<Column> notEqualCols, equalCols, notEqualRefCols, equalRefCols;
    private Mode mode;
    private boolean requiresComparisonRow;

    public MatchClause(Table table, List<Column> equalCols, List<Column> notEqualCols,
                       Mode mode, boolean requiresComparisonRow) {
        this(table, equalCols, notEqualCols,
             table, equalCols, notEqualCols,
             mode, requiresComparisonRow);
    }

    public MatchClause(Table table, List<Column> equalCols, List<Column> notEqualCols,
                       Table refTable, List<Column> equalRefCols, List<Column> notEqualRefCols,
                       Mode mode, boolean requiresComparisonRow) {
        super(table);

        this.equalCols = new ArrayList<>(equalCols);
        this.notEqualCols = new ArrayList<>(notEqualCols);

        this.refTable = refTable;
        this.equalRefCols = new ArrayList<>(equalRefCols);
        this.notEqualRefCols = new ArrayList<>(notEqualRefCols);

        this.mode = mode;
        this.requiresComparisonRow = requiresComparisonRow;
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

    public boolean requiresComparisonRow() {
        return requiresComparisonRow;
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

    protected String paramsToString() {
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

    public void accept(ClauseVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MatchClause that = (MatchClause) o;

        if (!equalCols.equals(that.equalCols)) return false;
        if (!equalRefCols.equals(that.equalRefCols)) return false;
        if (mode != that.mode) return false;
        if (!notEqualCols.equals(that.notEqualCols)) return false;
        if (!notEqualRefCols.equals(that.notEqualRefCols)) return false;
        if (!refTable.equals(that.refTable)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mode.hashCode();
        result = 31 * result + refTable.hashCode();
        result = 31 * result + notEqualCols.hashCode();
        result = 31 * result + equalCols.hashCode();
        result = 31 * result + notEqualRefCols.hashCode();
        result = 31 * result + equalRefCols.hashCode();
        return result;
    }
}
