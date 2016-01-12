package org.schemaanalyst.data.generation.search.objective;

import java.math.BigDecimal;

public class DistanceObjectiveValue extends ObjectiveValue {

    protected BigDecimal distance;

    public DistanceObjectiveValue() {
        super();
    }

    public DistanceObjectiveValue(String description) {
        super(description);
    }

    public void setValueUsingDistance(int distance) {
        setValueUsingDistance(new BigDecimal(distance));
    }

    public void setValueUsingDistance(double distance) {
        setValueUsingDistance(new BigDecimal(distance));
    }

    public void setValueUsingDistance(BigDecimal distance) {
        super.normalizeAndSetValue(distance);
        this.distance = distance;
    }

    // if the value is set directly, the distance is set to null	
    @Override
    public void normalizeAndSetValue(BigDecimal value) {
        this.distance = null;
        super.setValue(value);
    }

    // if the value is set directly, the distance is set to null
    @Override
    public void setValue(BigDecimal value) {
        this.distance = null;
        super.setValue(value);
    }

    public BigDecimal getDistance() {
        return distance;
    }

    @Override
    public void appendToStringBuilder(StringBuilder sb, String indent) {
        super.appendToStringBuilder(sb, indent);
        sb.append(" [Distance: ");
        sb.append(distance);
        sb.append("]");
    }
}
