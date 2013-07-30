package org.schemaanalyst.datageneration.search.objective.value;

import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;

public class NullValueObjectiveFunction extends ObjectiveFunction<Value> {

    protected boolean nullIsSatisfy;

    public NullValueObjectiveFunction(boolean nullIsSatisfy) {
        this.nullIsSatisfy = nullIsSatisfy;
    }

    @Override
    public ObjectiveValue evaluate(Value value) {
        String description = value + ", nullIsSatisfy: " + nullIsSatisfy;

        if ((nullIsSatisfy && value == null) || (!nullIsSatisfy && value != null)) {
            return ObjectiveValue.optimalObjectiveValue(description);
        } else {
            return ObjectiveValue.worstObjectiveValue(description);
        }
    }

    public static ObjectiveValue compute(Value value, boolean nullIsSatisfy) {
        return (new NullValueObjectiveFunction(nullIsSatisfy)).evaluate(value);
    }
}
