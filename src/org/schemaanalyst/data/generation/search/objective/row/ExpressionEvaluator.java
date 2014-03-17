package org.schemaanalyst.data.generation.search.objective.row;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionAdapter;

public class ExpressionEvaluator {

    protected Expression expression;

    public ExpressionEvaluator(Expression expression) {
        this.expression = expression;
    }

    public Value evaluate(Row row) {

        class ExpressionDispatcher extends ExpressionAdapter {
            Row row;
            Value value;

            Value dispatch(Row row) {
                this.row = row;
                value = null;
                expression.accept(this);
                return value;
            }

            @Override
            public void visit(ColumnExpression expression) {
                value = row.getCell(expression.getColumn()).getValue();
            }

            @Override
            public void visit(ConstantExpression expression) {
                value = expression.getValue();
            }
        }

        return (new ExpressionDispatcher()).dispatch(row);
    }
}
