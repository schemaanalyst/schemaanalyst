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
import org.schemaanalyst.sqlrepresentation.expression.BetweenExpression;

/**
 *  Evaluates a BetweenExpression by considering:  
 *  - X BETWEEN Y AND Z as the raw predicate ((X >= Y) AND (X <= Z)) -- the so-called "True Form"
 *  - X NOT BETWEEN Y AND Z as the raw predicate ((X < Y) OR (X > Z)) -- the so-called "False Form".
 *  
 *  where:
 *  - X is the result of the BetweenExpression's subjectExpression
 *  - Y is the result of the BetweenExpression's lhsExpression
 *  - Z is the result of the BetweenExpression's rhsExpression
 *  
 *  In each case, the X/Y and X/Z comparisons are referred to as the LHS and RHS comparisons respectively.
 *  
 */
public class BetweenExpressionObjectiveFunction extends ObjectiveFunction<Row> {

    // evaluators for different parts of the expression 
    private ExpressionEvaluator subjectEvaluator, lhsEvaluator, rhsEvaluator;
    
    // the relational operators for the LHS and RHS comparisons (i.e. >= or < for lhsOp)
    private RelationalOperator lhsOp, rhsOp;
    
    // "evaluateTrueForm" is true when the satisfaction goal is the raw predicate ((X >= Y) AND (X <= Z))
    // else false for its counterpart ((X < Y) OR (X > Z))
    private boolean evaluateTrueForm;
    
    // whether the involvement of NULL results in trivial satisfaction of the expression
    private boolean allowNull;   
    
    // a string descriptor for this objective function
    private String description;
    
    public BetweenExpressionObjectiveFunction(BetweenExpression expression,
                                              boolean goalIsToSatisfy,
                                              boolean allowNull) {
        // which form to evaluate?
        this.evaluateTrueForm = (goalIsToSatisfy != expression.isNotBetween());
        
        // is NULL allowed to satisfy the expression
        this.allowNull = allowNull;
        
        // get evaluators for each of the three sub-expressions involved 
        subjectEvaluator = new ExpressionEvaluator(expression.getSubject());
        lhsEvaluator = new ExpressionEvaluator(expression.getLHS());
        rhsEvaluator = new ExpressionEvaluator(expression.getRHS());

        // find out which operators of needed for the LHS and RHS comparisons
        if (evaluateTrueForm) {
            lhsOp = RelationalOperator.GREATER_OR_EQUALS;
            rhsOp = RelationalOperator.LESS_OR_EQUALS;
        } else {
            lhsOp = RelationalOperator.LESS;
            rhsOp = RelationalOperator.GREATER;
        }
        
        // make a descriptor string
        description = expression.toString() 
                + " goalIsToSatisfy: " + goalIsToSatisfy
                + " allowNull: " + allowNull;
    }

    @Override
    public ObjectiveValue evaluate(Row row) {

        // "SumOf" for AND ("True Form"), "Best Of" for OR ("False Form").
        MultiObjectiveValue objVal = evaluateTrueForm
                ? new SumOfMultiObjectiveValue()
                : new BestOfMultiObjectiveValue();

        // evaluate each subexpression to a value
        Value subjectValue = subjectEvaluator.evaluate(row);
        Value lhsValue = lhsEvaluator.evaluate(row);
        Value rhsValue = rhsEvaluator.evaluate(row);
        
        // swap the values for Y and Z (LHS and RHS) if they're in the wrong order
        if (lhsValue.compareTo(rhsValue) > 0) {
            Value temp = lhsValue;
            lhsValue = rhsValue;
            rhsValue = temp;
        }
        
        // add objective values for the two comparisons
        objVal.add(ValueObjectiveFunction.compute(subjectValue, lhsOp, lhsValue));
        objVal.add(ValueObjectiveFunction.compute(subjectValue, rhsOp, rhsValue));                
        
        // if NULL is allowed, there are extra considerations to make ...
        if (allowNull) {
            // compute objective values for NULL for each expression value
            ObjectiveValue subjectNullObjVal = 
                    NullValueObjectiveFunction.compute(subjectValue, true);
            ObjectiveValue lhsNullObjVal = 
                    NullValueObjectiveFunction.compute(lhsValue, true);
            ObjectiveValue rhsNullObjVal = 
                    NullValueObjectiveFunction.compute(rhsValue, true);
            
            // compile everything into a "BestOf" 
            BestOfMultiObjectiveValue allowNullObjVal = new BestOfMultiObjectiveValue();            
            allowNullObjVal.add(subjectNullObjVal);
            allowNullObjVal.add(lhsNullObjVal);
            allowNullObjVal.add(rhsNullObjVal);
            allowNullObjVal.add(objVal);
            
            // swap out the overall objective value with this one
            objVal = allowNullObjVal;
        } 
        
        objVal.setDescripton(description);
        return objVal;
    }
    
    @Override
    public String toString() {
        return description;
    }
}
