/*
 */
package deprecated.mutation.mutators.expression;

import java.util.ArrayList;
import java.util.List;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;

/**
 *
 * @author Chris J. Wright
 */
public class InDeletionMutator extends AbstractMutator {

    @Override
    public void visit(ListExpression expression) {
        super.visit(expression);
        for (Expression subExpr : expression.getSubexpressions()) {
            List<Expression> subExprs = new ArrayList<>(expression.getSubexpressions());
            subExprs.remove(subExpr);
            mutants.add(new ListExpression(subExprs));
        }
    }
}
