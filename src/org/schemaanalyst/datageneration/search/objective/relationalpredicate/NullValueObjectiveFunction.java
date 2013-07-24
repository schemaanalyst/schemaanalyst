package org.schemaanalyst.datageneration.search.objective.relationalpredicate;

import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;

public class NullValueObjectiveFunction extends ObjectiveFunction<Value> {

    protected boolean shouldBeNull;

    public NullValueObjectiveFunction(boolean shouldBeNull) {
        this.shouldBeNull = shouldBeNull;
    }

    @Override
    public ObjectiveValue evaluate(Value value) {
        ObjectiveValue objVal = new ObjectiveValue(value + ", shouldBeNull: " + shouldBeNull);

        if ((shouldBeNull && value == null) || (!shouldBeNull && value != null)) {
            objVal.setValueToBest();
        } else {
            objVal.setValueToWorst();
        }

        return objVal;
    }

    public static ObjectiveValue compute(Value value, boolean shouldBeNull) {
        NullValueObjectiveFunction objFun = new NullValueObjectiveFunction(shouldBeNull);
        return objFun.evaluate(value);
    }
}
