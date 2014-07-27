package org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionAdapter;

/**
 * Created by phil on 28/02/2014.
 */
public class ExpressionEvaluator {

    private Expression expression;
    private Row row;

    public ExpressionEvaluator(Expression expression, Row row) {
        this.expression = expression;
        this.row = row;
    }

    public Value evaluate() {
        class ValueExtractor extends ExpressionAdapter {
            boolean gotValue;
            Value value;

            void evaluate() {
                gotValue = false;
                expression.accept(this);
            }

            @Override
            public void visit(ColumnExpression expression) {
                gotValue = true;
                value = row.getCell(expression.getColumn()).getValue();
            }

            @Override
            public void visit(ConstantExpression expression) {
                gotValue = true;
                value = expression.getValue();
            }
        }

        ValueExtractor valueExtractor = new ValueExtractor();
        valueExtractor.evaluate();
        if (valueExtractor.gotValue) {
            return valueExtractor.value;
        } else {
            throw new CheckerException("Could not extract value from expression for checking in " + expression);
        }
    }
}
