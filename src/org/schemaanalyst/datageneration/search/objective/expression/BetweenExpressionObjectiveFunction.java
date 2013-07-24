package org.schemaanalyst.datageneration.search.objective.expression;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.relationalpredicate.ValueObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.logic.RelationalPredicate;
import org.schemaanalyst.sqlrepresentation.expression.BetweenExpression;

public class BetweenExpressionObjectiveFunction extends ObjectiveFunction<Row> {

    protected ExpressionEvaluator subjectExpression, lhsExpression, rhsExpression;
    protected ValueObjectiveFunction lhsObjFun, rhsObjFun;
    protected RelationalOperator lhsOp, rhsOp;
    protected boolean goalIsToSatisfy;

    public BetweenExpressionObjectiveFunction(BetweenExpression expression,
            boolean goalIsToSatisfy,
            boolean allowNull) {
        this.goalIsToSatisfy = goalIsToSatisfy;

        subjectExpression = new ExpressionEvaluator(expression.getSubject());
        lhsExpression = new ExpressionEvaluator(expression.getLHS());
        rhsExpression = new ExpressionEvaluator(expression.getRHS());

        lhsOp = RelationalOperator.GREATER_OR_EQUALS;
        rhsOp = RelationalOperator.LESS_OR_EQUALS;

        if (!goalIsToSatisfy) {
            lhsOp = lhsOp.inverse();
            rhsOp = rhsOp.inverse();
        }

        lhsObjFun = new ValueObjectiveFunction();
        rhsObjFun = new ValueObjectiveFunction();
    }

    @Override
    public ObjectiveValue evaluate(Row row) {
        MultiObjectiveValue objVal =
                goalIsToSatisfy ? new SumOfMultiObjectiveValue()
                : new BestOfMultiObjectiveValue();

        Value subjectValue = subjectExpression.evaluate(row);

        objVal.add(lhsObjFun.evaluate(
                new RelationalPredicate<>(
                subjectValue,
                lhsOp,
                lhsExpression.evaluate(row))));

        objVal.add(lhsObjFun.evaluate(
                new RelationalPredicate<>(
                subjectValue,
                rhsOp,
                rhsExpression.evaluate(row))));

        return objVal;
    }
}
