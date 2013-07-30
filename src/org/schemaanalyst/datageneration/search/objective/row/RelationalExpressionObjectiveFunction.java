package org.schemaanalyst.datageneration.search.objective.row;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.value.ValueRelationalObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

public class RelationalExpressionObjectiveFunction extends ObjectiveFunction<Row> {

    private ExpressionEvaluator lhsEvaluator, rhsEvaluator;
    private RelationalOperator op;
    private boolean nullAccepted;
    
    public RelationalExpressionObjectiveFunction(RelationalExpression expression,
                                                 boolean goalIsToSatisfy,
                                                 boolean nullAccepted) {        
        // set up evaluators for the LHS and RHS
        lhsEvaluator = new ExpressionEvaluator(expression.getLHS());
        rhsEvaluator = new ExpressionEvaluator(expression.getRHS());

        // resolve relational operator
        RelationalOperator op = expression.getRelationalOperator();
        this.op = goalIsToSatisfy ? op : op.inverse();

        // set nullAccepted
        this.nullAccepted = nullAccepted;       
    }

    @Override
    public ObjectiveValue evaluate(Row row) {
        Value lhsValue = lhsEvaluator.evaluate(row);
        Value rhsValue = rhsEvaluator.evaluate(row);         
        return ValueRelationalObjectiveFunction.compute(lhsValue, op, rhsValue, nullAccepted);
    }
}
