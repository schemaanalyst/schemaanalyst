package org.schemaanalyst.javawriter;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.*;

import java.util.ArrayList;
import java.util.List;

public class ExpressionJavaWriter {

    protected JavaWriter javaWriter;
    protected ValueJavaWriter valueJavaWriter;

    public ExpressionJavaWriter(JavaWriter javaWriter,
            ValueJavaWriter valueJavaWriter) {
        this.javaWriter = javaWriter;
        this.valueJavaWriter = valueJavaWriter;
    }

    public String writeConstruction(Expression expression) {

        class WriterExpressionVisitor implements ExpressionVisitor {

            String java;

            public String writeExpression(Expression expression) {
                java = "";
                expression.accept(this);
                return java;
            }

            @Override
            public void visit(AndExpression expression) {
                java += writeComposedExpresionConstruction(expression);
            }

            @Override
            public void visit(BetweenExpression expression) {
                java = javaWriter.writeConstruction(
                        expression,
                        writeExpression(expression.getSubject()),
                        writeExpression(expression.getLHS()),
                        writeExpression(expression.getRHS()),
                        javaWriter.writeBoolean(expression.isNotBetween()),
                        javaWriter.writeBoolean(expression.isSymmetric()));
            }

            @Override
            public void visit(ColumnExpression expression) {
                Table table = expression.getTable();
                Column column = expression.getColumn();
                java += javaWriter.writeConstruction(
                        expression,
                        javaWriter.getVariableName(table),
                        javaWriter.writeGetColumn(table, column));
            }

            @Override
            public void visit(ConstantExpression expression) {
                java += javaWriter.writeConstruction(
                        expression,
                        valueJavaWriter.writeConstruction(expression.getValue()));
            }

            @Override
            public void visit(InExpression expression) {
                java = javaWriter.writeConstruction(
                        expression,
                        writeExpression(expression.getLHS()),
                        writeExpression(expression.getRHS()),
                        javaWriter.writeBoolean(expression.isNotIn()));
            }

            @Override
            public void visit(ListExpression expression) {
                java += writeComposedExpresionConstruction(expression);
            }

            @Override
            public void visit(NullExpression expression) {
                java = javaWriter.writeConstruction(
                        expression,
                        writeExpression(expression.getSubexpression()),
                        javaWriter.writeBoolean(expression.isNotNull()));
            }

            @Override
            public void visit(OrExpression expression) {
                java += writeComposedExpresionConstruction(expression);
            }

            @Override
            public void visit(ParenthesisedExpression expression) {
                java = javaWriter.writeConstruction(
                        expression,
                        writeExpression(expression.getSubexpression()));
            }

            @Override
            public void visit(RelationalExpression expression) {
                java = javaWriter.writeConstruction(
                        expression,
                        writeExpression(expression.getLHS()),
                        javaWriter.writeEnumValue(expression.getRelationalOperator()),
                        writeExpression(expression.getRHS()));
            }
        }

        return (new WriterExpressionVisitor()).writeExpression(expression);
    }

    String writeComposedExpresionConstruction(CompoundExpression expression) {
        List<String> args = new ArrayList<>();
        for (Expression subexpression : expression.getSubexpressions()) {
            args.add(writeConstruction(subexpression));
        }
        return javaWriter.writeConstruction(expression, args);
    }
}
