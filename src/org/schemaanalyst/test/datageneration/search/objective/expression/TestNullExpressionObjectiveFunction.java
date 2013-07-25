package org.schemaanalyst.test.datageneration.search.objective.expression;

import static org.junit.Assert.*;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.expression.NullExpressionObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;
import org.schemaanalyst.test.testutil.mock.MockRow;

public class TestNullExpressionObjectiveFunction {

    private ObjectiveValue getObjectiveValue(Value value, boolean isNotNull, boolean goalIsToSatisfy) {
        NullExpression exp = new NullExpression(new ConstantExpression(value), isNotNull);        
        NullExpressionObjectiveFunction objFun = new NullExpressionObjectiveFunction(exp, goalIsToSatisfy);        
        return objFun.evaluate(new MockRow());
    }    
    
    @Test
    public void test_ValueIsNull_ExpIsNotNull_GoalIsToSatify() {
        ObjectiveValue objVal = getObjectiveValue(null, true, true);        
        assertFalse(objVal.isOptimal());
    }
    
    @Test
    public void test_ValueIsNull_ExpIsNull_GoalIsToSatify() {
        ObjectiveValue objVal = getObjectiveValue(null, false, true);        
        assertTrue(objVal.isOptimal());
    }    

    @Test
    public void test_ValueIsNull_ExpIsNotNull_GoalIsToFalsify() {
        ObjectiveValue objVal = getObjectiveValue(null, true, false);        
        assertTrue(objVal.isOptimal());
    }
    
    @Test
    public void test_ValueIsNull_ExpIsNull_GoalIsToFalsify() {
        ObjectiveValue objVal = getObjectiveValue(null, false, false);        
        assertFalse(objVal.isOptimal());
    }    
    
    @Test
    public void test_ValueIsNotNull_ExpIsNotNull_GoalIsToSatify() {
        ObjectiveValue objVal = getObjectiveValue(new NumericValue(1), true, true);        
        assertTrue(objVal.isOptimal());
    }
    
    @Test
    public void test_ValueIsNotNull_ExpIsNull_GoalIsToSatify() {
        ObjectiveValue objVal = getObjectiveValue(new NumericValue(1), false, true);        
        assertFalse(objVal.isOptimal());
    }    

    @Test
    public void test_ValueIsNotNull_ExpIsNotNull_GoalIsToFalsify() {
        ObjectiveValue objVal = getObjectiveValue(new NumericValue(1), true, false);        
        assertFalse(objVal.isOptimal());
    }
    
    @Test
    public void test_ValueIsNotNull_ExpIsNull_GoalIsToFalsify() {
        ObjectiveValue objVal = getObjectiveValue(new NumericValue(1), false, false);        
        assertTrue(objVal.isOptimal());
    }     
}
