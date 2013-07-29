package org.schemaanalyst.deprecated.test.datageneration.search.objective.row;

import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertNonOptimal;
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertOptimal;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.datageneration.search.objective.row.InExpressionObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;
import org.schemaanalyst.test.testutil.mock.MockRow;

public class TestInExpressionObjectiveFunction {

    InExpressionObjectiveFunction objFunSatisfy, objFunNotSatisfy;

    @Test
    public void expTrue_isNotInFalse_allowNullFalse() {

        ListExpression listExp = 
                new ListExpression(
                        new ConstantExpression(new NumericValue(0)),
                        new ConstantExpression(new NumericValue(1)),
                        new ConstantExpression(new NumericValue(2)));
        
        InExpression exp = 
                new InExpression(
                        new ConstantExpression(new NumericValue(0)),
                        listExp, 
                        false);
                
        setupObjFuns(exp, false);        
        assertOptimality(true);        
    }    

    @Test
    public void expFalse_isNotInFalse_allowNullFalse() {

        ListExpression listExp = 
                new ListExpression(
                        new ConstantExpression(new NumericValue(0)),
                        new ConstantExpression(new NumericValue(1)),
                        new ConstantExpression(new NumericValue(2)));
        
        InExpression exp = 
                new InExpression(
                        new ConstantExpression(new NumericValue(3)),
                        listExp, 
                        false);
                
        setupObjFuns(exp, false);        
        assertOptimality(false);        
    }    

    @Test
    public void expTrue_isNotInTrue_allowNullFalse() {

        ListExpression listExp = 
                new ListExpression(
                        new ConstantExpression(new NumericValue(0)),
                        new ConstantExpression(new NumericValue(1)),
                        new ConstantExpression(new NumericValue(2)));
        
        InExpression exp = 
                new InExpression(
                        new ConstantExpression(new NumericValue(0)),
                        listExp, 
                        true);
                
        setupObjFuns(exp, false);        
        assertOptimality(false);        
    }      

    @Test
    public void expFalse_isNotInTrue_allowNullFalse() {

        ListExpression listExp = 
                new ListExpression(
                        new ConstantExpression(new NumericValue(0)),
                        new ConstantExpression(new NumericValue(1)),
                        new ConstantExpression(new NumericValue(2)));
        
        InExpression exp = 
                new InExpression(
                        new ConstantExpression(new NumericValue(3)),
                        listExp, 
                        true);
                
        setupObjFuns(exp, false);        
        assertOptimality(true);        
    }    
    
    @Test
    public void expNull_isNotInFalse_allowNullTrue() {

        ListExpression listExp = 
                new ListExpression(
                        new ConstantExpression(new NumericValue(0)),
                        new ConstantExpression(new NumericValue(1)),
                        new ConstantExpression(new NumericValue(2)));
        
        InExpression exp = 
                new InExpression(
                        new ConstantExpression(null),
                        listExp, 
                        false);
                
        setupObjFuns(exp, true);        
        assertNullOptimality(true);        
    }
    
    @Test
    public void expNull2_isNotInFalse_allowNullTrue() {

        ListExpression listExp = 
                new ListExpression(
                        new ConstantExpression(new NumericValue(0)),
                        new ConstantExpression(null),
                        new ConstantExpression(new NumericValue(2)));
        
        InExpression exp = 
                new InExpression(
                        new ConstantExpression(new NumericValue(3)),
                        listExp, 
                        false);
                
        setupObjFuns(exp, true);        
        assertNullOptimality(true);        
    }      
    
    private void setupObjFuns(InExpression exp, boolean allowNull) {
        objFunSatisfy = new InExpressionObjectiveFunction(exp, true, allowNull);
        objFunNotSatisfy = new InExpressionObjectiveFunction(exp, false, allowNull);
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
            //assertOptimal(objFunFalse.evaluate(new MockRow()));
        } else {
            assertNonOptimal(objFunSatisfy.evaluate(new MockRow()));
            assertNonOptimal(objFunNotSatisfy.evaluate(new MockRow()));            
        }
    }      
    
}
