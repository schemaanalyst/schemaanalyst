package org.schemaanalyst.coverage.criterion.clause;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Scenario: We have one current row "C" to compare with a set of other rows "R", 1-r.
 * This function checks C is not equal to all R, 1-r, except for one row according the
 * columns individually in the lists "equalsCols" and "notEqualsCols" (with optional references).
 *
 * @author Phil McMinn
 */
public class MatchClause extends Clause {

    public enum Mode {
        AND, OR;

        public boolean isAnd() {
            return this == AND;
        }

        public boolean isOr() {
            return this == OR;
        }

        public String toString() {
            return isAnd() ? "\u2227" : "\u2228";
        }
    }

    public static final List<Column> EMPTY_COLUMN_LIST = Collections.unmodifiableList(new ArrayList<Column>());

    private Table refTable;
    private List<Column> matchingCols, nonMatchingCols, matchingRefCols, nonMatchingRefCols;
    private Mode mode;
    private boolean requiresComparisonRow, involvesOneTable;

    public MatchClause(Table table, List<Column> equalCols, List<Column> notEqualCols,
                       Mode mode, boolean requiresComparisonRow) {
        this(table, equalCols, notEqualCols, table, equalCols, notEqualCols, mode, requiresComparisonRow);
    }

    public MatchClause(Table table, List<Column> matchingCols, List<Column> nonMatchingCols,
                       Table refTable, List<Column> matchingRefCols, List<Column> nonMatchingRefCols,
                       Mode mode, boolean requiresComparisonRow) {
        super(table);

        this.matchingCols = new ArrayList<>(matchingCols);
        this.nonMatchingCols = new ArrayList<>(nonMatchingCols);

        this.refTable = refTable;
        this.matchingRefCols = new ArrayList<>(matchingRefCols);
        this.nonMatchingRefCols = new ArrayList<>(nonMatchingRefCols);

        this.mode = mode;
        this.requiresComparisonRow = requiresComparisonRow;
        this.involvesOneTable = table.equals(refTable);

        boolean sameNumberOfEqualsCols = matchingCols.size() == matchingRefCols.size();
        boolean sameNumberOfNotEqualsCols = nonMatchingCols.size() == nonMatchingRefCols.size();

        if (!sameNumberOfEqualsCols || !sameNumberOfNotEqualsCols) {
            throw new ClauseConfigurationException("Number of columns and reference columns are not equal");
        }
    }

    public List<Column> getColumns() {
        ArrayList<Column> cols = new ArrayList<>(matchingCols);
        cols.addAll(nonMatchingCols);
        return cols;
    }

    public List<Column> getMatchingColumns() {
        return new ArrayList<>(matchingCols);
    }

    public List<Column> getNonMatchingColumns() {
        return new ArrayList<>(nonMatchingCols);
    }

    public Table getReferenceTable() {
        return refTable;
    }

    public List<Column> getReferenceColumns() {
        ArrayList<Column> cols = new ArrayList<>(matchingRefCols);
        cols.addAll(nonMatchingRefCols);
        return cols;
    }

    public List<Column> getMatchingReferenceColumns() {
        return new ArrayList<>(matchingRefCols);
    }

    public List<Column> getNonMatchingReferenceColumns() {
        return new ArrayList<>(nonMatchingRefCols);
    }

    public Mode getMode() {
        return mode;
    }

    public boolean requiresComparisonRow() {
        return requiresComparisonRow;
    }

    public boolean involvesOneTable() {
        return involvesOneTable;
    }

    public String getName() {
        int numCols = matchingCols.size() + nonMatchingCols.size();
        return (numCols > 1 ? mode : "") + "Match";
    }

    protected String paramsToString() {
        String str = "";

        if (matchingCols.size() > 0) {
            str += "=" + colsToString(matchingCols, matchingRefCols);
        }
        if (nonMatchingCols.size() > 0) {
            if (str.length() > 0) {
                str += ", ";
            }
            str += "\u2260" + colsToString(nonMatchingCols, nonMatchingRefCols);
        }

        return str;
    }

    private String colsToString(List<Column> cols, List<Column> refCols) {
        String colsStr = "(" + table + ": " + StringUtils.join(cols, ",");

        if (!table.equals(refTable) || !cols.equals(refCols)) {
            colsStr += " -> ";
            if (!involvesOneTable()) {
                colsStr += refTable + ": ";
            }
            colsStr += StringUtils.join(refCols, ",");
        }

        return colsStr + ")";
    }

    public MatchClause duplicate() {
        return new MatchClause(
                table,
                new ArrayList<>(matchingCols),
                new ArrayList<>(nonMatchingCols),
                refTable,
                new ArrayList<>(matchingRefCols),
                new ArrayList<>(nonMatchingRefCols),
                mode, requiresComparisonRow);
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

        if (!matchingCols.equals(that.matchingCols)) return false;
        if (!matchingRefCols.equals(that.matchingRefCols)) return false;
        if (mode != that.mode) return false;
        if (!nonMatchingCols.equals(that.nonMatchingCols)) return false;
        if (!nonMatchingRefCols.equals(that.nonMatchingRefCols)) return false;
        if (!refTable.equals(that.refTable)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mode.hashCode();
        result = 31 * result + refTable.hashCode();
        result = 31 * result + nonMatchingCols.hashCode();
        result = 31 * result + matchingCols.hashCode();
        result = 31 * result + nonMatchingRefCols.hashCode();
        result = 31 * result + matchingRefCols.hashCode();
        return result;
    }
}
