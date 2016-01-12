package org.schemaanalyst.sqlwriter;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.TableDependencyOrderer;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.util.IndentableStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * An SQLWriter converts the SchemaAnalyst internal representation of SQL into 
 * SQL statements. This includes writing CREATE, DROP and INSERT statements.
 * </p>
 * 
 * <p>
 * This class writes SQL suitable for numerous DBMSs, however specialised 
 * classes are provided for specific DBMSs where it is necessary to format 
 * statements differently.
 * </p>
 */
public class SQLWriter {

    protected DataTypeSQLWriter dataTypeSQLWriter;
    protected ConstraintSQLWriter constraintSQLWriter;
    protected ExpressionSQLWriter expressionSQLWriter;
    protected CellSQLWriter cellSQLWriter;
    protected ValueSQLWriter valueSQLWriter;

    public SQLWriter() {
        instanitateSubWriters();
        setupSubWriters();
    }

    protected void instanitateSubWriters() {
        dataTypeSQLWriter = new DataTypeSQLWriter();
        constraintSQLWriter = new ConstraintSQLWriter();
        expressionSQLWriter = new ExpressionSQLWriter();
        cellSQLWriter = new CellSQLWriter();
        valueSQLWriter = new ValueSQLWriter();
    }

    protected void setupSubWriters() {
        cellSQLWriter.setValueSQLWriter(valueSQLWriter);

        constraintSQLWriter.setExpressionSQLWriter(expressionSQLWriter);
        expressionSQLWriter.setValueSQLWriter(valueSQLWriter);
    }

    public List<String> writeComments(List<String> comments) {
        List<String> statements = new ArrayList<>();

        for (String comment : comments) {
            statements.add(writeComment(comment));
        }

        return statements;
    }

    public String writeComment(String comment) {
        return "-- " + comment;
    }

    public List<String> writeCreateTableStatements(Schema schema) {
        List<String> statements = new ArrayList<>();

        List<Table> tables = schema.getTablesInOrder();
        for (Table table : tables) {
            statements.add(writeCreateTableStatement(schema, table));
        }
        return statements;
    }

    public String writeCreateTableStatement(Schema schema, Table table) {

        IndentableStringBuilder sql = new IndentableStringBuilder();
        sql.append("CREATE TABLE ");
        sql.append(quoteIdentifier(table.getName()));
        sql.appendln(" (");

        boolean first = true;
        for (Column column : table.getColumns()) {
            if (first) {
                first = false;
            } else {
                sql.appendln(0, ",");
            }

            // write column name
            sql.append(1, quoteIdentifier(column.getName()));

            // write column type			
            sql.appendTabbed(dataTypeSQLWriter.writeDataType(column));

            // write column constraints
            PrimaryKeyConstraint primaryKey = schema.getPrimaryKeyConstraint(table);
            if (primaryKey != null && !primaryKey.hasMultipleColumns() && primaryKey.involvesColumn(column)) {
                sql.appendTabbed(constraintSQLWriter.writeConstraint(primaryKey));
            }

            for (ForeignKeyConstraint foreignKey : schema.getForeignKeyConstraints(table)) {
                if (!foreignKey.hasMultipleColumns() && foreignKey.involvesColumn(column)) {
                    sql.appendTabbed(constraintSQLWriter.writeConstraint(foreignKey));
                }
            }

            boolean hasUnique = false;
            for (UniqueConstraint unique : schema.getUniqueConstraints(table)) {
                if (!hasUnique && !unique.hasMultipleColumns() && unique.involvesColumn(column)) {
                    sql.appendTabbed(constraintSQLWriter.writeConstraint(unique));
                    hasUnique = true;
                }
            }

            for (NotNullConstraint notNull : schema.getNotNullConstraints(table)) {
                if (notNull.getColumn().equals(column)) {
                    sql.appendTabbed(constraintSQLWriter.writeConstraint(notNull));
                }
            }
        }

        // write primary key
        PrimaryKeyConstraint primaryKey = schema.getPrimaryKeyConstraint(table);
        if (primaryKey != null && primaryKey.hasMultipleColumns()) {
            sql.appendln(0, ",");
            sql.append(1, constraintSQLWriter.writeConstraint(primaryKey));
        }

        // write foreign keys
        for (ForeignKeyConstraint foreignKey : schema.getForeignKeyConstraints(table)) {
            if (foreignKey.hasMultipleColumns()) {
                sql.appendln(0, ",");
                sql.append(1, constraintSQLWriter.writeConstraint(foreignKey));
            }
        }

        // write unique constraints
        for (UniqueConstraint unique : schema.getUniqueConstraints(table)) {
            if (unique.hasMultipleColumns()) {
                sql.appendln(0, ",");
                sql.append(1, constraintSQLWriter.writeConstraint(unique));
            }
        }

        // write check constraints
        for (CheckConstraint check : schema.getCheckConstraints(table)) {
            sql.appendln(0, ",");
            sql.append(1, constraintSQLWriter.writeConstraint(check));
        }

        sql.appendln(0);
        sql.append(")");
        return sql.toString();
    }

    public String writeInsertStatement(Table table, List<Column> columns, List<String> values) {

        IndentableStringBuilder sql = new IndentableStringBuilder();
        sql.appendln("INSERT INTO " + quoteIdentifier(table.getName()) + "(");
        sql.appendln(1, SQLWriter.writeColumnList(columns));
        sql.appendln(0, ") VALUES (");
        sql.appendln(1, SQLWriter.writeSeparatedList(values));
        sql.appendln(0, ")");
        return sql.toString();
    }

    public String writeInsertStatement(Row row) {
        Table table = row.getTable();
        List<Column> columns = table.getColumns();
        List<String> valueStrings = new ArrayList<>();

        for (Cell cell : row.getCells()) {
            String string = cellSQLWriter.writeCell(cell);
            valueStrings.add(string);
        }

        return writeInsertStatement(table, columns, valueStrings);
    }

    public List<String> writeInsertStatements(Schema schema, Data data) {
        List<String> statements = new ArrayList<>();

        List<Table> tables = new TableDependencyOrderer().order(data.getTables(), schema);

        for (Table table : tables) {
            List<Row> rows = data.getRows(table);
            if (rows != null) {
                for (Row row : data.getRows(table)) {
                    statements.add(writeInsertStatement(row));
                }
            }
        }
        return statements;
    }

    public List<String> writeDeleteFromTableStatements(Schema schema) {
        List<String> statements = new ArrayList<>();

        List<Table> tables = schema.getTablesInReverseOrder();
        for (Table table : tables) {
            statements.add(writeDeleteFromTableStatement(table));
        }
        return statements;
    }

    public String writeDeleteFromTableStatement(Table table) {
        return "DELETE FROM " + quoteIdentifier(table.getName());
    }

    public List<String> writeDropTableStatements(Schema schema) {
        return writeDropTableStatements(schema, false);
    }

    public List<String> writeDropTableStatements(Schema schema, boolean addIfExists) {
        List<String> statements = new ArrayList<>();

        List<Table> tables = schema.getTablesInReverseOrder();
        for (Table table : tables) {
            statements.add(writeDropTableStatement(table, addIfExists));
        }
        return statements;
    }


    public String writeDropTableStatement(Table table) {
        return writeDropTableStatement(table, false);
    }

    public String writeDropTableStatement(Table table, boolean addIfExists) {
        String sql = "DROP TABLE ";
        if (addIfExists) {
            sql += "IF EXISTS ";
        }
        sql += quoteIdentifier(table.getName());
        return sql;
    }

    public static String quoteIdentifier(String identifier) {
        return "\"" + identifier + "\"";
    }

    public static String writeSeparatedList(List<String> values) {
        StringBuilder sql = new StringBuilder();
        boolean first = true;
        for (String value : values) {
            if (first) {
                first = false;
            } else {
                sql.append(", ");
            }
            sql.append(value);
        }
        return sql.toString();
    }

    public static String writeColumnList(List<Column> columns) {
        List<String> columnStrings = new ArrayList<>();
        for (Column column : columns) {
            columnStrings.add(quoteIdentifier(column.getName()));
        }
        return SQLWriter.writeSeparatedList(columnStrings);
    }
}
