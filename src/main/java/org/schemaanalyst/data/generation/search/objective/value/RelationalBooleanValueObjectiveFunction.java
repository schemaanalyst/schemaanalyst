package org.schemaanalyst.data.generation.search.objective.value;

import org.schemaanalyst.data.BooleanValue;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunctionException;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.logic.RelationalOperator;

public class RelationalBooleanValueObjectiveFunction extends RelationalValueObjectiveFunction<BooleanValue> {

    public RelationalBooleanValueObjectiveFunction(RelationalOperator op, boolean allowNull) {
        super(op, allowNull);
        
        if (op != RelationalOperator.EQUALS && op != RelationalOperator.NOT_EQUALS) {
            throw new ObjectiveFunctionException("Cannot use " + op + " with two boolean values");
        }
    }    
    
    @Override
    protected ObjectiveValue computeObjectiveValue(BooleanValue lhs, BooleanValue rhs) {
        
        if (op == RelationalOperator.EQUALS) {
            if (lhs.get() == rhs.get()) {
                return ObjectiveValue.optimalObjectiveValue(description);
            } else {
                return ObjectiveValue.worstObjectiveValue(description);
            }
        } else {
            if (lhs.get() != rhs.get()) {
                return ObjectiveValue.optimalObjectiveValue(description);
            } else {
                return ObjectiveValue.worstObjectiveValue(description);
            }
        }        
    }

}
