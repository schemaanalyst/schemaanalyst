package org.schemaanalyst.data.generation.search.objective.value;

import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.generation.search.objective.*;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.util.tuple.Pair;

import java.util.Iterator;
import java.util.List;

public class EqualsMultiValueObjectiveFunction extends ObjectiveFunction<Pair<List<Value>>> {

    private boolean equals, allowNull;
    
    public EqualsMultiValueObjectiveFunction(boolean equals, boolean allowNull) {
        this.equals = equals;
        this.allowNull = allowNull;        
    }
    
    @Override
    public ObjectiveValue evaluate(Pair<List<Value>> candidateSolution) {
        List<Value> lhs = candidateSolution.getFirst();
        List<Value> rhs = candidateSolution.getSecond();
        
        // check rows are of the same size
        if (lhs.size() != rhs.size()) {
            throw new ObjectiveFunctionException(
                    "Value lists " + lhs + " and " + rhs + " are not of the same size");
        }
        
        RelationalOperator op = equals
                ? RelationalOperator.EQUALS
                : RelationalOperator.NOT_EQUALS;
        
        String description = lhs + " " + op + " " + rhs;
        
        MultiObjectiveValue objVal = equals
                ? new SumOfMultiObjectiveValue(description)
                : new BestOfMultiObjectiveValue(description);
        
        // iterate through the cells of each row being compared
        Iterator<Value> row1Iterator = lhs.iterator();
        Iterator<Value> row2Iterator = rhs.iterator();

        while (row1Iterator.hasNext() && row2Iterator.hasNext()) {

            Value lhsValue = row1Iterator.next();
            Value rhsValue = row2Iterator.next();
            
            objVal.add(RelationalValueObjectiveFunction.compute(
                    lhsValue, op, rhsValue, allowNull));
        }

        return objVal;        
    }
    
    public static ObjectiveValue compute(
            List<Value> lhs, boolean equals, List<Value> rhs, boolean allowNull) {
        EqualsMultiValueObjectiveFunction objFun = new EqualsMultiValueObjectiveFunction(equals, allowNull);
        return objFun.evaluate(new Pair<>(lhs, rhs));        
    }
    
   
}
