package org.schemaanalyst.data;

import java.util.ArrayList;
import java.util.List;

public class DateValue extends Value
        implements CompoundValue {

    private static final long serialVersionUID = -2857133215655181386L;
    protected NumericValue year, month, day;

    public DateValue() {
        this(2000, 1, 1);
    }

    public DateValue(int year, int month, int day) {
        this.year = new NumericValue(year, 1000, 9999);
        this.month = new NumericValue(month, 1, 12);
        this.day = new NumericValue(day, 1, 31);
    }

    public NumericValue getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public NumericValue getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month.set(month);
    }

    public NumericValue getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day.set(day);
    }

    @Override
    public List<Value> getElements() {
        List<Value> elements = new ArrayList<>();
        elements.add(year);
        elements.add(month);
        elements.add(day);
        return elements;
    }

    @Override
    public void accept(ValueVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public DateValue duplicate() {
        DateValue duplicate = new DateValue();
        duplicate.year = year.duplicate();
        duplicate.month = month.duplicate();
        duplicate.day = day.duplicate();
        return duplicate;
    }

    @Override
    public int compareTo(Value v) {
        if (getClass() != v.getClass()) {
            throw new DataException(
                    "Cannot compare DateValues to a " + v.getClass());
        }

        DateValue other = (DateValue) v;
        int result = year.compareTo(other.year);
        if (result == 0) {
            result = month.compareTo(other.month);
            if (result == 0) {
                result = day.compareTo(other.day);
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((day == null) ? 0 : day.hashCode());
        result = prime * result + ((month == null) ? 0 : month.hashCode());
        result = prime * result + ((year == null) ? 0 : year.hashCode());
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
        DateValue other = (DateValue) obj;
        if (day == null) {
            if (other.day != null)
                return false;
        } else if (!day.equals(other.day))
            return false;
        if (month == null) {
            if (other.month != null)
                return false;
        } else if (!month.equals(other.month))
            return false;
        if (year == null) {
            if (other.year != null)
                return false;
        } else if (!year.equals(other.year))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format(
                "'%04d-%02d-%02d'",
                year.get().intValue(),
                month.get().intValue(),
                day.get().intValue());
    }
}
