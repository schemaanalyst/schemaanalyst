package org.schemaanalyst.coverage.predicate.function;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by phil on 24/01/2014.
 */
public class FunctionFactory {

    static final List<Column> EMPTY_COLUMN_LIST = new ArrayList<>();

    public static Function isNotNull(Table table, Column column) {
        return new NullFunction(
                table,
                column,
                false);
    }

    public static Function isNull(Table table, Column column) {
        return new NullFunction(
                table,
                column,
                true);
    }

    public static Function unique(Table table, Column... columns) {
        return unique(table, Arrays.asList(columns));
    }

    public static Function unique(Table table, List<Column> columns) {
        return new MatchFunction(
                table,
                EMPTY_COLUMN_LIST,
                columns,
                MatchFunction.Mode.OR);
    }

    public static Function notUnique(Table table, Column... columns) {
        return notUnique(table, Arrays.asList(columns));
    }

    public static Function notUnique(Table table, List<Column> columns) {
        return new MatchFunction(
                table,
                columns,
                EMPTY_COLUMN_LIST,
                MatchFunction.Mode.AND);
    }

    public static Function references(Table table, List<Column> columns, Table refTable, List<Column> refCols) {
        return new MatchFunction(
                table,
                columns,
                EMPTY_COLUMN_LIST,
                refTable,
                refCols,
                EMPTY_COLUMN_LIST,
                MatchFunction.Mode.AND);
    }

    public static Function notReferences(Table table, List<Column> columns, Table refTable, List<Column> refCols) {
        return new MatchFunction(
                table,
                EMPTY_COLUMN_LIST,
                columns,
                refTable,
                EMPTY_COLUMN_LIST,
                refCols,
                MatchFunction.Mode.OR);
    }

    public static Function expression(Table table, Expression expression) {
        return new ExpressionFunction(
                table,
                expression,
                true);
    }

    public static Function notExpression(Table table, Expression expression) {
        return new ExpressionFunction(
                table,
                expression,
                false);
    }
}
