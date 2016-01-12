package org.schemaanalyst.mutation.analysis.executor.alters.sqlwriter;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.sqlwriter.ExpressionSQLWriter;
import org.schemaanalyst.sqlwriter.SQLWriter;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * A constraint writer that produces ALTER TABLE statements for each constraint.
 *
 * @author Chris J. Wright
 */
public class AlterTableConstraintWriter {

    protected ExpressionSQLWriter expressionSQLWriter = new ExpressionSQLWriter();
    MessageDigest instance = null;

    public String writeConstraint(Constraint constraint) {

        class ConstraintSQLWriterVisitor implements ConstraintVisitor {

            String sql;

            public String writeConstraint(Constraint constraint) {
                sql = "";
                constraint.accept(this);
                return sql;
            }

            @Override
            public void visit(CheckConstraint constraint) {
                sql = writeCheck(constraint);
            }

            @Override
            public void visit(ForeignKeyConstraint constraint) {
                sql = writeForeignKey(constraint);
            }

            @Override
            public void visit(NotNullConstraint constraint) {
                sql = writeNotNull(constraint);
            }

            @Override
            public void visit(PrimaryKeyConstraint constraint) {
                sql = writePrimaryKey(constraint);
            }

            @Override
            public void visit(UniqueConstraint constraint) {
                sql = writeUnique(constraint);
            }

        }
        return (new ConstraintSQLWriterVisitor()).writeConstraint(constraint);
    }

    public String writeCheck(CheckConstraint check) {
        String name = getConstraintName(check);
        return "ADD CONSTRAINT " + name + " CHECK (" + expressionSQLWriter.writeExpression(check.getExpression()) + ")";
    }

    public String writeForeignKey(ForeignKeyConstraint foreignKey) {
        String name = getConstraintName(foreignKey);
        String sql = "ADD CONSTRAINT " + name + " FOREIGN KEY (";
        sql += SQLWriter.writeColumnList(foreignKey.getColumns());
        sql += ") REFERENCES " + foreignKey.getReferenceTable().getName();
        sql += "(" + SQLWriter.writeColumnList(foreignKey.getReferenceColumns()) + ")";
        return sql;
    }

    public String writeNotNull(NotNullConstraint notNull) {
        return "ALTER " + notNull.getColumn().getName() + " SET NOT NULL";
    }

    public String writePrimaryKey(PrimaryKeyConstraint primaryKey) {
        String name = getConstraintName(primaryKey);
        return "ADD CONSTRAINT " + name + " PRIMARY KEY (" + SQLWriter.writeColumnList(primaryKey.getColumns()) + ")";
    }

    public String writeUnique(UniqueConstraint unique) {
        String name = getConstraintName(unique);
        return "ADD CONSTRAINT " + name + " UNIQUE (" + SQLWriter.writeColumnList(unique.getColumns()) + ")";
    }

    public List<String> writeDropConstraint(Constraint constraint) {

        class ConstraintSQLWriterVisitor implements ConstraintVisitor {

            List<String> sql;

            public List<String> writeConstraint(Constraint constraint) {
                sql = new ArrayList<>();
                constraint.accept(this);
                return sql;
            }

            @Override
            public void visit(CheckConstraint constraint) {
                sql.add(writeDropCheck(constraint));
            }

            @Override
            public void visit(ForeignKeyConstraint constraint) {
                sql.add(writeDropForeignKey(constraint));
            }

            @Override
            public void visit(NotNullConstraint constraint) {
                sql.add(writeDropNotNull(constraint));
            }

            @Override
            public void visit(PrimaryKeyConstraint constraint) {
                sql.add(writeDropPrimaryKey(constraint));
                for (Column column : constraint.getColumns()) {
                    sql.add(writeDropNotNull(column.getName()));
                }

            }

            @Override
            public void visit(UniqueConstraint constraint) {
                sql.add(writeDropUnique(constraint));
            }

        }

        return (new ConstraintSQLWriterVisitor()).writeConstraint(constraint);
    }

    private String writeDropCheck(CheckConstraint constraint) {
        return "DROP CONSTRAINT IF EXISTS " + getConstraintName(constraint);
    }

    private String writeDropForeignKey(ForeignKeyConstraint constraint) {
        return "DROP CONSTRAINT IF EXISTS " + getConstraintName(constraint);
    }

    private String writeDropNotNull(NotNullConstraint constraint) {
        return writeDropNotNull(constraint.getColumn().getName());
    }

    private String writeDropNotNull(String column) {
        String sql = "ALTER COLUMN " + column;
        sql += " DROP NOT NULL";
        return sql;
    }

    private String writeDropPrimaryKey(PrimaryKeyConstraint constraint) {
        return "DROP CONSTRAINT IF EXISTS " + getConstraintName(constraint);
    }

    private String writeDropUnique(UniqueConstraint constraint) {
        return "DROP CONSTRAINT IF EXISTS " + getConstraintName(constraint);
    }

    private String getConstraintName(Constraint constraint) {
        try {
            if (instance == null) {
                instance = MessageDigest.getInstance("md5");
            }
            byte[] digest = instance.digest(constraint.toString().getBytes());
            return "constraint" + new BigInteger(1, digest).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
}
