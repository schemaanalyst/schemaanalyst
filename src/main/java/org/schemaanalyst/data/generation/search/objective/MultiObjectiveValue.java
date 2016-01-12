package org.schemaanalyst.data.generation.search.objective;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class MultiObjectiveValue extends ObjectiveValue {

    protected List<ObjectiveValue> objVals;
    protected boolean computeValue;

    public MultiObjectiveValue() {
        this(null);
    }

    public MultiObjectiveValue(String description) {
        super(description);
        objVals = new ArrayList<>();
        computeValue = true;
    }

    public void add(ObjectiveValue objectiveValue) {
        objVals.add(objectiveValue);
        computeValue = true;
    }

    protected abstract void computeValue();

    @Override
    public BigDecimal getValue() {
        if (computeValue) {
            computeValue();
        }
        return value;
    }

    protected void appendObjectiveValuesToStringBuilder(StringBuilder sb, String indent) {
        for (ObjectiveValue objVal : objVals) {
            sb.append("\n");
            sb.append(indent);
            objVal.appendToStringBuilder(sb, indent);
        }
    }
}
