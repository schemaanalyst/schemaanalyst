package org.schemaanalyst.data;

import java.math.BigDecimal;

public class NumericValue extends Value {

    private static final long serialVersionUID = -5783509968308882198L;
    protected BigDecimal value = BigDecimal.ZERO;
    protected BigDecimal min, max;

    public NumericValue() {
    }

    public NumericValue(BigDecimal value) {
        set(value);
    }

    public NumericValue(int value) {
        set(value);
    }

    public NumericValue(int min, int max) {
        this.min = new BigDecimal(min);
        this.max = new BigDecimal(max);
    }

    public NumericValue(int value, int min, int max) {
        this(min, max);
        set(value);
    }

    public NumericValue(String value) {
        set(value);
    }

    public NumericValue(String min, String max) {
        this.min = new BigDecimal(min);
        this.max = new BigDecimal(max);
    }

    public NumericValue(String value, String min, String max) {
        this(min, max);
        set(value);
    }

    public BigDecimal get() {
        return value;
    }

    public void set(int value) {
        this.set(new BigDecimal(value));
    }

    public void set(String value) {
        this.set(new BigDecimal(value));
    }

    public void set(BigDecimal value) {
        if (min != null && value.compareTo(min) < 0) {
            this.value = min;
        } else if (max != null && value.compareTo(max) > 0) {
            this.value = max;
        } else {
            this.value = value;
        }
    }

    public BigDecimal getStepSize() {
        return BigDecimal.ONE.scaleByPowerOfTen(-value.scale());
    }

    @Override
    public void increment() {
        value = value.add(getStepSize());
    }

    @Override
    public void decrement() {
        value = value.subtract(getStepSize());
    }

    @Override
    public void accept(ValueVisitor valueVisitor) {
        valueVisitor.visit(this);
    }

    @Override
    public NumericValue duplicate() {
        NumericValue duplicate = new NumericValue();
        duplicate.value = value;
        duplicate.min = min;
        duplicate.max = max;
        return duplicate;
    }

    @Override
    public int compareTo(Value v) {
        if (getClass() != v.getClass()) {
            throw new DataException(
                    "Cannot compare NumericValues to a " + v.getClass());
        }
        return value.compareTo(((NumericValue) v).value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        NumericValue other = (NumericValue) obj;
        return value.compareTo(other.value) == 0;
    }

    @Override
    public String toString() {
        return value.toPlainString();
    }
}
