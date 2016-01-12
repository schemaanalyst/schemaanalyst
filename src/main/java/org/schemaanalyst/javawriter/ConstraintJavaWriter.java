package org.schemaanalyst.javawriter;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.schemaanalyst.javawriter.MethodNameConstants.*;

public class ConstraintJavaWriter {

    protected JavaWriter javaWriter;
    protected ExpressionJavaWriter expressionJavaWriter;

    public ConstraintJavaWriter(JavaWriter javaWriter,
            ExpressionJavaWriter expressionJavaWriter) {
        this.javaWriter = javaWriter;
        this.expressionJavaWriter = expressionJavaWriter;
    }

    public String writeAdditionToTable(Constraint constraint) {

        class WriterContraintVisitor implements ConstraintVisitor {

            String methodName;
            List<String> args;

            String writeConstraint(Constraint constraint) {
                args = new ArrayList<>();
                if (constraint.hasIdentifier() && constraint.getIdentifier().get() != null) {
                    args.add(javaWriter.writeString(constraint.getName()));
                }
                args.add(javaWriter.getVariableName(constraint.getTable()));
                constraint.accept(this);
                return javaWriter.writeMethodCall("this", methodName, args);
            }

            @Override
            public void visit(CheckConstraint constraint) {
                methodName = SCHEMA_CREATE_CHECK_CONSTRAINT_METHOD;
                args.add(expressionJavaWriter.writeConstruction(constraint.getExpression()));
            }

            @Override
            public void visit(ForeignKeyConstraint constraint) {
                methodName = SCHEMA_CREATE_FOREIGN_KEY_CONSTRAINT_METHOD;

                args.add(wrapColumnArgsString(constraint.getTable(), constraint.getColumns()));
                args.add(javaWriter.getVariableName(constraint.getReferenceTable()));
                args.add(wrapColumnArgsString(constraint.getReferenceTable(), constraint.getReferenceColumns()));
            }

            @Override
            public void visit(NotNullConstraint constraint) {
                methodName = SCHEMA_CREATE_NOT_NULL_CONSTRAINT_METHOD;
                args.add(javaWriter.writeGetColumn(constraint.getTable(), constraint.getColumn()));
            }

            @Override
            public void visit(PrimaryKeyConstraint constraint) {
                methodName = SCHEMA_CREATE_PRIMARY_KEY_CONSTRAINT_METHOD;
                addColumnArgs(constraint.getTable(), constraint.getColumns());
            }

            @Override
            public void visit(UniqueConstraint constraint) {
                methodName = SCHEMA_CREATE_UNIQUE_CONSTRAINT_METHOD;
                addColumnArgs(constraint.getTable(), constraint.getColumns());
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

        return (new WriterContraintVisitor()).writeConstraint(constraint);
    }
}
