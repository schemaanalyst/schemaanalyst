package org.schemaanalyst.data;

public class BooleanValue extends Value {

    private static final long serialVersionUID = 2243548983716445823L;
    protected boolean value = false;

    public BooleanValue() {
    }

    public BooleanValue(boolean value) {
        this.value = value;
    }

    public boolean get() {
        return value;
    }

    public void set(boolean value) {
        this.value = value;
    }

    @Override
    public void accept(ValueVisitor valueVisitor) {
        valueVisitor.visit(this);
    }

    @Override
    public BooleanValue duplicate() {
        BooleanValue duplicate = new BooleanValue();
        duplicate.value = value;
        return duplicate;
    }

    @Override
    public int compareTo(Value other) {
        throw new DataException("Cannot compare BooleanValues");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (value ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BooleanValue other = (BooleanValue) obj;
        return value == other.value;
    }

    @Override
    public String toString() {
        return value ? "TRUE" : "FALSE";
    }
}
