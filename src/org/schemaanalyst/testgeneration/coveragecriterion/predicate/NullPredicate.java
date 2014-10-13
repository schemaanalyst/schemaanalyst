package org.schemaanalyst.testgeneration.coveragecriterion.predicate;

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
        this.table = table;
        this.column = column;
        this.truthValue = truthValue;
    }

    public Table getTable() {
        return table;
    }

    public Column getColumn() {
        return column;
    }

    public boolean getTruthValue() {
        return truthValue;
    }

    @Override
    public void accept(PredicateVisitor predicateVisitor) {
        predicateVisitor.visit(this);
    }

    @Override
    public String toString() {
        return (!truthValue ? "\u00AC" : "") + "Null[" + table + ": " + column.toString() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NullPredicate that = (NullPredicate) o;

        if (truthValue != that.truthValue) return false;
        if (!column.equals(that.column)) return false;
        if (!table.equals(that.table)) return false;

        return true;
    }

    @Override
    // This has been modified from the auto-generated version to return a more different hashcode
    // subcomponent for truthValue (previously it just added 1 if the truthValue was true)
    public int hashCode() {
        int result = table.hashCode();
        result = 31 * result + column.hashCode();
        result = 31 * result * (truthValue ? 3 : 1);
        return result;
    }
}

