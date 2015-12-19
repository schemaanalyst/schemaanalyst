package org.schemaanalyst.mutation.normalisation;

import java.util.ArrayList;
import java.util.List;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.expression.AndExpression;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionAdapter;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;
import org.schemaanalyst.sqlrepresentation.expression.ParenthesisedExpression;

/**
 * Replace each Check is not null with a not null, including those in ANDs.
 * 
 * @author Chris J. Wright
 */
public class NormaliseCheckIsNotNullToNotNull extends SchemaNormaliser {

    
    @Override
    public Schema normalise(final Schema schema) {
        for (final CheckConstraint check : schema.getCheckConstraints()) {
            ExpressionAdapter visitor = new ExpressionAdapter() {
                @Override
                public void visit(AndExpression expression) {
                    // For any subexpression that is a check is not null expression, replace with not null
                    List<Expression> newSubexpressions = new ArrayList<>();
                    for (Expression subexpression : expression.getSubexpressions()) {
                        subexpression = stripParenthesised(subexpression);
                        if (subexpression instanceof NullExpression) {
                            boolean success = replaceNullExpression((NullExpression) subexpression);
                            if (!success) {
                                newSubexpressions.add(subexpression);
                            }
                        } else {
                            newSubexpressions.add(subexpression);
                        }
                    }
                    if (newSubexpressions.isEmpty()) {
                        // Remove if no subexpressions left
                        schema.removeCheckConstraint(check);
                    } else {
                        // Else set the new subexpressions
                        expression.setSubexpressions(newSubexpressions);
                    }
                }

                @Override
                public void visit(ParenthesisedExpression expression) {
                    expression.getSubexpression().accept(this);
                }

                @Override
                public void visit(NullExpression expression) {
                    // Replace Check is not null with a not null
                    boolean success = replaceNullExpression(expression);
                    if (success) {
                        // Remove the check constraint
                        schema.removeCheckConstraint(check);
                    }
                }

                /**
                 * Attempt to replace a NullExpression with a Not Null constraint
                 * @param expression The NullExpression
                 * @return Whether it was able to be removed
                 */
                private boolean replaceNullExpression(NullExpression expression) {
                    // Only apply if it is "NOT NULL" variant
                    boolean success = false;
                    if (expression.isNotNull()) {
                        Expression subexpression = expression.getSubexpression();
                        // Only apply if constraint refers to a column
                        if (subexpression instanceof ColumnExpression) {
                            success = true;
                            ColumnExpression colExpr = (ColumnExpression) subexpression;
                            Column col = colExpr.getColumn();
                            // Create an equivalent not null, if one doesn't already exist
                            NotNullConstraint constraint = new NotNullConstraint(check.getTable(), col);
                            if (!schema.getNotNullConstraints(check.getTable()).contains(constraint)) {
                                schema.addNotNullConstraint(constraint);
                            }
                        }
                    }
                    return success;
                }
                
                private Expression stripParenthesised(Expression expression) {
                    if (expression instanceof ParenthesisedExpression) {
                        return ((ParenthesisedExpression)expression).getSubexpression();
                    } else {
                        return expression;
                    }
                }
            };
            check.getExpression().accept(visitor);
        }
        return schema;
    }
    
}
