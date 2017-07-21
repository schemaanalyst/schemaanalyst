package org.schemaanalyst.data.generation.search.objective.value;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;

public class NullValueObjectiveFunction extends ObjectiveFunction<Value> {

    protected boolean allowNull;

    public NullValueObjectiveFunction(boolean allowNull) {
        this.allowNull = allowNull;
    }

    @Override
    public ObjectiveValue evaluate(Value value) {
        String description = value + ", allowNull: " + allowNull;

        if ((allowNull && value == null) || (!allowNull && value != null)) {
            return ObjectiveValue.optimalObjectiveValue(description);
        } else {
            return ObjectiveValue.worstObjectiveValue(description);
        }
    }

    public static ObjectiveValue compute(Value value, boolean allowNull) {
        return (new NullValueObjectiveFunction(allowNull)).evaluate(value);
    }
    
    @Override
    public Data getState() {
    	return null;
    }
    
    @Override
    public Predicate getpredicate() {
    	return null;
    }
}
