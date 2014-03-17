package _deprecated.datageneration.search.objective.value;

import org.schemaanalyst.data.Value;
import _deprecated.datageneration.search.objective.ObjectiveFunction;
import _deprecated.datageneration.search.objective.ObjectiveValue;

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
}
