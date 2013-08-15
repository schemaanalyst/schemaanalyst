package org.schemaanalyst.test.datageneration.search.objective.data;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertEquals;
import static org.schemaanalyst.test.testutil.assertion.ObjectiveValueAssert.assertNonOptimal;
import static org.schemaanalyst.test.testutil.assertion.ObjectiveValueAssert.assertOptimal;
import static org.schemaanalyst.test.testutil.Params.*;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.data.ExpressionColumnObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;
import org.schemaanalyst.test.testutil.mock.OneColumnMockDatabase;

@RunWith(JUnitParamsRunner.class)
public class TestExpressionColumnObjectiveFunction {

    Object[] testValues() {
        return $(
                $(d(r(1), r(1), r(1), r(1)), true, false, true, 4, 0),
                $(d(r(1), r(1), r(1), r(1)), true, true, true, 4, 0),
                $(d(r(1), r(1), r(1), r(1)), false, false, false, 4, 0),
                $(d(r(1), r(1), r(1), r(1)), false, true, false, 4, 0),
                
                $(d(r(1), r(2), r(3), r(4)), true, false, false, 1, 3),
                $(d(r(1), r(2), r(3), r(4)), true, true, false, 1, 3),
                $(d(r(1), r(2), r(3), r(4)), false, false, false, 1, 3),
                $(d(r(1), r(2), r(3), r(4)), false, true, false, 1, 3),
                
                $(d(r(2), r(3), r(4), r(5)), true, false, false, 0, 4),
                $(d(r(2), r(3), r(4), r(5)), true, true, false, 0, 4),
                $(d(r(2), r(3), r(4), r(5)), false, false, true, 0, 4),
                $(d(r(2), r(3), r(4), r(5)), false, true, true, 0, 4),      
                
                $(d(r(1), r(NULL)), true, false, false, 1, 1),
                $(d(r(1), r(NULL)), true, true, true, 2, 0),
                $(d(r(1), r(NULL)), false, false, false, 2, 0),
                $(d(r(1), r(NULL)), false, true, false, 1, 1),
                
                $(d(r(2), r(NULL)), true, false, false, 0, 2),
                $(d(r(2), r(NULL)), true, true, false, 1, 1),
                $(d(r(2), r(NULL)), false, false, false, 1, 1),     
                $(d(r(2), r(NULL)), false, true, true, 0, 2)
                            
                );
    }
    
    OneColumnMockDatabase database = new OneColumnMockDatabase();    
    
    RelationalExpression expression = 
            new RelationalExpression(
                    new ColumnExpression(database.table, database.column),
                    RelationalOperator.EQUALS, 
                    new ConstantExpression(new NumericValue(1)));      
    
    @Test
    @Parameters(method = "testValues")
    public void oneColumnTests(
            Integer[] dataValues, boolean goalIsToSatisfy, 
            boolean allowNull, boolean optimal, 
            int numAcceptedRows, int numRejectedRows) {

        database.setDataValues(dataValues);

        ExpressionColumnObjectiveFunction objFun = new ExpressionColumnObjectiveFunction(
                expression, "", goalIsToSatisfy, allowNull);
        ObjectiveValue objVal = objFun.evaluate(database.data);

        if (optimal) {
            assertOptimal(objVal);
            
            if (goalIsToSatisfy) {
                assertEquals(
                        "No. of rejected rows should be zero", 
                        0, objFun.getFalsifyingRows().size());
            } else {
                assertEquals(
                        "No. of accepted rows should be zero", 
                        0, objFun.getSatisfyingRows().size());
            }
        } else {
            assertNonOptimal(objVal);
        }
        
        assertEquals("No. of accepted rows should be " + numAcceptedRows 
                + " (list is " + objFun.getSatisfyingRows() + ")",
                numAcceptedRows, objFun.getSatisfyingRows().size());        
        
        assertEquals("No. of rejected rows should be " + numRejectedRows 
                + " (list is " + objFun.getFalsifyingRows() + ")",
                numRejectedRows, objFun.getFalsifyingRows().size());
    }         
    
    @Test
    public void testNoRows() {
        ExpressionColumnObjectiveFunction objFunTrue = new ExpressionColumnObjectiveFunction(
                expression, "", true, true);        
        
        assertNonOptimal(objFunTrue.evaluate(new Data()));  
        
        assertEquals("Number of accepted rows should be zero", 
                0, objFunTrue.getSatisfyingRows().size());        
        
        assertEquals("Number of rejected rows should be zero", 
                0, objFunTrue.getFalsifyingRows().size());  
        
        ExpressionColumnObjectiveFunction objFunFalse = new ExpressionColumnObjectiveFunction(
                expression, "", false, true);  
        
        assertNonOptimal(objFunFalse.evaluate(new Data()));  
        
        assertEquals("Number of accepted rows should be zero", 
                0, objFunFalse.getSatisfyingRows().size());        
        
        assertEquals("Number of rejected rows should be zero", 
                0, objFunFalse.getFalsifyingRows().size());             
    }    
}
