/*
 */
package org.schemaanalyst.mutation.mutators.expression;

import java.util.ArrayList;
import java.util.List;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;

/**
 *
 * @author Chris J. Wright
 */
public class InDeletionMutator extends AbstractMutator {

    @Override
    public void visit(InExpression expression) {
        super.visit(expression);
        Expression rhs = expression.getRHS();
        if (rhs instanceof ListExpression) {
            ListExpression listExpr = (ListExpression) rhs;
            for (Expression subExpr : listExpr.getSubexpressions()) {
                List<Expression> subExprs = new ArrayList<>(listExpr.getSubexpressions());
                subExprs.remove(subExpr);
                mutants.add(new InExpression(expression.getLHS(), new ListExpression(subExprs), expression.isNotIn()));
            }
        }
    }
}
