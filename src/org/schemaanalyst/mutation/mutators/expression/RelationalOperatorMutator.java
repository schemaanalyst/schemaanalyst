/*
 */
package org.schemaanalyst.mutation.mutators.expression;

import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

/**
 *
 * @author Chris J. Wright
 */
public class RelationalOperatorMutator extends AbstractMutator {

    @Override
    public void visit(RelationalExpression expression) {
        super.visit(expression);
        RelationalOperator relOp = expression.getRelationalOperator();
        for (RelationalOperator altRelOp : RelationalOperator.values()) {
            if (relOp != altRelOp) {
                mutants.add(new RelationalExpression(expression.getLHS(), altRelOp, expression.getRHS()));
            }
        }
    }
}
