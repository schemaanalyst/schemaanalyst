package org.schemaanalyst.datageneration.search.objective.value;

import java.util.Iterator;
import java.util.List;

import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunctionException;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.util.Pair;

public class MultiValueEqualsObjectiveFunction extends ObjectiveFunction<Pair<List<Value>>> {

    private boolean equals, nullAccepted;
    
    public MultiValueEqualsObjectiveFunction(boolean equals, boolean nullAccepted) {
        this.equals = equals;
        this.nullAccepted = nullAccepted;        
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
            
            objVal.add(ValueRelationalObjectiveFunction.compute(
                    lhsValue, op, rhsValue, nullAccepted));
        }

        return objVal;        
    }
    
    public static ObjectiveValue compute(
            List<Value> lhs, boolean equals, List<Value> rhs, boolean nullAccepted) {
        MultiValueEqualsObjectiveFunction objFun = new MultiValueEqualsObjectiveFunction(equals, nullAccepted);
        return objFun.evaluate(new Pair<List<Value>>(lhs, rhs));        
    }
    
   
}
