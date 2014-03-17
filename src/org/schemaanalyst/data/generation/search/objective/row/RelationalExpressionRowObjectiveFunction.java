package org.schemaanalyst.data.generation.search.objective.row;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.value.RelationalValueObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

public class RelationalExpressionRowObjectiveFunction extends ObjectiveFunction<Row> {

    private ExpressionEvaluator lhsEvaluator, rhsEvaluator;
    private RelationalOperator op;
    private boolean allowNull;
    
    public RelationalExpressionRowObjectiveFunction(RelationalExpression expression,
                                                 boolean goalIsToSatisfy,
                                                 boolean allowNull) {        
        // set up evaluators for the LHS and RHS
        lhsEvaluator = new ExpressionEvaluator(expression.getLHS());
        rhsEvaluator = new ExpressionEvaluator(expression.getRHS());

        // resolve relational operator
        RelationalOperator op = expression.getRelationalOperator();
        this.op = goalIsToSatisfy ? op : op.inverse();

        // set allowNull
        this.allowNull = allowNull;       
    }

    @Override
    public ObjectiveValue evaluate(Row row) {
        Value lhsValue = lhsEvaluator.evaluate(row);
        Value rhsValue = rhsEvaluator.evaluate(row);         
        return RelationalValueObjectiveFunction.compute(lhsValue, op, rhsValue, allowNull);
    }
}
