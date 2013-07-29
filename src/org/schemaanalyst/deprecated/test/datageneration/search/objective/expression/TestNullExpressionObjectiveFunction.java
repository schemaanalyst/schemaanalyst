package org.schemaanalyst.deprecated.test.datageneration.search.objective.expression;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.row.NullExpressionObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;
import org.schemaanalyst.test.testutil.mock.MockRow;

import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.*;

public class TestNullExpressionObjectiveFunction {

    private ObjectiveValue getObjectiveValue(Value value, boolean isNotNull, boolean goalIsToSatisfy) {
        NullExpression exp = new NullExpression(new ConstantExpression(value), isNotNull);        
        NullExpressionObjectiveFunction objFun = new NullExpressionObjectiveFunction(exp, goalIsToSatisfy);        
        return objFun.evaluate(new MockRow());
    }    
    
    @Test
    public void expIsNull_isNotNullIsTrue_SatisfyTrue() {
        ObjectiveValue objVal = getObjectiveValue(null, true, true);        
        assertNonOptimal(objVal);
    }
    
    @Test
    public void expIsNull_isNotNullIsFalse_SatisfyTrue() {
        ObjectiveValue objVal = getObjectiveValue(null, false, true);        
        assertOptimal(objVal);
    }    

    @Test
    public void expIsNull_isNotNullIsTrue_SatisfyFalse() {
        ObjectiveValue objVal = getObjectiveValue(null, true, false);        
        assertOptimal(objVal);
    }
    
    @Test
    public void expIsNull_isNotNullIsFalse_SatisfyFalse() {
        ObjectiveValue objVal = getObjectiveValue(null, false, false);        
        assertNonOptimal(objVal);
    }    
    
    @Test
    public void expIsNotNull_isNotNullIsTrue_SatisfyTrue() {
        ObjectiveValue objVal = getObjectiveValue(new NumericValue(1), true, true);        
        assertOptimal(objVal);
    }
    
    @Test
    public void expIsNotNull_isNotNullIsFalse_SatisfyTrue() {
        ObjectiveValue objVal = getObjectiveValue(new NumericValue(1), false, true);        
        assertNonOptimal(objVal);
    }    

    @Test
    public void expIsNotNull_isNotNullIsTrue_SatisfyFalse() {
        ObjectiveValue objVal = getObjectiveValue(new NumericValue(1), true, false);        
        assertNonOptimal(objVal);
    }
    
    @Test
    public void expIsNotNull_isNotNullIsFalse_SatisfyFalse() {
        ObjectiveValue objVal = getObjectiveValue(new NumericValue(1), false, false);        
        assertOptimal(objVal);
    }     
}
