package org.schemaanalyst.datageneration.cellrandomisation;

import java.math.BigDecimal;
import java.util.List;

import org.schemaanalyst.data.BooleanValue;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.DateTimeValue;
import org.schemaanalyst.data.DateValue;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.data.TimeValue;
import org.schemaanalyst.data.TimestampValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.ValueVisitor;
import org.schemaanalyst.util.random.Random;

public class CellRandomiser {

    protected Random random;
    protected double nullProbability;
    protected int yearMin, yearMax;
    protected int monthMin, monthMax;
    protected int dayMin, dayMax;
    protected int hourMin, hourMax;
    protected int minuteMin, minuteMax;
    protected int secondMin, secondMax;
    protected BigDecimal numericMin, numericMax;
    protected int stringLengthMax, characterMin, characterMax;
    protected int timestampMin, timestampMax;

    public CellRandomiser(Random random,
            double nullProbability,
            int yearMin, int yearMax,
            int monthMin, int monthMax,
            int dayMin, int dayMax,
            int hourMin, int hourMax,
            int minuteMin, int minuteMax,
            int secondMin, int secondMax,
            BigDecimal numericMin, BigDecimal numericMax,
            int stringLengthMax, int characterMin, int characterMax,
            int timestampMin, int timestampMax) {

        this.random = random;

        this.nullProbability = nullProbability;

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

    public void randomizeCell(Cell cell) {
        randomizeCell(cell, true);
    }

    public void randomizeCell(Cell cell, boolean allowNull) {

        class ValueDispatcher implements ValueVisitor {

            public void visit(BooleanValue value) {
                randomizeBooleanValue(value);
            }

            public void visit(DateValue value) {
                randomizeDateValue(value);
            }

            public void visit(DateTimeValue value) {
                randomizeDateTimeValue(value);
            }

            public void visit(NumericValue value) {
                randomizeNumericValue(value);
            }

            public void visit(StringValue value) {
                randomizeStringValue(value);
            }

            public void visit(TimeValue value) {
                randomizeTimeValue(value);
            }

            public void visit(TimestampValue value) {
                randomizeTimestampValue(value);
            }
        }

        if (!allowNull) {
            randomizeNull(cell);
        } else {
            cell.setNull(false);
        }

        if (!cell.isNull()) {
            Value value = cell.getValueInstance();
            value.accept(new ValueDispatcher());
        }
    }

    public void randomizeCells(List<Cell> cells) {
        randomizeCells(cells, true);
    }

    public void randomizeCells(List<Cell> cells, boolean allowNull) {
        for (Cell cell : cells) {
            randomizeCell(cell, allowNull);
        }
    }

    public void randomizeCells(Row row) {
        randomizeCells(row.getCells());
    }

    public void randomizeCells(Row row, boolean allowNull) {
        randomizeCells(row.getCells(), allowNull);
    }

    protected void randomizeNull(Cell cell) {
        boolean setToNull = random.nextDouble() <= nullProbability;
        if (setToNull) {
            cell.setValue(null);
        }
    }

    protected void randomizeBooleanValue(BooleanValue booleanValue) {
        booleanValue.set(random.nextBoolean());
    }

    protected void randomizeDateValue(DateValue dateValue) {
        randomizeNumericValue(dateValue.getYear(), yearMin, yearMax);
        randomizeNumericValue(dateValue.getMonth(), monthMin, monthMax);
        randomizeNumericValue(dateValue.getDay(), dayMin, dayMax);
    }

    protected void randomizeDateTimeValue(DateTimeValue dateTimeValue) {
        randomizeNumericValue(dateTimeValue.getYear(), yearMin, yearMax);
        randomizeNumericValue(dateTimeValue.getMonth(), monthMin, monthMax);
        randomizeNumericValue(dateTimeValue.getDay(), dayMin, dayMax);
        randomizeNumericValue(dateTimeValue.getHour(), hourMin, hourMax);
        randomizeNumericValue(dateTimeValue.getMinute(), minuteMin, minuteMax);
        randomizeNumericValue(dateTimeValue.getSecond(), secondMin, secondMax);
    }

    protected void randomizeNumericValue(NumericValue numericValue) {
        randomizeNumericValue(numericValue, numericMin, numericMax);
    }

    protected void randomizeNumericValue(NumericValue numericValue, int min, int max) {
        randomizeNumericValue(numericValue, new BigDecimal(min), new BigDecimal(max));
    }

    protected void randomizeNumericValue(NumericValue numericValue, BigDecimal min, BigDecimal max) {
        BigDecimal value = numericValue.get();
        BigDecimal range = max.subtract(min);
        BigDecimal randomInRange = range.multiply(new BigDecimal(random.nextDouble()));
        BigDecimal newValue = min.add(randomInRange);

        // ensure the new value has the same scale as the old value
        newValue = newValue.setScale(value.scale(), BigDecimal.ROUND_FLOOR);

        numericValue.set(newValue);
    }

    protected void randomizeStringValue(StringValue stringValue) {

        stringValue.clearCharacters();

        if (stringLengthMax > 0) {
            int numCharsToGenerate = random.nextInt(stringLengthMax);

            for (int i = 0; i < numCharsToGenerate; i++) {
                boolean added = stringValue.addCharacter();
                if (added) {
                    NumericValue character = stringValue.getCharacter(i);
                    randomizeNumericValue(character, characterMin, characterMax);
                }
            }
        }
    }

    protected void randomizeTimeValue(TimeValue timeValue) {
        randomizeNumericValue(timeValue.getHour(), hourMin, hourMax);
        randomizeNumericValue(timeValue.getMinute(), minuteMin, minuteMax);
        randomizeNumericValue(timeValue.getSecond(), secondMin, secondMax);
    }

    protected void randomizeTimestampValue(TimestampValue timestampValue) {
        randomizeNumericValue(timestampValue, timestampMin, timestampMax);
    }
}
