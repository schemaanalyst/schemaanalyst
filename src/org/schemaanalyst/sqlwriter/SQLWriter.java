package org.schemaanalyst.sqlwriter;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.sqlrepresentation.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.UniqueConstraint;
import org.schemaanalyst.util.IndentableStringBuilder;

public class SQLWriter {

    protected DataTypeSQLWriter dataTypeSQLWriter;
    protected ConstraintSQLWriter constraintSQLWriter;
    protected OperandSQLWriter operandSQLWriter;
    protected ExpressionSQLWriter expressionSQLWriter;
    protected CheckConditionSQLWriter checkConditionSQLWriter;
    protected CellSQLWriter cellSQLWriter;
    protected ValueSQLWriter valueSQLWriter;

    public SQLWriter() {
        instanitateSubWriters();
        setupSubWriters();
    }

    protected void instanitateSubWriters() {
        dataTypeSQLWriter = new DataTypeSQLWriter();
        constraintSQLWriter = new ConstraintSQLWriter();
        operandSQLWriter = new OperandSQLWriter();
        expressionSQLWriter = new ExpressionSQLWriter();
        checkConditionSQLWriter = new CheckConditionSQLWriter();
        cellSQLWriter = new CellSQLWriter();
        valueSQLWriter = new ValueSQLWriter();
    }

    protected void setupSubWriters() {
        cellSQLWriter.setValueSQLWriter(valueSQLWriter);

        constraintSQLWriter.setCheckConditionSQLWriter(checkConditionSQLWriter);

        constraintSQLWriter.setExpressionSQLWriter(expressionSQLWriter);
        expressionSQLWriter.setValueSQLWriter(valueSQLWriter);

        checkConditionSQLWriter.setOperandSQLWriter(operandSQLWriter);
        operandSQLWriter.setValueSQLWriter(valueSQLWriter);
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

        List<Table> tables = schema.getTables();
        for (Table table : tables) {
            statements.add(writeCreateTableStatement(table));
        }
        return statements;
    }

    public String writeCreateTableStatement(Table table) {

        IndentableStringBuilder sql = new IndentableStringBuilder();
        sql.append("CREATE TABLE ");
        sql.append(table.getName());
        sql.appendln(" (");

        boolean first = true;
        for (Column column : table.getColumns()) {
            if (first) {
                first = false;
            } else {
                sql.appendln(0, ",");
            }

            // write column name
            sql.append(1, column.getName());

            // write column type			
            sql.appendTabbed(dataTypeSQLWriter.writeDataType(column));

            // write column constraints
            PrimaryKeyConstraint primaryKey = table.getPrimaryKeyConstraint();
            if (primaryKey != null && !primaryKey.hasMultipleColumns() && primaryKey.involvesColumn(column)) {
                sql.appendTabbed(constraintSQLWriter.writeConstraint(primaryKey));
            }

            for (ForeignKeyConstraint foreignKey : table.getForeignKeyConstraints()) {
                if (!foreignKey.hasMultipleColumns() && foreignKey.involvesColumn(column)) {
                    sql.appendTabbed(constraintSQLWriter.writeConstraint(foreignKey));
                }
            }

            for (UniqueConstraint unique : table.getUniqueConstraints()) {
                if (!unique.hasMultipleColumns() && unique.involvesColumn(column)) {
                    sql.appendTabbed(constraintSQLWriter.writeConstraint(unique));
                }
            }

            for (NotNullConstraint notNull : table.getNotNullConstraints()) {
                if (notNull.getColumn().equals(column)) {
                    sql.appendTabbed(constraintSQLWriter.writeConstraint(notNull));
                }
            }
        }

        // write primary key
        PrimaryKeyConstraint primaryKey = table.getPrimaryKeyConstraint();
        if (primaryKey != null && primaryKey.hasMultipleColumns()) {
            sql.appendln(0, ",");
            sql.append(1, constraintSQLWriter.writeConstraint(primaryKey));
        }

        // write foreign keys
        for (ForeignKeyConstraint foreignKey : table.getForeignKeyConstraints()) {
            if (foreignKey.hasMultipleColumns()) {
                sql.appendln(0, ",");
                sql.append(1, constraintSQLWriter.writeConstraint(foreignKey));
            }
        }

        // write unique constraints
        for (UniqueConstraint unique : table.getUniqueConstraints()) {
            if (unique.hasMultipleColumns()) {
                sql.appendln(0, ",");
                sql.append(1, constraintSQLWriter.writeConstraint(unique));
            }
        }

        // write check constraints
        for (CheckConstraint check : table.getCheckConstraints()) {
            sql.appendln(0, ",");
            sql.append(1, constraintSQLWriter.writeConstraint(check));
        }

        sql.appendln(0);
        sql.append(")");
        return sql.toString();
    }

    public String writeInsertStatement(Table table, List<Column> columns, List<String> values) {
        return "INSERT INTO " + table.getName()
                + "(" + SQLWriter.writeColumnList(columns) + ") "
                + "VALUES(" + SQLWriter.writeSeparatedList(values) + ")";
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

    public List<String> writeInsertStatements(Data data) {
        List<String> statements = new ArrayList<>();

        List<Table> tables = data.getTables();
        tables = Schema.orderByDependencies(tables);

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
        sql += table.getName();
        return sql;
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
            columnStrings.add(column.getName());
        }
        return SQLWriter.writeSeparatedList(columnStrings);
    }
}
