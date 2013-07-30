package org.schemaanalyst.datageneration.search.objective.value;

import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;

public class NullValueObjectiveFunction extends ObjectiveFunction<Value> {

    protected boolean nullAccepted;

    public NullValueObjectiveFunction(boolean nullAccepted) {
        this.nullAccepted = nullAccepted;
    }

    @Override
    public ObjectiveValue evaluate(Value value) {
        String description = value + ", nullAccepted: " + nullAccepted;

        if ((nullAccepted && value == null) || (!nullAccepted && value != null)) {
            return ObjectiveValue.optimalObjectiveValue(description);
        } else {
            return ObjectiveValue.worstObjectiveValue(description);
        }
    }

    public static ObjectiveValue compute(Value value, boolean nullAccepted) {
        return (new NullValueObjectiveFunction(nullAccepted)).evaluate(value);
    }
}
