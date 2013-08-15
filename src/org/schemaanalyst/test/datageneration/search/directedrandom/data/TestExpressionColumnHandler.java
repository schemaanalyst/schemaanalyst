package org.schemaanalyst.test.datageneration.search.directedrandom.data;

import static junitparams.JUnitParamsRunner.$;
import static org.schemaanalyst.test.testutil.Params.*;
import static org.schemaanalyst.test.testutil.assertion.DataAssert.assertDataEquals;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.datageneration.search.handler.ExpressionColumnHandler;
import org.schemaanalyst.datageneration.search.objective.data.ExpressionColumnObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;
import org.schemaanalyst.test.testutil.mock.MockCellRandomiser;
import org.schemaanalyst.test.testutil.mock.OneColumnMockDatabase;

@RunWith(JUnitParamsRunner.class)
public class TestExpressionColumnHandler {

    OneColumnMockDatabase database = new OneColumnMockDatabase();    
    
    RelationalExpression expression = 
            new RelationalExpression(
                    new ColumnExpression(database.table, database.column),
                    RelationalOperator.EQUALS, 
                    new ConstantExpression(new NumericValue(1)));     
    
    Object[] testValues() {
        return $(
                $(d(r(2), r(4), r(8)), d(r(1), r(1), r(1)), true, true),
                $(d(r(1), r(1), r(1)), d(r(2), r(4), r(8)), false, true)                
                );
    }
    
    // When the test code is harder to write than the object it's testing ....
    // This tests that the row values get changed as expected by the ExpressionConstraintHandler
    @Test 
    @Parameters(method = "testValues")    
    public void test(Integer[] originalValues, Integer[] finalValues, boolean goalIsToSatisfy, boolean allowNull) {

        database.setDataValues(originalValues);
        
        MockCellRandomiser cellRandomiser = new MockCellRandomiser(finalValues);
        
        ExpressionColumnObjectiveFunction objFun = new ExpressionColumnObjectiveFunction(
                expression, "", goalIsToSatisfy, allowNull);
        
        ExpressionColumnHandler ech = new ExpressionColumnHandler(objFun, cellRandomiser);
        ech.attemptToFindSolution(database.data);
        
        assertDataEquals(finalValues, database.data);
    }
    
}
