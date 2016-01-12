package org.schemaanalyst.data;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.expression.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by phil on 26/02/2014.
 */
public class ValueMiner {

    public ValueLibrary mine(Schema schema) {
        List<Expression> expressions = new ArrayList<>();
        List<CheckConstraint> checkConstraints = schema.getCheckConstraints();
        for (CheckConstraint checkConstraint : checkConstraints) {
            expressions.add(checkConstraint.getExpression());
        }
        return mine(expressions);
    }

    public ValueLibrary mine(Expression expression) {
        return mine(Arrays.asList(expression));
    }

    public ValueLibrary mine(List<Expression> expressions) {
        class ExpressionValueMiner implements ExpressionVisitor {

            List<ConstantExpression> constantExpressions;

            public List<ConstantExpression> extractConstantExpressions(List<Expression> expressions) {
                constantExpressions = new ArrayList<>();
                visit(expressions);
                return constantExpressions;
            }

            public void visit(List<Expression> expressions) {
                for (Expression expression : expressions) {
                    expression.accept(this);
                }
            }

            @Override
            public void visit(AndExpression expression) {
                visit(expression.getSubexpressions());
            }

            @Override
            public void visit(BetweenExpression expression) {
                visit(expression.getSubexpressions());
            }

            @Override
            public void visit(ColumnExpression expression) {
                visit(expression.getSubexpressions());
            }

            @Override
            public void visit(ConstantExpression expression) {
                constantExpressions.add(expression);
            }

            @Override
            public void visit(InExpression expression) {
                visit(expression.getSubexpressions());
            }

            @Override
            public void visit(ListExpression expression) {
                visit(expression.getSubexpressions());
            }

            @Override
            public void visit(NullExpression expression) {
            }

            @Override
            public void visit(OrExpression expression) {
                visit(expression.getSubexpressions());
            }

            @Override
            public void visit(ParenthesisedExpression expression) {
                visit(expression.getSubexpressions());
            }

            @Override
            public void visit(RelationalExpression expression) {
                visit(expression.getSubexpressions());
            }
        }

        return extractConstantsIntoValueLibrary(
                new ExpressionValueMiner().extractConstantExpressions(expressions));
    }

    private ValueLibrary extractConstantsIntoValueLibrary(List<ConstantExpression> constantExpressions) {
        ValueLibrary valueLibrary = new ValueLibrary();
        for (ConstantExpression constantExpression : constantExpressions) {
            valueLibrary.addValue(constantExpression.getValue());
        }
        return valueLibrary;
    }
}


