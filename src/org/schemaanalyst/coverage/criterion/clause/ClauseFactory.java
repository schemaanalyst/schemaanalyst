package org.schemaanalyst.coverage.criterion.clause;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by phil on 24/01/2014.
 */
public class ClauseFactory {

    static final List<Column> EMPTY_COLUMN_LIST = new ArrayList<>();

    public static Clause isNotNull(Table table, Column column) {
        return new NullClause(
                table,
                column,
                false);
    }

    public static Clause isNull(Table table, Column column) {
        return new NullClause(
                table,
                column,
                true);
    }

    public static Clause unique(Table table, Column... columns) {
        return unique(table, Arrays.asList(columns));
    }

    public static Clause unique(Table table, List<Column> columns) {
        return new MatchClause(
                table,
                EMPTY_COLUMN_LIST,
                columns,
                MatchClause.Mode.OR);
    }

    public static Clause notUnique(Table table, Column... columns) {
        return notUnique(table, Arrays.asList(columns));
    }

    public static Clause notUnique(Table table, List<Column> columns) {
        return new MatchClause(
                table,
                columns,
                EMPTY_COLUMN_LIST,
                MatchClause.Mode.AND);
    }

    public static Clause references(Table table, List<Column> columns, Table refTable, List<Column> refCols) {
        return new MatchClause(
                table,
                columns,
                EMPTY_COLUMN_LIST,
                refTable,
                refCols,
                EMPTY_COLUMN_LIST,
                MatchClause.Mode.AND);
    }

    public static Clause notReferences(Table table, List<Column> columns, Table refTable, List<Column> refCols) {
        return new MatchClause(
                table,
                EMPTY_COLUMN_LIST,
                columns,
                refTable,
                EMPTY_COLUMN_LIST,
                refCols,
                MatchClause.Mode.OR);
    }

    public static Clause expression(Table table, Expression expression) {
        return new ExpressionClause(
                table,
                expression,
                true);
    }

    public static Clause notExpression(Table table, Expression expression) {
        return new ExpressionClause(
                table,
                expression,
                false);
    }
}
