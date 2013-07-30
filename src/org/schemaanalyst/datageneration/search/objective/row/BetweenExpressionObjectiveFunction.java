package org.schemaanalyst.datageneration.search.objective.row;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.value.ValueRelationalObjectiveFunction;
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
    private boolean nullIsSatisfy;   
    
    // a string descriptor for this objective function
    private String description;
    
    public BetweenExpressionObjectiveFunction(BetweenExpression expression,
                                              boolean goalIsToSatisfy,
                                              boolean nullIsSatisfy) {
        // which form to evaluate?
        this.evaluateTrueForm = (goalIsToSatisfy != expression.isNotBetween());
                
        // is NULL allowed to satisfy the expression
        this.nullIsSatisfy = nullIsSatisfy;
        
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
                + " nullIsSatisfy: " + nullIsSatisfy;
    }

    @Override
    public ObjectiveValue evaluate(Row row) {
        // "SumOf" for AND ("True Form"), "Best Of" for OR ("False Form").
        MultiObjectiveValue objVal = evaluateTrueForm
                ? new SumOfMultiObjectiveValue(description)
                : new BestOfMultiObjectiveValue(description);

        // evaluate each subexpression to a value
        Value subjectValue = subjectEvaluator.evaluate(row);
        Value lhsValue = lhsEvaluator.evaluate(row);
        Value rhsValue = rhsEvaluator.evaluate(row);
        
        // NOTE: the following should not be implemented unless the BETWEEN expression is SYMMETRIC
        // (it's ASYMMETRIC by default), and we do not support SYMMETRIC in BetweenExpression yet. 
        /*
        // swap the values for Y and Z (LHS and RHS) if they're in the wrong order
        if (lhsValue != null && rhsValue != null && lhsValue.compareTo(rhsValue) > 0) {
            Value temp = lhsValue;
            lhsValue = rhsValue;
            rhsValue = temp;
        }
        */
        
        // add objective values for the two comparisons
        objVal.add(ValueRelationalObjectiveFunction.compute(subjectValue, lhsOp, lhsValue, nullIsSatisfy));
        objVal.add(ValueRelationalObjectiveFunction.compute(subjectValue, rhsOp, rhsValue, nullIsSatisfy));                

        return objVal;        
    }
}
