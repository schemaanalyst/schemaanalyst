package org.schemaanalyst.sqlwriter;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

import java.util.Iterator;
import java.util.List;

/**
 * Writer to transform all types of constraints into CHECK constraints.
 *
 * @author Chris J. Wright
 */
public class ConstraintAsCheckSQLWriter extends ConstraintSQLWriter {

    private final String INACTIVE_MUTANT;

    public ConstraintAsCheckSQLWriter(String mutantId) {
        INACTIVE_MUTANT = "NOT active_mutant(" + mutantId + "::text)";
        expressionSQLWriter = new ExpressionSQLWriter();
    }

    @Override
    public String writeCheck(CheckConstraint check) {
        Expression expression = check.getExpression();
        String sql = expressionSQLWriter.writeExpression(expression);
        return "CHECK (" + INACTIVE_MUTANT + " OR (" + sql + "))";
    }

    @Override
    public String writePrimaryKey(PrimaryKeyConstraint primaryKey) {
        List<Column> columns = primaryKey.getColumns();
        // Must be the active mutant
        String sql = "CHECK (" + INACTIVE_MUTANT + " OR (";
        // Must be not null
        for (Column column : columns) {
            sql += satisfyNotNull(column);
            sql += " AND ";
        }
        // Must satisfy pk rules
        sql += satisfyPrimaryKey(primaryKey.getTable(), columns);
        sql += "))";
        return sql;
    }

    @Override
    public String writeUnique(UniqueConstraint unique) {
        // Must be the active mutant
        String sql = "CHECK (" + INACTIVE_MUTANT + " OR (";
        // Must have at least one null valued column...
        for (Column column : unique.getColumns()) {
            sql += "NOT " + satisfyNotNull(column);
            sql += " OR ";
        }
        // ...or satisfy unique rules
        sql += satisfyUnique(unique.getTable(), unique.getColumns());
        sql += "))";
        return sql;
    }

    @Override
    public String writeNotNull(NotNullConstraint notNull) {
        String sql = "CHECK (" + INACTIVE_MUTANT + " OR (";
        sql += satisfyNotNull(notNull.getColumn());
        sql += "))";
        return sql;
    }

    @Override
    public String writeForeignKey(ForeignKeyConstraint foreignKey) {
//        CHECK (NOT satisfy_notnull(c::text) OR NOT satisfy_notnull(d::text) OR satisfy_fk('one'::text,'{a,b}'::text[],ARRAY[c,d]::text[]));
        List<Column> columns = foreignKey.getColumns();
        List<Column> refColumns = foreignKey.getReferenceColumns();
        // Must be the active mutant
        String sql = "CHECK (" + INACTIVE_MUTANT + " OR (";
        // Either a column is null...
        for (Column column : columns) {
            sql += "NOT " + satisfyNotNull(column) + " OR ";
        }
        // ...or the fk rules are satisfied
        sql += satisfyFk(foreignKey.getReferenceTable(), columns, refColumns);
        sql += "))";
        return sql;
    }

    private static String satisfyPrimaryKey(Table table, List<Column> columns) {
        String sql = "satisfy_pk('" + table.getName() + "'::text,";
        sql += "ARRAY[" + columnsToQuotedString(columns) + "]::text[],";
        sql += "ARRAY[" + columnsToDoubleQuotedString(columns) + "]::text[])";
        return sql;
    }

    private static String satisfyUnique(Table table, List<Column> columns) {
        String sql = "satisfy_unique('" + table.getName() + "'::text,";
        sql += "ARRAY[" + columnsToQuotedString(columns) + "]::text[],";
        sql += "ARRAY[" + columnsToDoubleQuotedString(columns) + "]::text[])";
        return sql;
    }

    private static String satisfyNotNull(Column column) {
        return "satisfy_notnull(\"" + column.getName() + "\"::text)";
    }

    private static String satisfyFk(Table refTable, List<Column> columns, List<Column> refColumns) {
        String sql = "satisfy_fk('" + refTable.getName() + "'::text,";
        sql += "ARRAY[" + columnsToQuotedString(refColumns) + "]::text[],";
        sql += "ARRAY[" + columnsToDoubleQuotedString(columns) + "]::text[])";
        return sql;
    }

    private static String columnsToQuotedString(List<Column> columns) {
        return columnsToString(columns, "'");
    }

    private static String columnsToDoubleQuotedString(List<Column> columns) {
        return columnsToString(columns, "\"");
    }

    private static String columnsToString(List<Column> columns) {
        return columnsToString(columns, "");
    }

    private static String columnsToString(List<Column> columns, String quote) {
        String colNames = "";
        Iterator<Column> iter = columns.iterator();
        while (iter.hasNext()) {
            colNames += quote + iter.next().getName() + quote;
            if (iter.hasNext()) {
                colNames += ",";
            }
        }
        return colNames;
    }

}
