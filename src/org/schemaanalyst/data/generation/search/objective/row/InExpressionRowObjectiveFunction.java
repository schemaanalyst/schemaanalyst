package org.schemaanalyst.data.generation.search.objective.row;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.generation.search.objective.*;
import org.schemaanalyst.data.generation.search.objective.value.RelationalValueObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Evaluates an InExpression by considering:

 * - X IN (A, B, C, ...) as the raw clause
 *   ((X = A) OR (X = B) OR (X = C) OR ...) -- the so-called "True Form"

 * - X NOT IN (A, B, C, ...) as the raw clause
 * ((X != A) AND (X != B) AND (X != C) AND ...) -- the so-called "False Form"
 * 
 * where 
 * - X is the result of the InExpression's lhsExpression
 * - A, B, C etc. are the result of the subexpressions of the InExpression's rhsExpresion, 
 *   which is assumed to be a ListExpression, else an ObjectiveFunctionException is thrown. 
 *
 */        
public class InExpressionRowObjectiveFunction extends ObjectiveFunction<Row> {

    // expression evaluators for each of the InExpression subexpression component parts
    private ExpressionEvaluator lhsEvaluator;
    private List<ExpressionEvaluator> rhsEvaluators;
    
    // the relational operator, which is EQUALS or NOT_EQUALS, depending on whether the
    // true form is being evaluated or not
    private RelationalOperator op;
    
    // set to true when the satification goal is the "True Form" of the expression, 
    // i.e. ((X = A) OR (X = B) OR (X = C) OR ...)
    private boolean evaluateTrueForm;
    
    // whether the expression is trivially satisfied if NULL is involved in the evaluation
    private boolean allowNull;
    
    // a descriptor for the objective function
    private String description;
    
    public InExpressionRowObjectiveFunction(InExpression expression,
                                         boolean goalIsToSatisfy,
                                         boolean allowNull) {

        // whether to evaluate the true form or not
        this.evaluateTrueForm = (goalIsToSatisfy != expression.isNotIn());
        
        // is NULL allowed for the clause to be trivially true?
        this.allowNull = allowNull;
        
        // set up the subexpression evaluators...
        lhsEvaluator = new ExpressionEvaluator(expression.getLHS());
        
        if (!(expression.getRHS() instanceof ListExpression)) {
            throw new ObjectiveFunctionException(
                    "Can only handle ListExpressions as the RHS of an InExpression");
        }        
        rhsEvaluators = new ArrayList<>();
        for (Expression subexpression : ((ListExpression) expression.getRHS()).getSubexpressions()) {
            rhsEvaluators.add(new ExpressionEvaluator(subexpression));
        }
        
        // set op
        op = evaluateTrueForm ? RelationalOperator.EQUALS : RelationalOperator.NOT_EQUALS;
        
        // make a descriptor string
        description = expression.toString() 
                + " goalIsToSatisfy: " + goalIsToSatisfy
                + " allowNull: " + allowNull;
    }
    
    @Override
    public ObjectiveValue evaluate(Row row) {
        
        if (rhsEvaluators.size() > 0) {
            // there are elements in the list expression
            
            // "Best Of" for OR ("True Form"), "SumOf" for AND ("False Form").
            MultiObjectiveValue objVal = evaluateTrueForm
                    ? new BestOfMultiObjectiveValue(description)
                    : new SumOfMultiObjectiveValue(description);        
            
            // evaluate all of the subexpressions with respect to the row
            Value lhsValue = lhsEvaluator.evaluate(row);
                
            List<Value> rhsValues = new ArrayList<>();        
            for (ExpressionEvaluator expEvaluator : rhsEvaluators) {
                rhsValues.add(expEvaluator.evaluate(row));
            }
            
            // get objective values for all of the rhs sub-expressions
            for (Value rhsValue : rhsValues) {
                objVal.add(RelationalValueObjectiveFunction.compute(lhsValue, op, rhsValue, allowNull));            
            }
            
            objVal.setDescripton(description);
            return objVal;
        } else {
            // empty list expression            
            return evaluateTrueForm 
                    ? ObjectiveValue.worstObjectiveValue(description)
                    : ObjectiveValue.optimalObjectiveValue(description);
        }
    }
}
