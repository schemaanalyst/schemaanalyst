package org.schemaanalyst.test.datageneration.search.objective.expression;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.row.RelationalExpressionObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;
import org.schemaanalyst.test.testutil.mock.MockRow;

import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.*;

public class TestRelationalExpressionObjectiveFunction {
    
    @Test
    public void expTrue_SatisfyTrue_allowNullFalse() {
        RelationalExpression exp 
            = new RelationalExpression(
                    new ConstantExpression(new NumericValue(1)),
                    RelationalOperator.EQUALS, 
                    new ConstantExpression(new NumericValue(1)));
        
        RelationalExpressionObjectiveFunction objFun =
                new RelationalExpressionObjectiveFunction(exp, true, false);
        
        ObjectiveValue objVal = objFun.evaluate(new MockRow());
        
        assertOptimal(objVal);
    }

    @Test
    public void expTrue_SatisfyTrue_allowNullTrue() {
        RelationalExpression exp 
            = new RelationalExpression(
                    new ConstantExpression(null),
                    RelationalOperator.EQUALS, 
                    new ConstantExpression(new NumericValue(1)));
        
        RelationalExpressionObjectiveFunction objFun =
                new RelationalExpressionObjectiveFunction(exp, true, true);
        
        ObjectiveValue objVal = objFun.evaluate(new MockRow());
        
        assertOptimal(objVal);
    }    
    
    @Test
    public void expNull_SatisfyTrue_allowNullFalse() {
        RelationalExpression exp 
            = new RelationalExpression(
                    new ConstantExpression(null),
                    RelationalOperator.EQUALS, 
                    new ConstantExpression(new NumericValue(1)));
        
        RelationalExpressionObjectiveFunction objFun =
                new RelationalExpressionObjectiveFunction(exp, true, false);
        
        ObjectiveValue objVal = objFun.evaluate(new MockRow());
        
        assertNonOptimal(objVal);
    }     
    
}
