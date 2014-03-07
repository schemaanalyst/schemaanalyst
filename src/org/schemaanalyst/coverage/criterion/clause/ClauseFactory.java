package org.schemaanalyst.coverage.criterion.clause;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

import java.util.Arrays;
import java.util.List;

/**
 * Created by phil on 24/01/2014.
 */
public class ClauseFactory {

    public static NullClause isNotNull(Table table, Column column) {
        return new NullClause(
                table,
                column,
                false);
    }

    public static NullClause isNull(Table table, Column column) {
        return new NullClause(
                table,
                column,
                true);
    }

    public static MatchClause unique(Table table, Column column, boolean requiresComparisonRow) {
        return unique(table, Arrays.asList(column), requiresComparisonRow);
    }

    public static MatchClause unique(Table table, List<Column> columns, boolean requiresComparisonRow) {
        return new MatchClause(
                table,
                MatchClause.EMPTY_COLUMN_LIST,
                columns,
                MatchClause.Mode.OR,
                requiresComparisonRow);
    }

    public static MatchClause notUnique(Table table, Column column) {
        return notUnique(table, Arrays.asList(column));
    }

    public static MatchClause notUnique(Table table, List<Column> columns) {
        return new MatchClause(
                table,
                columns,
                MatchClause.EMPTY_COLUMN_LIST,
                MatchClause.Mode.AND,
                true);
    }

    public static MatchClause references(Table table, List<Column> columns, Table refTable, List<Column> refCols) {
        return new MatchClause(
                table,
                columns,
                MatchClause.EMPTY_COLUMN_LIST,
                refTable,
                refCols,
                MatchClause.EMPTY_COLUMN_LIST,
                MatchClause.Mode.AND,
                false);
    }

    public static MatchClause notReferences(Table table, List<Column> columns, Table refTable, List<Column> refCols) {
        return new MatchClause(
                table,
                MatchClause.EMPTY_COLUMN_LIST,
                columns,
                refTable,
                MatchClause.EMPTY_COLUMN_LIST,
                refCols,
                MatchClause.Mode.OR,
                false);
    }

    public static ExpressionClause expression(Table table, Expression expression) {
        return new ExpressionClause(
                table,
                expression,
                true);
    }

    public static ExpressionClause notExpression(Table table, Expression expression) {
        return new ExpressionClause(
                table,
                expression,
                false);
    }
}
