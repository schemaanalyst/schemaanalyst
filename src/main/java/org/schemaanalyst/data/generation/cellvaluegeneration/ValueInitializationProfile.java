package org.schemaanalyst.data.generation.cellvaluegeneration;

import java.math.BigDecimal;

/**
 * Created by phil on 26/02/2014.
 */
public class ValueInitializationProfile {

    public static final ValueInitializationProfile SMALL = new ValueInitializationProfile(
            1990, // yearMin
            2020, // yearMax

            1, // monthMin
            12, // monthMax,

            1, // dayMin
            31, // dayMax,

            0, // hourMin
            23, // hourMax

            0, // minuteMin
            59, // minuteMax

            0, // secondMin
            59, // secondMax

            new BigDecimal(-1000), // numericMin
            new BigDecimal(1000), // numericMax

            10, // stringLengthMax
            97, // characterMin
            122, // characterMax

            631152000, // timestampMin
            1577836800 // timestampMax
    );

    public static final ValueInitializationProfile LARGE = new ValueInitializationProfile(
            1000, // yearMin
            9999, // yearMax

            1, // monthMin
            12, // monthMax,

            1, // dayMin
            31, // dayMax,

            0, // hourMin
            23, // hourMax

            0, // minuteMin
            59, // minuteMax

            0, // secondMin
            59, // secondMax

            new BigDecimal(-10000), // numericMin
            new BigDecimal(10000), // numericMax

            20, // stringLengthMax
            32, // characterMin
            126, // characterMax

            -2147483648, // timestampMin
            2147483647 // timestampMax
    );

    private int yearMin, yearMax;
    private int monthMin, monthMax;
    private int dayMin, dayMax;
    private int hourMin, hourMax;
    private int minuteMin, minuteMax;
    private int secondMin, secondMax;
    private BigDecimal numericMin, numericMax;
    private int stringLengthMax, characterMin, characterMax;
    private int timestampMin, timestampMax;

    public ValueInitializationProfile(
            int yearMin, int yearMax,
            int monthMin, int monthMax,
            int dayMin, int dayMax,
            int hourMin, int hourMax,
            int minuteMin, int minuteMax,
            int secondMin, int secondMax,
            BigDecimal numericMin, BigDecimal numericMax,
            int stringLengthMax, int characterMin, int characterMax,
            int timestampMin, int timestampMax) {

        this.yearMin = yearMin;
        this.yearMax = yearMax;

        this.monthMin = monthMin;
        this.monthMax = monthMax;

        this.dayMin = dayMin;
        this.dayMax = dayMax;

        this.hourMin = hourMin;
        this.hourMax = hourMax;

        this.minuteMin = minuteMin;
        this.minuteMax = minuteMax;

        this.secondMin = secondMin;
        this.secondMax = secondMax;

        this.numericMin = numericMin;
        this.numericMax = numericMax;

        this.stringLengthMax = stringLengthMax;
        this.characterMin = characterMin;
        this.characterMax = characterMax;

        this.timestampMin = timestampMin;
        this.timestampMax = timestampMax;
    }

    public int getYearMin() {
        return yearMin;
    }

    public int getYearMax() {
        return yearMax;
    }

    public int getMonthMin() {
        return monthMin;
    }

    public int getMonthMax() {
        return monthMax;
    }

    public int getDayMin() {
        return dayMin;
    }

    public int getDayMax() {
        return dayMax;
    }

    public int getHourMin() {
        return hourMin;
    }

    public int getHourMax() {
        return hourMax;
    }

    public int getMinuteMin() {
        return minuteMin;
    }

    public int getMinuteMax() {
        return minuteMax;
    }

    public int getSecondMin() {
        return secondMin;
    }

    public int getSecondMax() {
        return secondMax;
    }

    public BigDecimal getNumericMin() {
        return numericMin;
    }

    public BigDecimal getNumericMax() {
        return numericMax;
    }

    public int getStringLengthMax() {
        return stringLengthMax;
    }

    public int getCharacterMin() {
        return characterMin;
    }

    public int getCharacterMax() {
        return characterMax;
    }

    public int getTimestampMin() {
        return timestampMin;
    }

    public int getTimestampMax() {
        return timestampMax;
    }
}
