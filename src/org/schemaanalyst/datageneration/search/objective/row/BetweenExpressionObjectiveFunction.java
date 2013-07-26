package org.schemaanalyst.datageneration.search.objective.row;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.value.NullValueObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.value.ValueObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.logic.RelationalPredicate;
import org.schemaanalyst.sqlrepresentation.expression.BetweenExpression;

public class BetweenExpressionObjectiveFunction extends ObjectiveFunction<Row> {

    protected ExpressionEvaluator subjectExpression, lhsExpression, rhsExpression;
    protected ValueObjectiveFunction lhsObjFun, rhsObjFun;
    protected RelationalOperator lhsOp, rhsOp;
    protected boolean evaluateToTrue, allowNull;

    public BetweenExpressionObjectiveFunction(BetweenExpression expression,
                                              boolean goalIsToSatisfy,
                                              boolean allowNull) {
        this.evaluateToTrue = (goalIsToSatisfy != expression.isNotBetween());
        this.allowNull = allowNull;
        
        subjectExpression = new ExpressionEvaluator(expression.getSubject());
        lhsExpression = new ExpressionEvaluator(expression.getLHS());
        rhsExpression = new ExpressionEvaluator(expression.getRHS());

        lhsOp = RelationalOperator.GREATER_OR_EQUALS;
        rhsOp = RelationalOperator.LESS_OR_EQUALS;

        if (!evaluateToTrue) {
            lhsOp = lhsOp.inverse();
            rhsOp = rhsOp.inverse();
        }

        lhsObjFun = new ValueObjectiveFunction();
        rhsObjFun = new ValueObjectiveFunction();
    }

    @Override
    public ObjectiveValue evaluate(Row row) {
        MultiObjectiveValue objVal = evaluateToTrue
                ? new SumOfMultiObjectiveValue()
                : new BestOfMultiObjectiveValue();

        Value subjectValue = subjectExpression.evaluate(row);
        Value lhsExpValue = lhsExpression.evaluate(row);
        Value rhsExpValue = rhsExpression.evaluate(row);
        
        objVal.add(lhsObjFun.evaluate(
                new RelationalPredicate<>(subjectValue, lhsOp, lhsExpValue)));

        objVal.add(lhsObjFun.evaluate(
                new RelationalPredicate<>(subjectValue, rhsOp, rhsExpValue)));        
        
        if (allowNull) {
            ObjectiveValue subjectNullObjVal = 
                    NullValueObjectiveFunction.compute(subjectValue, true);
            ObjectiveValue lhsNullObjVal = 
                    NullValueObjectiveFunction.compute(lhsExpValue, true);
            ObjectiveValue rhsNullObjVal = 
                    NullValueObjectiveFunction.compute(rhsExpValue, true);
            
            BestOfMultiObjectiveValue allowNullObjVal = 
                    new BestOfMultiObjectiveValue("Allowing for nulls");
            allowNullObjVal.add(subjectNullObjVal);
            allowNullObjVal.add(lhsNullObjVal);
            allowNullObjVal.add(rhsNullObjVal);
            allowNullObjVal.add(objVal);
            return allowNullObjVal;
        } else {
            return objVal;
        }
    }
}
