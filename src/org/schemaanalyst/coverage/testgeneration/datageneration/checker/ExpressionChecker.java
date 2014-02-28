package org.schemaanalyst.coverage.testgeneration.datageneration.checker;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.expression.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 27/02/2014.
 */
public class ExpressionChecker extends Checker {

    private Expression expression;
    private boolean satisfy, allowNull;
    private Row row;
    private boolean compliant;
    private List<Cell> nonComplyingCells;

    public ExpressionChecker(Expression expression, boolean satisfy, boolean allowNull, Row row) {
        this.expression = expression;
        this.satisfy = satisfy;
        this.allowNull = allowNull;
        this.row = row;
    }

    public List<Cell> getNonComplyingCells() {
        return nonComplyingCells;
    }

    @Override
    public boolean check() {
        compliant = true;
        nonComplyingCells = new ArrayList<>();

        // do the check
        expression.accept(new ExpressionAdapter() {

            void checkSubexpression(Expression expression) {
                ExpressionChecker expressionChecker = new ExpressionChecker(expression, satisfy, allowNull, row);
                expressionChecker.check();
                nonComplyingCells.addAll(getNonComplyingCells());
            }

            void setNonCompliant(Expression expression) {
                compliant = false;
                List<Column> columnsInvolved = expression.getColumnsInvolved();
                for (Column column : columnsInvolved) {
                    nonComplyingCells.add(row.getCell(column));
                }
            }

            @Override
            public void visit(AndExpression expression) {
                for (Expression subexpression : expression.getSubexpressions()) {
                    checkSubexpression(subexpression);
                }
            }

            @Override
            public void visit(BetweenExpression expression) {
                Value subject = new ExpressionEvaluator(expression.getSubject(), row).evaluate();
                Value lhs = new ExpressionEvaluator(expression.getLHS(), row).evaluate();
                Value rhs = new ExpressionEvaluator(expression.getRHS(), row).evaluate();

                boolean lhsResult, rhsResult;

                if (expression.isNotBetween()) {
                    lhsResult = new RelationalChecker(
                            subject,
                            RelationalOperator.LESS,
                            lhs,
                            allowNull).check();

                    rhsResult = new RelationalChecker(
                            subject,
                            RelationalOperator.GREATER,
                            rhs,
                            allowNull).check();
                } else {
                    lhsResult = new RelationalChecker(
                            subject,
                            RelationalOperator.GREATER_OR_EQUALS,
                            lhs,
                            allowNull).check();

                    rhsResult = new RelationalChecker(
                            subject,
                            RelationalOperator.LESS_OR_EQUALS,
                            rhs,
                            allowNull).check();
                }

                if (lhsResult || rhsResult) {
                    setNonCompliant(expression);
                }
            }

            @Override
            public void visit(InExpression expression) {
                Value lhs = new ExpressionEvaluator(expression.getLHS(), row).evaluate();

                List<Value> rhsValues = new ArrayList<>();
                Expression rhs = expression.getRHS();
                if (rhs instanceof ListExpression) {
                    ListExpression listExpression = (ListExpression) rhs;
                    for (Expression subexpression : listExpression.getSubexpressions()) {
                        Value rhsValue = new ExpressionEvaluator(subexpression, row).evaluate();
                        rhsValues.add(rhsValue);
                    }
                } else {
                    throw new CheckerException("Cannot check InExpression without a ListExpression for RHS element");
                }

                boolean result = false;
                for (Value rhsValue : rhsValues) {
                    boolean valueResult = new RelationalChecker(
                            lhs,
                            RelationalOperator.EQUALS,
                            rhsValue,
                            allowNull).check();
                    if (valueResult) {
                        result = true;
                    }
                }

                if (!result) {
                    setNonCompliant(expression);
                }
            }

            @Override
            public void visit(NullExpression expression) {
                Value subject = new ExpressionEvaluator(expression.getSubexpression(), row).evaluate();
                boolean expressionSatisifed = expression.isNotNull() && subject != null;

                if (expressionSatisifed != satisfy) {
                    setNonCompliant(expression);
                }
            }

            @Override
            public void visit(OrExpression expression) {
                for (Expression subexpression : expression.getSubexpressions()) {
                    checkSubexpression(subexpression);
                }
            }

            @Override
            public void visit(ParenthesisedExpression expression) {
                checkSubexpression(expression.getSubexpression());
            }

            @Override
            public void visit(RelationalExpression expression) {
                Value lhs = new ExpressionEvaluator(expression.getLHS(), row).evaluate();
                Value rhs = new ExpressionEvaluator(expression.getRHS(), row).evaluate();

                boolean result = new RelationalChecker(
                        lhs,
                        expression.getRelationalOperator(),
                        rhs,
                        allowNull).check();

                if (!result) {
                    setNonCompliant(expression);
                }
            }
        });

        return compliant;
    }

}
