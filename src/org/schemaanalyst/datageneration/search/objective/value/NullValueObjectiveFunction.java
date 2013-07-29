package org.schemaanalyst.datageneration.search.objective.value;

import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;

public class NullValueObjectiveFunction extends ObjectiveFunction<Value> {

    protected boolean nullIsTrue;

    public NullValueObjectiveFunction(boolean nullIsTrue) {
        this.nullIsTrue = nullIsTrue;
    }

    @Override
    public ObjectiveValue evaluate(Value value) {
        String description = value + ", nullIsTrue: " + nullIsTrue;

        if ((nullIsTrue && value == null) || (!nullIsTrue && value != null)) {
            return ObjectiveValue.optimalObjectiveValue(description);
        } else {
            return ObjectiveValue.worstObjectiveValue(description);
        }
    }

    public static ObjectiveValue compute(Value value, boolean nullIsTrue) {
        return (new NullValueObjectiveFunction(nullIsTrue)).evaluate(value);
    }
}
