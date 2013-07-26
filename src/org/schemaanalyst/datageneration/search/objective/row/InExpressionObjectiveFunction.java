package org.schemaanalyst.datageneration.search.objective.row;

import java.util.ArrayList;
import java.util.List;

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
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;

public class InExpressionObjectiveFunction extends ObjectiveFunction<Row> {

    protected ExpressionEvaluator lhsExpEvaluator;
    protected List<ExpressionEvaluator> rhsExpsEvaluators;
    protected RelationalOperator op;
    protected boolean evaluateToTrue, allowNull;
    
    public InExpressionObjectiveFunction(InExpression expression,
                                         boolean goalIsToSatisfy,
                                         boolean allowNull) {
        this.evaluateToTrue = (goalIsToSatisfy != expression.isNotIn());
        this.allowNull = allowNull;
        
        lhsExpEvaluator = new ExpressionEvaluator(expression.getLHS());
        
        if (!(expression.getRHS() instanceof ListExpression)) {
            throw new RuntimeException("Can only handle ListExpressions as the RHS of an InExpression");
        }
        
        rhsExpsEvaluators = new ArrayList<ExpressionEvaluator>();
        for (Expression subexpression : ((ListExpression) expression.getRHS()).getSubexpressions()) {
            rhsExpsEvaluators.add(new ExpressionEvaluator(subexpression));
        }
        
        op = evaluateToTrue ? RelationalOperator.EQUALS : RelationalOperator.NOT_EQUALS;
    }
    
    @Override
    public ObjectiveValue evaluate(Row row) {
        MultiObjectiveValue objVal = evaluateToTrue
                ? new BestOfMultiObjectiveValue()
                : new SumOfMultiObjectiveValue();        
        
        Value lhsValue = lhsExpEvaluator.evaluate(row);
        List<Value> rhsValues = new ArrayList<>();
        
        for (ExpressionEvaluator expEvaluator : rhsExpsEvaluators) {
            rhsValues.add(expEvaluator.evaluate(row));
        }
        
        for (Value rhsValue : rhsValues) {
            ValueObjectiveFunction objFun = new ValueObjectiveFunction();
            objVal.add(objFun.evaluate(new RelationalPredicate<>(lhsValue, op, rhsValue)));            
        }
        
        if (allowNull) {
            BestOfMultiObjectiveValue allowNullObjVal = new BestOfMultiObjectiveValue("Allowing for nulls");            
            allowNullObjVal.add(NullValueObjectiveFunction.compute(lhsValue, true));
            for (Value rhsValue : rhsValues) {
                allowNullObjVal.add(NullValueObjectiveFunction.compute(rhsValue, true));
            }
            allowNullObjVal.add(objVal);
            return allowNullObjVal;
        } else {
            return objVal;
        }               
    }
}
