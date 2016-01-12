package org.schemaanalyst.data;

import java.util.ArrayList;
import java.util.List;

import static org.schemaanalyst.data.DateTimeValueDefaults.*;

public class TimeValue extends Value implements CompoundValue {

    private static final long serialVersionUID = 8345571547413381450L;
    protected NumericValue hour, minute, second;

    public TimeValue() {
        this(DEFAULT_HOUR, DEFAULT_MINUTE, DEFAULT_SECOND);
    }

    public TimeValue(int hour, int minute, int second) {
        this.hour = new NumericValue(hour, MIN_HOUR, MAX_HOUR);
        this.minute = new NumericValue(minute, MIN_MINUTE, MAX_MINUTE);
        this.second = new NumericValue(second, MIN_SECOND, MAX_SECOND);
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

    @Override
    public List<Value> getElements() {
        List<Value> elements = new ArrayList<>();
        elements.add(hour);
        elements.add(minute);
        elements.add(second);
        return elements;
    }

    @Override
    public void accept(ValueVisitor valueVisitor) {
        valueVisitor.visit(this);
    }

    @Override
    public TimeValue duplicate() {
        TimeValue duplicate = new TimeValue();
        duplicate.hour = hour.duplicate();
        duplicate.minute = minute.duplicate();
        duplicate.second = second.duplicate();
        return duplicate;
    }

    @Override
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((hour == null) ? 0 : hour.hashCode());
        result = prime * result + ((minute == null) ? 0 : minute.hashCode());
        result = prime * result + ((second == null) ? 0 : second.hashCode());
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
        TimeValue other = (TimeValue) obj;
        if (hour == null) {
            if (other.hour != null)
                return false;
        } else if (!hour.equals(other.hour))
            return false;
        if (minute == null) {
            if (other.minute != null)
                return false;
        } else if (!minute.equals(other.minute))
            return false;
        if (second == null) {
            if (other.second != null)
                return false;
        } else if (!second.equals(other.second))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format(
                "'%02d:%02d:%02d'",
                hour.get().intValue(),
                minute.get().intValue(),
                second.get().intValue());
    }
}
