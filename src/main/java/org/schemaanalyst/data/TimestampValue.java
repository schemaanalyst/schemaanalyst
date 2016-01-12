package org.schemaanalyst.data;

import java.util.Calendar;
import java.util.TimeZone;

import static org.schemaanalyst.data.DateTimeValueDefaults.*;

public class TimestampValue extends NumericValue {

    public static int MIN = -2147483648, MAX = 2147483647;
    private static final long serialVersionUID = 8677553167833978258L;

    public TimestampValue() {
        this(DEFAULT_YEAR, DEFAULT_MONTH, DEFAULT_DAY, DEFAULT_HOUR, DEFAULT_MINUTE, DEFAULT_SECOND);
    }

    public TimestampValue(int value) {
        super(value, MIN, MAX);
    }

    public TimestampValue(int year, int month, int day) {
        this(year, month, day, DEFAULT_HOUR, DEFAULT_MINUTE, DEFAULT_SECOND);
    }

    public TimestampValue(int year, int month, int day, int hour, int minute, int second) {
        super(MIN, MAX);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        // note that months are 0-11 in Calendar
        calendar.set(year, month - 1, day, hour, minute, second);

        int milliseconds = (int) (calendar.getTimeInMillis() / 1000);
        set(milliseconds);
    }

    @Override
    public void accept(ValueVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public TimestampValue duplicate() {
        TimestampValue duplicate = new TimestampValue();
        duplicate.value = value;
        return duplicate;
    }

    @Override
    public int compareTo(Value v) {
        if (getClass() != v.getClass()) {
            throw new DataException(
                    "Cannot compare TimestampValues to a " + v.getClass());
        }
        return value.compareTo(((TimestampValue) v).value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        return getClass() == obj.getClass();
    }

    @Override
    public String toString() {
        long longValue = value.longValue();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(longValue * 1000);

        return String.format(
                "'%04d-%02d-%02d %02d:%02d:%02d'",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
    }
}
