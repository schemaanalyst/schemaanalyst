package org.schemaanalyst.data.generation.search.objective;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

public class ObjectiveValue implements Comparable<ObjectiveValue> {

    public static final BigDecimal OPTIMAL = ZERO, WORST = ONE;
    protected static final BigDecimal A = WORST, B = ONE;
    protected static final int PRECISION = 20;
    protected BigDecimal value = WORST;
    protected String description;

    public ObjectiveValue() {
    }

    public ObjectiveValue(String description) {
        this.description = description;
    }

    public void setValue(double value) {
        setValue(new BigDecimal(value));
    }

    public void setValue(String value) {
        setValue(new BigDecimal(value));
    }

    public void setValue(ObjectiveValue objVal) {
        setValue(objVal.getValue());
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void normalizeAndSetValue(BigDecimal value) {
        setValue(A.subtract(A.divide(B.add(value), PRECISION, BigDecimal.ROUND_FLOOR)));
    }

    public void setValueToOptimal() {
        setValue(OPTIMAL);
    }

    public void setValueToWorst() {
        setValue(WORST);
    }

    public BigDecimal getValue() {
        return value;
    }

    public boolean isOptimal() {
        return getValue().compareTo(OPTIMAL) == 0;
    }
    
    public boolean isWorst() {
        return getValue().compareTo(WORST) == 0;
    }

    @Override
    public int compareTo(ObjectiveValue other) {
        return -getValue().compareTo(other.getValue());
    }

    public boolean betterThan(ObjectiveValue other) {
        return compareTo(other) > 0;
    }

    public boolean worseThan(ObjectiveValue other) {
        return compareTo(other) < 0;
    }

    public void setDescripton(String description) {
        this.description = description;
    }

    public void appendToStringBuilder(StringBuilder sb, String indent) {
        sb.append(indent);
        sb.append("* ");
        if (description != null) {
            sb.append(description);
            sb.append(". ");
        }
        sb.append("Value: ");
        sb.append(getValue());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        appendToStringBuilder(sb, "");
        return sb.toString();
    }

    public static ObjectiveValue worstObjectiveValue() {
        return worstObjectiveValue(null);
    }

    public static ObjectiveValue worstObjectiveValue(String description) {
        ObjectiveValue objVal = new ObjectiveValue(description);
        objVal.setValueToWorst();
        return objVal;
    }

    public static ObjectiveValue optimalObjectiveValue() {
        return optimalObjectiveValue(null);
    }

    public static ObjectiveValue optimalObjectiveValue(String description) {
        ObjectiveValue objVal = new ObjectiveValue(description);
        objVal.setValueToOptimal();
        return objVal;
    }
}
