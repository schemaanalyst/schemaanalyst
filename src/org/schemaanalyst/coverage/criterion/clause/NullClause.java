package org.schemaanalyst.coverage.criterion.clause;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

/**
 * Created by phil on 19/01/2014.
 */
public class NullClause extends Clause {

    private Column column;
    private boolean satisfy;

    public NullClause(Table table, Column column, boolean satisfy) {
        super(table);
        this.column = column;
        this.satisfy = satisfy;
    }

    public Column getColumn() {
        return column;
    }

    public boolean requiresComparisonRow() {
        return false;
    }

    public boolean getSatisfy() {
        return satisfy;
    }

    public String getName() {
        return (!satisfy ? "\u00AC" : "") + "Null";
    }

    protected String paramsToString() {
        return table + ": " + column.toString();
    }

    public NullClause duplicate() {
        return new NullClause(table, column, satisfy);
    }

    public void accept(ClauseVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        NullClause that = (NullClause) o;

        if (satisfy != that.satisfy) return false;
        if (!column.equals(that.column)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + column.hashCode();
        result = 31 * result + (satisfy ? 1 : 0);
        return result;
    }
}
