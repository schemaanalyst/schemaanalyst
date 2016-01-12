package org.schemaanalyst.unittest.data.generation.search.objective.row.value;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.BooleanValue;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunctionException;
import org.schemaanalyst.data.generation.search.objective.value.RelationalBooleanValueObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.util.tuple.Pair;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.fail;
import static org.schemaanalyst.unittest.testutil.assertion.ObjectiveValueAssert.assertOptimal;
import static org.schemaanalyst.unittest.testutil.assertion.ObjectiveValueAssert.assertWorst;

@RunWith(JUnitParamsRunner.class)
public class TestRelationalBooleanValueObjectiveFunction {

    Object[] optimalValues() {
        return $(
                $(new BooleanValue(true), RelationalOperator.EQUALS, new BooleanValue(true), false),
                $(new BooleanValue(false), RelationalOperator.EQUALS, new BooleanValue(false), false),
                $(new BooleanValue(false), RelationalOperator.NOT_EQUALS, new BooleanValue(true), false),
                $(new BooleanValue(true), RelationalOperator.NOT_EQUALS, new BooleanValue(false), false),
                
                // same tests above should pass regardless of allowNull, as there are no nulls
                $(new BooleanValue(true), RelationalOperator.EQUALS, new BooleanValue(true), false),
                $(new BooleanValue(false), RelationalOperator.EQUALS, new BooleanValue(false), false),
                $(new BooleanValue(false), RelationalOperator.NOT_EQUALS, new BooleanValue(true), false),
                $(new BooleanValue(true), RelationalOperator.NOT_EQUALS, new BooleanValue(false), false),                
                
                $(new BooleanValue(false), RelationalOperator.EQUALS, null, true),
                $(null, RelationalOperator.EQUALS, null, true),
                $(null, RelationalOperator.EQUALS, new BooleanValue(false), true),
                $(new BooleanValue(false), RelationalOperator.NOT_EQUALS, null, true),
                $(null, RelationalOperator.NOT_EQUALS, null, true),
                $(null, RelationalOperator.NOT_EQUALS, new BooleanValue(false), true)                
        );
    }    
    
    @Test
    @Parameters(method = "optimalValues")    
    public void testOptimal(BooleanValue lhs, RelationalOperator op, BooleanValue rhs, boolean allowNull) {        
        RelationalBooleanValueObjectiveFunction objFun = 
                new RelationalBooleanValueObjectiveFunction(op, allowNull);
        
        assertOptimal(objFun.evaluate(new Pair<>(lhs, rhs)));
    }
    
    // -----------------------------------------------------------------------------------------------------------------------        
    
    Object[] worstValues() {
        return $(
                $(new BooleanValue(true), RelationalOperator.EQUALS, new BooleanValue(false), true),
                $(new BooleanValue(false), RelationalOperator.EQUALS, new BooleanValue(true), true),
                $(new BooleanValue(true), RelationalOperator.NOT_EQUALS, new BooleanValue(true), true),
                $(new BooleanValue(false), RelationalOperator.NOT_EQUALS, new BooleanValue(false), true),

                // same tests above should pass regardless of allowNull, as there are no nulls
                $(new BooleanValue(true), RelationalOperator.EQUALS, new BooleanValue(false), false),
                $(new BooleanValue(false), RelationalOperator.EQUALS, new BooleanValue(true), false),
                $(new BooleanValue(true), RelationalOperator.NOT_EQUALS, new BooleanValue(true), false),
                $(new BooleanValue(false), RelationalOperator.NOT_EQUALS, new BooleanValue(false), false),                
                
                $(new BooleanValue(false), RelationalOperator.EQUALS, null, false),
                $(null, RelationalOperator.EQUALS, null, false),
                $(null, RelationalOperator.EQUALS, new BooleanValue(false), false),
                $(new BooleanValue(false), RelationalOperator.NOT_EQUALS, null, false),
                $(null, RelationalOperator.NOT_EQUALS, null, false),
                $(null, RelationalOperator.NOT_EQUALS, new BooleanValue(false), false)                
        );
    }     
            
    @Test
    @Parameters(method = "worstValues")    
    public void testWorst(BooleanValue lhs, RelationalOperator op, BooleanValue rhs, boolean allowNull) {        
        RelationalBooleanValueObjectiveFunction objFun = 
                new RelationalBooleanValueObjectiveFunction(op, allowNull);
        
        assertWorst(objFun.evaluate(new Pair<>(lhs, rhs)));
    } 
    
    // -----------------------------------------------------------------------------------------------------------------------
    
    Object[] throwsExceptionValues() {
        return $(
                $(new BooleanValue(true), RelationalOperator.GREATER, new BooleanValue(false), true),
                $(new BooleanValue(true), RelationalOperator.GREATER_OR_EQUALS, new BooleanValue(false), true),
                $(new BooleanValue(true), RelationalOperator.LESS, new BooleanValue(false), true),
                $(new BooleanValue(true), RelationalOperator.LESS_OR_EQUALS, new BooleanValue(false), true),                
                $(null, RelationalOperator.GREATER, null, true)                
        );
    }      
    
    @Test
    @Parameters(method = "throwsExceptionValues")    
    public void testThrowsException(BooleanValue lhs, RelationalOperator op, BooleanValue rhs, boolean allowNull) {        
        boolean fail = true;
        try {        
            RelationalBooleanValueObjectiveFunction objFun = 
                    new RelationalBooleanValueObjectiveFunction(op, allowNull);

            assertWorst(objFun.evaluate(new Pair<>(lhs, rhs)));
        } catch (ObjectiveFunctionException e) {
            fail = false;            
        }
        
        if (fail) {
            fail("Exception should be thrown, but wasn't");
        }
    }     
    
}
