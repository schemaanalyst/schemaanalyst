package org.schemaanalyst.coverage.criterion.clause;

import org.schemaanalyst.sqlrepresentation.Table;

/**
 * Created by phil on 19/01/2014.
 */
public abstract class Clause {

    protected Table table;

    public Clause(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return table;
    }

    public abstract boolean requiresComparisonRow();

    public abstract String getName();

    protected abstract String paramsToString();

    public abstract void accept(ClauseVisitor visitor);

    public abstract Clause duplicate();

    public String toString() {
        return getName() + "(" + paramsToString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Clause function = (Clause) o;

        if (table != null ? !table.equals(function.table) : function.table != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return table != null ? table.hashCode() : 0;
    }
}
