package org.schemaanalyst.test.datageneration.search.objective.data;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
import org.schemaanalyst.datageneration.search.objective.data.ExpressionObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;
import org.schemaanalyst.test.testutil.mock.OneColumnMockDatabase;

@RunWith(JUnitParamsRunner.class)
public class TestExpressionObjectiveFunction {

    Integer[] one_one_one_one = {1, 1, 1, 1};
    Integer[] one_two_three_four = {1, 2, 3, 4};
    Integer[] two_three_four_five = {2, 3, 4, 5};
    Integer[] one_null = {1, null};
    Integer[] two_null = {2, null};
    
    Object[] testValues() {
        return $(
                $(one_one_one_one, true, false, true, 0),
                $(one_one_one_one, true, true, true, 0),
                $(one_one_one_one, false, false, false, 4),
                $(one_one_one_one, false, true, false, 4),
                
                $(one_two_three_four, true, false, false, 3),
                $(one_two_three_four, true, true, false, 3),
                $(one_two_three_four, false, false, false, 1),
                $(one_two_three_four, false, true, false, 1),
                
                $(two_three_four_five, true, false, false, 4),
                $(two_three_four_five, true, true, false, 4),
                $(two_three_four_five, false, false, true, 0),
                $(two_three_four_five, false, true, true, 0),      
                
                $(one_null, true, false, false, 1),
                $(one_null, true, true, true, 0),
                $(one_null, false, false, false, 2),
                $(one_null, false, true, false, 1),
                
                $(two_null, true, false, false, 2),
                $(two_null, true, true, false, 1),
                $(two_null, false, false, false, 1),
                $(two_null, false, true, true, 0)                 
                );
    }
    
    
    @Test
    @Parameters(method = "testValues")
    public void oneColumnTests(
            Integer[] dataValues, boolean goalIsToSatisfy, 
            boolean nullAccepted, boolean optimal, int numRejectedRows) {

        OneColumnMockDatabase database = new OneColumnMockDatabase();

        Data data = database.createData(dataValues.length);        
        database.setDataValues(dataValues);
        List<Column> columns = new ArrayList<>();
        columns.add(database.column);        
        
        RelationalExpression expression = 
                new RelationalExpression(
                        new ColumnExpression(database.column),
                        RelationalOperator.EQUALS, 
                        new ConstantExpression(new NumericValue(1)));            
        
        ExpressionObjectiveFunction objFun = new ExpressionObjectiveFunction(
                expression, "", goalIsToSatisfy, nullAccepted);
        ObjectiveValue objVal = objFun.evaluate(data);

        if (optimal) {
            assertOptimal(objVal);
            
            assertTrue("No. of rejected rows should be zero"
                    + " (but list is " + objFun.getRejectedRows() + ")",
                    objFun.getRejectedRows().size() == 0);
            
        } else {
            assertNonOptimal(objVal);
        }
         
        assertEquals("No. of rejected rows should be " + numRejectedRows 
                + " (list is " + objFun.getRejectedRows() + ")",
                numRejectedRows, objFun.getRejectedRows().size());
    }          
}
