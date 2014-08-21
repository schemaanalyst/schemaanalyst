package org.schemaanalyst.sqlwriter;

import org.schemaanalyst.data.DateValue;

/**
 * Created by phil on 21/08/2014.
 */
public class DateWriter {

    private static int[] MAX_MONTH_DAYS = {31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static int FEBRUARY = 2;
    private static int LEAP_YEAR_FEB_DAYS = 29;
    private static int COMMON_YEAR_FEB_DAYS = 28;

    public static int getMaxDay(int month, int year) {
        if (month == FEBRUARY) {
            return (isLeapYear(year)) ? LEAP_YEAR_FEB_DAYS : COMMON_YEAR_FEB_DAYS;
        } else {
            return MAX_MONTH_DAYS[month-1];
        }
    }

    public static boolean isLeapYear(int yearInt) {
        if (yearInt % 4 != 0) return false;
        if (yearInt % 100 != 0) return true;
        return (yearInt % 400 == 0);
    }

    public String writeDate(DateValue dateValue) {
        return writeDate(
                dateValue.getYear().get().intValue(),
                dateValue.getMonth().get().intValue(),
                dateValue.getDay().get().intValue());
    }

    public String writeDate(int year, int month, int day) {
        int maxDay = getMaxDay(month, year);
        if (day > maxDay) {
            day = maxDay;
        }
        return String.format("'%04d-%02d-%02d'", year, month, day);
    }
}
