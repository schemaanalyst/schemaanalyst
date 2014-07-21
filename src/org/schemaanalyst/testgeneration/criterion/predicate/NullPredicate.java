package org.schemaanalyst.testgeneration.criterion.predicate;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

/**
 * Created by phil on 18/07/2014.
 */
public class NullPredicate extends Predicate {

    private Table table;
    private Column column;
    private boolean truthValue;

    public NullPredicate(Table table, Column column, boolean truthValue) {
        this.column = column;
        this.truthValue = truthValue;
    }

    public Table getTable() {
        return table;
    }

    public Column getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        NullPredicate that = (NullPredicate) o;

        if (truthValue != that.truthValue) return false;
        if (!column.equals(that.column)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + column.hashCode();
        result = 31 * result + (truthValue ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return (!truthValue ? "\u00AC" : "") + "Null(" + table + ": " + column.toString() + ")";
    }
}

