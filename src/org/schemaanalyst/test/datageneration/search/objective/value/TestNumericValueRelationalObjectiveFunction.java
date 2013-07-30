package org.schemaanalyst.test.datageneration.search.objective.value;

import static java.math.BigDecimal.ZERO;
import static junitparams.JUnitParamsRunner.$;
import static org.schemaanalyst.datageneration.search.objective.value.NumericValueRelationalObjectiveFunction.K;
import static org.schemaanalyst.logic.RelationalOperator.EQUALS;
import static org.schemaanalyst.logic.RelationalOperator.GREATER;
import static org.schemaanalyst.logic.RelationalOperator.GREATER_OR_EQUALS;
import static org.schemaanalyst.logic.RelationalOperator.LESS;
import static org.schemaanalyst.logic.RelationalOperator.LESS_OR_EQUALS;
import static org.schemaanalyst.logic.RelationalOperator.NOT_EQUALS;
import static org.schemaanalyst.test.testutil.BigDecimalAssert.assertEquals;
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertOptimal;
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertWorst;

import java.math.BigDecimal;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.datageneration.search.objective.DistanceObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.value.NumericValueRelationalObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.util.Pair;


@RunWith(JUnitParamsRunner.class)
public class TestNumericValueRelationalObjectiveFunction {   
    
    Object[] distanceValues() {
        return $(
                $("1000.1", EQUALS, "1000.1", ZERO),
                $("1000.0", EQUALS, "1000.1", new BigDecimal("0.1").add(K)),
                  
                $("1000.0", NOT_EQUALS, "1000.1", ZERO),
                $("1000.1", NOT_EQUALS, "1000.1", K),
                  
                $("500", LESS, "999.999", ZERO),
                $("700.1", LESS, "500", new BigDecimal("200.1").add(K)),
                  
                $("500", LESS_OR_EQUALS, "500", ZERO),
                $("400", LESS_OR_EQUALS, "500", ZERO),
                $("710.1", LESS_OR_EQUALS, "500", new BigDecimal("210.1").add(K)),                
                  
                $("750", GREATER, "500", ZERO),
                $("600", GREATER, "700", new BigDecimal("100").add(K)),                
                  
                $("600.1", GREATER_OR_EQUALS, "600", ZERO),
                $("500", GREATER_OR_EQUALS, "500", ZERO),
                $("600", GREATER_OR_EQUALS, "600.1", new BigDecimal("0.1").add(K))
                );
    }     
    
    @Test
    @Parameters(method = "distanceValues")    
    public void testDistance(String lhs, RelationalOperator op, String rhs, BigDecimal expectedDistance) {
        DistanceObjectiveValue objValnullIsSatisfy = 
                (DistanceObjectiveValue) (new NumericValueRelationalObjectiveFunction(op, true)).evaluate(
                        new Pair<NumericValue>(new NumericValue(lhs), new NumericValue(rhs)));        

        DistanceObjectiveValue objValNullIsFalse = 
                (DistanceObjectiveValue) (new NumericValueRelationalObjectiveFunction(op, false)).evaluate(
                        new Pair<NumericValue>(new NumericValue(lhs), new NumericValue(rhs)));        
        
        
        assertEquals("Distance should be " + expectedDistance + " for " + objValnullIsSatisfy, 
                     expectedDistance, objValnullIsSatisfy.getDistance());
        
        assertEquals("Distance should be " + expectedDistance + " for " + objValNullIsFalse, 
                     expectedDistance, objValNullIsFalse.getDistance());
    }
   
    
    Object[] nullValues() {
        return $(
                $(null, EQUALS, null),
                $(null, EQUALS, new NumericValue(1)),
                $(new NumericValue(1), EQUALS, null)
               );
    }      
    
    @Test
    @Parameters(method = "nullValues")
    public void testOptimalNullValues(NumericValue lhs, RelationalOperator op, NumericValue rhs) {

        NumericValueRelationalObjectiveFunction objFun =
                new NumericValueRelationalObjectiveFunction(op, true);         

        assertOptimal(objFun.evaluate(new Pair<NumericValue>(lhs, rhs)));        
    }
    
    @Test
    @Parameters(method = "nullValues")
    public void testWorstNullValues(NumericValue lhs, RelationalOperator op, NumericValue rhs) {

        NumericValueRelationalObjectiveFunction objFun =
                new NumericValueRelationalObjectiveFunction(op, false);         

        assertWorst(objFun.evaluate(new Pair<NumericValue>(lhs, rhs)));        
    }    
}