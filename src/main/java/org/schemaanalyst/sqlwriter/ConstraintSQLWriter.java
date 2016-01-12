package org.schemaanalyst.sqlwriter;

import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

import static org.schemaanalyst.sqlwriter.SQLWriter.quoteIdentifier;
import static org.schemaanalyst.sqlwriter.SQLWriter.writeColumnList;

public class ConstraintSQLWriter {

    protected ExpressionSQLWriter expressionSQLWriter;

    public void setExpressionSQLWriter(ExpressionSQLWriter expressionSQLWriter) {
        this.expressionSQLWriter = expressionSQLWriter;
    }

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

        String sql = writeConstraintName(constraint);
        sql += (new ConstraintSQLWriterVisitor()).writeConstraint(constraint);
        return sql;
    }

    public String writeCheck(CheckConstraint check) {
        Expression expression = check.getExpression();
        return "CHECK (" + expressionSQLWriter.writeExpression(expression) + ")";
    }

    public String writeForeignKey(ForeignKeyConstraint foreignKey) {
        String sql = "";
        if (foreignKey.hasMultipleColumns()) {
            sql += "FOREIGN KEY (" + writeColumnList(foreignKey.getColumns()) + ")";
        }
        sql += " REFERENCES " + quoteIdentifier(foreignKey.getReferenceTable().getName())
                + " (" + SQLWriter.writeColumnList(foreignKey.getReferenceColumns()) + ")";
        return sql;
    }

    public String writeNotNull(NotNullConstraint notNull) {
        return "NOT NULL";
    }

    public String writePrimaryKey(PrimaryKeyConstraint primaryKey) {
        String sql = "PRIMARY KEY";
        if (primaryKey.hasMultipleColumns()) {
            sql += " (" + writeColumnList(primaryKey.getColumns()) + ")";
        }
        return sql;
    }

    public String writeUnique(UniqueConstraint unique) {
        String sql = "UNIQUE";
        if (unique.hasMultipleColumns()) {
            sql += " (" + writeColumnList(unique.getColumns()) + ")";
        }
        return sql;
    }

    public String writeConstraintName(Constraint constraint) {
        if (constraint.hasIdentifier() && constraint.getName() != null) {
            return "CONSTRAINT " + quoteIdentifier(constraint.getName()) + " ";
        } else {
            return "";
        }
    }
}