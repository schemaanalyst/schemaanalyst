package org.schemaanalyst.deprecated.test.datageneration.search.objective.row;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.datageneration.search.objective.row.RelationalExpressionObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;
import org.schemaanalyst.test.testutil.mock.MockRow;

import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.*;

public class TestRelationalExpressionObjectiveFunction {
    
    RelationalExpressionObjectiveFunction objFunSatisfy, objFunNotSatisfy;    
    
    @Test
    public void expTrue_allowNullFalse() {
        RelationalExpression exp 
            = new RelationalExpression(
                    new ConstantExpression(new NumericValue(1)),
                    RelationalOperator.EQUALS, 
                    new ConstantExpression(new NumericValue(1)));
        
        setupObjFuns(exp, false);        // allowNull
        assertOptimality(true);         // goalIsToSatisfy
    }

    @Test
    public void expFalse_allowNullFalse() {
        RelationalExpression exp 
            = new RelationalExpression(
                    new ConstantExpression(new NumericValue(2)),
                    RelationalOperator.EQUALS, 
                    new ConstantExpression(new NumericValue(1)));
        
        setupObjFuns(exp, false);        
        assertOptimality(false);
    }    
    
    @Test
    public void expNull_allowNullFalse() {
        RelationalExpression exp 
            = new RelationalExpression(
                    new ConstantExpression(null),
                    RelationalOperator.EQUALS, 
                    new ConstantExpression(new NumericValue(1)));
        
        setupObjFuns(exp, false);       
        assertNullOptimality(false);
    }    
    
    @Test
    public void expNull_allowNullTrue() {
        RelationalExpression exp 
            = new RelationalExpression(
                    new ConstantExpression(null),
                    RelationalOperator.EQUALS, 
                    new ConstantExpression(new NumericValue(1)));
        
        setupObjFuns(exp, true);                
        assertNullOptimality(true);
    }

    private void setupObjFuns(RelationalExpression exp, boolean allowNull) {
        objFunSatisfy = new RelationalExpressionObjectiveFunction(exp, true, allowNull);
        objFunNotSatisfy = new RelationalExpressionObjectiveFunction(exp, false, allowNull);
    }      
    
    private void assertOptimality(boolean satisfyIsOptimal) {
        if (satisfyIsOptimal) {
            assertOptimal(objFunSatisfy.evaluate(new MockRow()));
            assertNonOptimal(objFunNotSatisfy.evaluate(new MockRow()));
        } else {
            assertNonOptimal(objFunSatisfy.evaluate(new MockRow()));
            assertOptimal(objFunNotSatisfy.evaluate(new MockRow()));            
        }
    }
    
    private void assertNullOptimality(boolean allowNull) {
        if (allowNull) {
            assertOptimal(objFunSatisfy.evaluate(new MockRow()));
            assertOptimal(objFunNotSatisfy.evaluate(new MockRow()));
        } else {
            assertNonOptimal(objFunSatisfy.evaluate(new MockRow()));
            assertNonOptimal(objFunNotSatisfy.evaluate(new MockRow()));            
        }
    }           
}
