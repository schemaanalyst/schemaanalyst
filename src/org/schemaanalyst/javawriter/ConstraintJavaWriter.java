package org.schemaanalyst.javawriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.sqlrepresentation.constraint.ConstraintVisitor;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

import static org.schemaanalyst.javawriter.MethodNameConstants.*;

public class ConstraintJavaWriter {

    protected JavaWriter javaWriter;
    protected ExpressionJavaWriter expressionJavaWriter;

    public ConstraintJavaWriter(JavaWriter javaWriter,
            ExpressionJavaWriter expressionJavaWriter) {
        this.javaWriter = javaWriter;
        this.expressionJavaWriter = expressionJavaWriter;
    }

    public String writeAdditionToTable(Table table, Constraint constraint) {

        class WriterContraintVisitor implements ConstraintVisitor {

            Table table;
            String methodName;
            List<String> args;

            String writeConstraint(Table table, Constraint constraint) {
                this.table = table;
                args = new ArrayList<>();
                if (constraint.hasName()) {
                    args.add(javaWriter.writeString(constraint.getName()));
                }
                constraint.accept(this);
                return javaWriter.writeTableMethodCall(table, methodName, args);
            }

            @Override
            public void visit(CheckConstraint constraint) {
                methodName = TABLE_CREATE_CHECK_CONSTRAINT_METHOD;
                args.add(expressionJavaWriter.writeConstruction(constraint.getExpression()));
            }

            @Override
            public void visit(ForeignKeyConstraint constraint) {
                methodName = TABLE_CREATE_FOREIGN_KEY_CONSTRAINT_METHOD;

                args.add(wrapColumnArgsString(table, constraint.getColumns()));
                args.add(javaWriter.getVariableName(constraint.getReferenceTable()));
                args.add(wrapColumnArgsString(constraint.getReferenceTable(), constraint.getReferenceColumns()));
            }

            @Override
            public void visit(NotNullConstraint constraint) {
                methodName = TABLE_CREATE_NOT_NULL_CONSTRAINT_METHOD;
                args.add(javaWriter.writeGetColumn(table, constraint.getColumn()));
            }

            @Override
            public void visit(PrimaryKeyConstraint constraint) {
                methodName = TABLE_CREATE_PRIMARY_KEY_CONSTRAINT_METHOD;
                addColumnArgs(table, constraint.getColumns());
            }

            @Override
            public void visit(UniqueConstraint constraint) {
                methodName = TABLE_CREATE_UNIQUE_CONSTRAINT_METHOD;
                addColumnArgs(table, constraint.getColumns());
            }

            void addColumnArgs(Table table, List<Column> columns) {
                args.addAll(makeColumnArgsList(table, columns));
            }

            List<String> makeColumnArgsList(Table table, List<Column> columns) {
                List<String> columnArgs = new ArrayList<>();
                for (Column column : columns) {
                    columnArgs.add(javaWriter.writeGetColumn(table, column));
                }
                return columnArgs;
            }

            String wrapColumnArgsString(Table table, List<Column> columns) {
                String columnArgsString = makeColumnArgsString(table, columns);
                if (columns.size() > 1) {
                    javaWriter.addImportFor(Arrays.class);
                    return javaWriter.writeMethodCall(
                            Arrays.class.getSimpleName(),
                            "asList",
                            columnArgsString);
                } else {
                    return columnArgsString;
                }
            }

            String makeColumnArgsString(Table table, List<Column> columns) {
                List<String> columnArgs = makeColumnArgsList(table, columns);
                return javaWriter.writeArgsList(columnArgs);
            }
        }

        return (new WriterContraintVisitor()).writeConstraint(table, constraint);
    }
}
