package org.schemaanalyst.unittest.data.generation.search.objective.row.value;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.generation.search.objective.DistanceObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.value.RelationalNumericValueObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.util.tuple.Pair;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static junitparams.JUnitParamsRunner.$;
import static org.schemaanalyst.data.generation.search.objective.value.RelationalNumericValueObjectiveFunction.K;
import static org.schemaanalyst.logic.RelationalOperator.*;
import static org.schemaanalyst.unittest.testutil.assertion.BigDecimalAssert.assertEquals;
import static org.schemaanalyst.unittest.testutil.assertion.ObjectiveValueAssert.assertOptimal;
import static org.schemaanalyst.unittest.testutil.assertion.ObjectiveValueAssert.assertWorst;


@RunWith(JUnitParamsRunner.class)
public class TestRelationalNumericValueObjectiveFunction {   
    
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
        DistanceObjectiveValue objValallowNull = 
                (DistanceObjectiveValue) (new RelationalNumericValueObjectiveFunction(op, true)).evaluate(
                        new Pair<>(new NumericValue(lhs), new NumericValue(rhs)));        

        DistanceObjectiveValue objValNullIsFalse = 
                (DistanceObjectiveValue) (new RelationalNumericValueObjectiveFunction(op, false)).evaluate(
                        new Pair<>(new NumericValue(lhs), new NumericValue(rhs)));        
        
        
        assertEquals("Distance should be " + expectedDistance + " for " + objValallowNull, 
                     expectedDistance, objValallowNull.getDistance());
        
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

        RelationalNumericValueObjectiveFunction objFun =
                new RelationalNumericValueObjectiveFunction(op, true);         

        assertOptimal(objFun.evaluate(new Pair<>(lhs, rhs)));        
    }
    
    @Test
    @Parameters(method = "nullValues")
    public void testWorstNullValues(NumericValue lhs, RelationalOperator op, NumericValue rhs) {

        RelationalNumericValueObjectiveFunction objFun =
                new RelationalNumericValueObjectiveFunction(op, false);         

        assertWorst(objFun.evaluate(new Pair<>(lhs, rhs)));        
    }    
}