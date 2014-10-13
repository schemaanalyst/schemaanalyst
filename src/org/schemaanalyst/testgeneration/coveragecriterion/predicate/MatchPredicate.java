package org.schemaanalyst.testgeneration.coveragecriterion.predicate;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by phil on 18/07/2014.
 */
public class MatchPredicate extends Predicate {

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

    private Table table, refTable;
    private List<Column> matchingCols, nonMatchingCols, matchingRefCols, nonMatchingRefCols;
    private Mode mode;
    private boolean tableIsRefTable;
    private boolean colsAreRefCols;

    public MatchPredicate(Table table, List<Column> equalCols, List<Column> notEqualCols, Mode mode) {
        this(table, equalCols, notEqualCols, table, equalCols, notEqualCols, mode);
    }

    public MatchPredicate(Table table, List<Column> matchingCols, List<Column> nonMatchingCols,
                          Table refTable, List<Column> matchingRefCols, List<Column> nonMatchingRefCols, Mode mode) {
        this.table = table;
        this.matchingCols = new ArrayList<>(matchingCols);
        this.nonMatchingCols = new ArrayList<>(nonMatchingCols);

        this.refTable = refTable;
        this.matchingRefCols = new ArrayList<>(matchingRefCols);
        this.nonMatchingRefCols = new ArrayList<>(nonMatchingRefCols);

        this.mode = mode;
        this.tableIsRefTable = table.equals(refTable);

        boolean sameNumberOfEqualsCols = matchingCols.size() == matchingRefCols.size();
        boolean sameNumberOfNotEqualsCols = nonMatchingCols.size() == nonMatchingRefCols.size();

        if (!sameNumberOfEqualsCols || !sameNumberOfNotEqualsCols) {
            throw new PredicateConfigurationException("Number of columns and reference columns are not equal");
        }

        colsAreRefCols = true;
        Iterator<Column> matchingColsIterator = matchingCols.iterator();
        Iterator<Column> matchingRefColsIterator = matchingRefCols.iterator();
        while (matchingColsIterator.hasNext() && matchingRefColsIterator.hasNext()) {
            if (!matchingColsIterator.next().equals(matchingRefColsIterator.next())) {
                colsAreRefCols = false;
            }
        }
        if (colsAreRefCols) {
            Iterator<Column> nonMatchingColsIterator = nonMatchingCols.iterator();
            Iterator<Column> nonMatchingRefColsIterator = nonMatchingRefCols.iterator();
            while (nonMatchingColsIterator.hasNext() && nonMatchingRefColsIterator.hasNext()) {
                if (!nonMatchingColsIterator.next().equals(nonMatchingRefColsIterator.next())) {
                    colsAreRefCols = false;
                }
            }
        }
    }

    public Table getTable() {
        return table;
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

    public boolean tableIsRefTable() {
        return tableIsRefTable;
    }

    public boolean colsAreRefCols() {
        return colsAreRefCols;
    }

    public String getName() {
        int numCols = matchingCols.size() + nonMatchingCols.size();
        return (numCols > 1 ? mode : "") + "Match";
    }

    @Override
    public void accept(PredicateVisitor predicateVisitor) {
        predicateVisitor.visit(this);
    }

    private String paramsToString() {
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
        String colsStr = "[" + table + ": " + StringUtils.join(cols, ",");

        if (!table.equals(refTable) || !cols.equals(refCols)) {
            colsStr += " -> ";
            if (!tableIsRefTable()) {
                colsStr += refTable + ": ";
            }
            colsStr += StringUtils.join(refCols, ",");
        }

        return colsStr + "]";
    }

    @Override
    public String toString() {
        int numCols = matchingCols.size() + nonMatchingCols.size();
        return (numCols > 1 ? mode : "") + "Match" + "[" + paramsToString() + "]";
    }

    @Override
    // This has been modified from the auto-generated version to ignore the mode field if there is
    // only one column
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchPredicate that = (MatchPredicate) o;

        if (!matchingCols.equals(that.matchingCols)) return false;
        if (!matchingRefCols.equals(that.matchingRefCols)) return false;
        if (!nonMatchingCols.equals(that.nonMatchingCols)) return false;
        if (!nonMatchingRefCols.equals(that.nonMatchingRefCols)) return false;
        if (!refTable.equals(that.refTable)) return false;
        if (!table.equals(that.table)) return false;
        if (getColumns().size() > 1 && mode != that.mode) return false;

        return true;
    }

    @Override
    // This has been modified from the auto-generated version to ignore the mode field if there is
    // only one column
    public int hashCode() {
        int result = table.hashCode();
        result = 31 * result + refTable.hashCode();
        result = 31 * result + matchingCols.hashCode();
        result = 31 * result + nonMatchingCols.hashCode();
        result = 31 * result + matchingRefCols.hashCode();
        result = 31 * result + nonMatchingRefCols.hashCode();
        if (getColumns().size() > 1) {
            result = 31 * result + mode.hashCode();
        }
        return result;
    }
}
