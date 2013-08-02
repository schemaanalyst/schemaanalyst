package org.schemaanalyst.test.datageneration.search.objective.data;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertEquals;
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertNonOptimal;
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertOptimal;

import java.util.ArrayList;
import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.data.ExpressionConstraintObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;
import org.schemaanalyst.test.testutil.mock.OneColumnMockDatabase;

@RunWith(JUnitParamsRunner.class)
public class TestExpressionEqualsObjectiveFunction {

    Integer[] one_one_one_one = {1, 1, 1, 1};
    Integer[] one_two_three_four = {1, 2, 3, 4};
    Integer[] two_three_four_five = {2, 3, 4, 5};
    Integer[] one_null = {1, null};
    Integer[] two_null = {2, null};
    
    Object[] testValues() {
        return $(
                $(one_one_one_one, true, false, true, 4, 0),
                $(one_one_one_one, true, true, true, 4, 0),
                $(one_one_one_one, false, false, false, 4, 0),
                $(one_one_one_one, false, true, false, 4, 0),
                
                $(one_two_three_four, true, false, false, 1, 3),
                $(one_two_three_four, true, true, false, 1, 3),
                $(one_two_three_four, false, false, false, 1, 3),
                $(one_two_three_four, false, true, false, 1, 3),
                
                $(two_three_four_five, true, false, false, 0, 4),
                $(two_three_four_five, true, true, false, 0, 4),
                $(two_three_four_five, false, false, true, 0, 4),
                $(two_three_four_five, false, true, true, 0, 4),      
                
                $(one_null, true, false, false, 1, 1),
                $(one_null, true, true, true, 2, 0),
                $(one_null, false, false, false, 1, 1),
                $(one_null, false, true, false, 2, 0),
                
                $(two_null, true, false, false, 0, 2),
                $(two_null, true, true, false, 1, 1),
                $(two_null, false, false, true, 0, 2),
                $(two_null, false, true, false, 1, 1)                 
                );
    }
    
    OneColumnMockDatabase database = new OneColumnMockDatabase();    
    
    RelationalExpression expression = 
            new RelationalExpression(
                    new ColumnExpression(database.column),
                    RelationalOperator.EQUALS, 
                    new ConstantExpression(new NumericValue(1)));      
    
    @Test
    @Parameters(method = "testValues")
    public void oneColumnTests(
            Integer[] dataValues, boolean goalIsToSatisfy, 
            boolean nullAccepted, boolean optimal, 
            int numAcceptedRows, int numRejectedRows) {

        Data data = database.createData(dataValues.length);        
        database.setDataValues(dataValues);
        List<Column> columns = new ArrayList<>();
        columns.add(database.column);        

        ExpressionConstraintObjectiveFunction objFun = new ExpressionConstraintObjectiveFunction(
                expression, "", goalIsToSatisfy, nullAccepted);
        ObjectiveValue objVal = objFun.evaluate(data);

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
        ExpressionConstraintObjectiveFunction objFunTrue = new ExpressionConstraintObjectiveFunction(
                expression, "", true, true);        
        
        assertOptimal(objFunTrue.evaluate(new Data()));  
        
        assertEquals("Number of accepted rows should be zero", 
                0, objFunTrue.getSatisfyingRows().size());        
        
        assertEquals("Number of rejected rows should be zero", 
                0, objFunTrue.getFalsifyingRows().size());  
        
        ExpressionConstraintObjectiveFunction objFunFalse = new ExpressionConstraintObjectiveFunction(
                expression, "", false, true);  
        
        assertNonOptimal(objFunFalse.evaluate(new Data()));  
        
        assertEquals("Number of accepted rows should be zero", 
                0, objFunFalse.getSatisfyingRows().size());        
        
        assertEquals("Number of rejected rows should be zero", 
                0, objFunFalse.getFalsifyingRows().size());             
    }    
}
