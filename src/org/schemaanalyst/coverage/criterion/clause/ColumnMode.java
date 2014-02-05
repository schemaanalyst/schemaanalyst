package org.schemaanalyst.coverage.criterion.clause;

/**
* Created by phil on 05/02/2014.
*/
public enum ColumnMode {
    AND, OR;

    public boolean isAndMode() {
        return this == AND;
    }

    public boolean isOrMode() {
        return this == OR;
    }

    public String toString() {
        return isAndMode() ? "\u2227" : "\u2228";
    }
}
