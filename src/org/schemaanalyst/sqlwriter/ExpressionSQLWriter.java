package org.schemaanalyst.sqlwriter;

import org.schemaanalyst.data.Value;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.expression.AndExpression;
import org.schemaanalyst.sqlrepresentation.expression.BetweenExpression;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.CompoundExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionVisitor;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;
import org.schemaanalyst.sqlrepresentation.expression.OrExpression;
import org.schemaanalyst.sqlrepresentation.expression.ParenthesisedExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;
import org.schemaanalyst.util.StringUtils;

public class ExpressionSQLWriter {

    protected ValueSQLWriter valueSQLWriter;

    public void setValueSQLWriter(ValueSQLWriter valueSQLWriter) {
        this.valueSQLWriter = valueSQLWriter;
    }

    public String writeExpression(Expression expression) {

        class ExpressionSQLWriterVisitor implements ExpressionVisitor {

            String sql;

            public String writeExpression(Expression expression) {
                sql = "";
                expression.accept(this);
                return sql;
            }

            @Override
            public void visit(AndExpression expression) {
                sql = writeAndExpression(expression);
            }

            @Override
            public void visit(BetweenExpression expression) {
                sql = writeBetweenExpression(expression);
            }

            @Override
            public void visit(ColumnExpression expression) {
                sql = writeColumn(expression.getColumn());
            }

            @Override
            public void visit(ConstantExpression expression) {
                sql = writeValue(expression.getValue());
            }

            @Override
            public void visit(InExpression expression) {
                sql = writeInExpression(expression);
            }

            @Override
            public void visit(ListExpression expression) {
                sql = writeListExpression(expression);
            }

            @Override
            public void visit(NullExpression expression) {
                sql = writeNullExpression(expression);
            }

            @Override
            public void visit(OrExpression expression) {
                sql = writeOrExpression(expression);
            }

            @Override
            public void visit(ParenthesisedExpression expression) {
                sql = writeParenthesisedExpression(expression);
            }

            @Override
            public void visit(RelationalExpression expression) {
                sql = writeRelationalExpression(expression);
            }
        }

        return (new ExpressionSQLWriterVisitor()).writeExpression(expression);
    }

    public String writeAndExpression(AndExpression expression) {
		return StringUtils.implode(expression.getSubexpressions(), " AND ");	
    }

    public String writeBetweenExpression(BetweenExpression expression) {
        String sql = writeExpression(expression.getSubject());
        if (expression.isNotBetween()) {
            sql += " NOT";
        }
        sql += " BETWEEN ";
        sql += writeExpression(expression.getLHS());
        sql += " AND ";
        sql += writeExpression(expression.getRHS());
        return sql;

    }

    public String writeColumn(Column column) {
        return column.toString();
    }

    public String writeInExpression(InExpression expression) {
        String sql = writeExpression(expression.getLHS());
        if (expression.isNotIn()) {
            sql += " NOT";
        }
        sql += " IN ";
        sql += writeExpression(expression.getRHS());
        return sql;
    }

    public String writeListExpression(ListExpression expression) {
        return "(" + StringUtils.implode(expression.getSubexpressions(), ", ") + ")";
    }

    public String writeNullExpression(NullExpression expression) {
        String sql = writeExpression(expression.getSubexpression());
        sql += " IS ";
        if (expression.isNotNull()) {
            sql += "NOT ";
        }
        sql += "NULL";
        return sql;
    }

    public String writeOrExpression(OrExpression expression) {
		return StringUtils.implode(expression.getSubexpressions(), " OR ");	
    }

    public String writeParenthesisedExpression(ParenthesisedExpression expression) {
        return "(" + writeExpression(expression.getSubexpression()) + ")";
    }

    public String writeRelationalExpression(RelationalExpression expression) {
        String sql = writeExpression(expression.getLHS());
        sql += " " + expression.getRelationalOperator() + " ";
        sql += writeExpression(expression.getRHS());
        return sql;
    }

    public String writeValue(Value expression) {
        return valueSQLWriter.writeValue(expression);
    }
}
