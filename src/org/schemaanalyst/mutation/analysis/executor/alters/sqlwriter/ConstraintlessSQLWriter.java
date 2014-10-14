package org.schemaanalyst.mutation.analysis.executor.alters.sqlwriter;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.IndentableStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * An SQLWriter that omits constraints, which can be separately produced as
 * ALTER TABLE statements.
 *
 * @author Chris J. Wright
 */
public class ConstraintlessSQLWriter extends SQLWriter {

    AlterTableConstraintWriter constraintWriter = new AlterTableConstraintWriter();

    @Override
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
        }

        sql.appendln(0);
        sql.append(")");
        return sql.toString();
    }

    /**
     * Write the alter table statements for one table in a schema
     *
     * @param schema The schema
     * @param table The table
     * @return The alter statements
     */
    public List<String> writeAlterTableStatements(Schema schema, Table table) {
        List<String> stmts = new ArrayList<>();
        for (Constraint constraint : schema.getConstraints(table)) {
            stmts.add("ALTER TABLE " + table.getName() + " " + constraintWriter.writeConstraint(constraint));
        }
        return stmts;
    }

    /**
     * Write the alter table statements for all tables in a schema
     *
     * @param schema The schema
     * @return The alter statements
     */
    public List<String> writeAlterTableStatements(Schema schema) {
        List<String> stmts = new ArrayList<>();
        for (Table table : schema.getTables()) {
            stmts.addAll(writeAlterTableStatements(schema, table));
        }
        return stmts;
    }

    /**
     * Write the alter table statements for one table in a schema
     *
     * @param schema The schema
     * @param table The table
     * @return The alter statements
     */
    public List<String> writeDropAlterTableStatements(Schema schema, Table table) {
        List<String> stmts = new ArrayList<>();
        for (Constraint constraint : schema.getConstraints(table)) {
            for (String stmt : constraintWriter.writeDropConstraint(constraint)) {
                stmts.add("ALTER TABLE " + table.getName() + " " + stmt);
            }
        }
        return stmts;
    }

    /**
     * Write the alter table statements for all tables in a schema
     *
     * @param schema The schema
     * @return The alter statements
     */
    public List<String> writeDropAlterTableStatements(Schema schema) {
        List<String> stmts = new ArrayList<>();
        for (Table table : schema.getTables()) {
            stmts.addAll(writeDropAlterTableStatements(schema, table));
        }
        return stmts;
    }
}
