package org.schemaanalyst.data;

import java.util.ArrayList;
import java.util.List;

public class TimeValue extends Value
        implements CompoundValue {

    private static final long serialVersionUID = 8345571547413381450L;
    protected NumericValue hour, minute, second;

    public TimeValue() {
        this(0, 0, 0);
    }

    public TimeValue(int hour, int minute, int second) {
        this.hour = new NumericValue(hour, 0, 23);
        this.minute = new NumericValue(minute, 0, 59);
        this.second = new NumericValue(second, 0, 59);
    }

    public NumericValue getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour.set(hour);
    }

    public NumericValue getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute.set(minute);
    }

    public NumericValue getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second.set(second);
    }

    public List<Value> getElements() {
        List<Value> elements = new ArrayList<Value>();
        elements.add(hour);
        elements.add(minute);
        elements.add(second);
        return elements;
    }

    public void accept(ValueVisitor valueVisitor) {
        valueVisitor.visit(this);
    }

    public TimeValue duplicate() {
        TimeValue duplicate = new TimeValue();
        duplicate.hour = hour.duplicate();
        duplicate.minute = minute.duplicate();
        duplicate.second = second.duplicate();
        return duplicate;
    }

    public int compareTo(Value v) {
        if (getClass() != v.getClass()) {
            throw new DataException(
                    "Cannot compare TimeValues to a " + v.getClass());
        }

        TimeValue other = (TimeValue) v;
        int result = hour.compareTo(other.hour);
        if (result == 0) {
            result = minute.compareTo(other.minute);
            if (result == 0) {
                result = second.compareTo(other.second);
            }
        }
        return result;
    }

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

        TimeValue other = (TimeValue) obj;
        return hour.equals(other.hour)
                && minute.equals(other.minute)
                && second.equals(other.second);
    }

    public String toString() {
        return String.format(
                "'%02d:%02d:%02d'",
                hour.get().intValue(),
                minute.get().intValue(),
                second.get().intValue());
    }
}
