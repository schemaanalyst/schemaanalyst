package org.schemaanalyst.test.datageneration.search.objective.expression;

import static org.junit.Assert.*;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.expression.RelationalExpressionObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;
import org.schemaanalyst.test.testutil.mock.MockRow;

public class TestRelationalExpressionObjectiveFunction {
    
    @Test
    public void test() {
        RelationalExpression exp 
            = new RelationalExpression(
                    new ConstantExpression(new NumericValue(1)),
                    RelationalOperator.EQUALS, 
                    new ConstantExpression(new NumericValue(1)));
        
        RelationalExpressionObjectiveFunction objFun =
                new RelationalExpressionObjectiveFunction(exp, true, false);
        
        ObjectiveValue objVal = objFun.evaluate(new MockRow());
        
        assertTrue(objVal.isOptimal());
    }

}
