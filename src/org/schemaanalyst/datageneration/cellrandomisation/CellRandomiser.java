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

    public void randomiseCell(Cell cell) {
        randomiseCell(cell, true);
    }

    public void randomiseCell(Cell cell, boolean allowNull) {

        class ValueDispatcher implements ValueVisitor {

            @Override
            public void visit(BooleanValue value) {
                randomiseBooleanValue(value);
            }

            @Override
            public void visit(DateValue value) {
                randomiseDateValue(value);
            }

            @Override
            public void visit(DateTimeValue value) {
                randomiseDateTimeValue(value);
            }

            @Override
            public void visit(NumericValue value) {
                randomiseNumericValue(value);
            }

            @Override
            public void visit(StringValue value) {
                randomiseStringValue(value);
            }

            @Override
            public void visit(TimeValue value) {
                randomiseTimeValue(value);
            }

            @Override
            public void visit(TimestampValue value) {
                randomiseTimestampValue(value);
            }
        }

        if (!allowNull) {
            randomiseNull(cell);
        } else {
            cell.setNull(false);
        }

        if (!cell.isNull()) {
            Value value = cell.getValueInstance();
            value.accept(new ValueDispatcher());
        }
    }

    public void randomiseCells(List<Cell> cells) {
        randomiseCells(cells, true);
    }

    public void randomiseCells(List<Cell> cells, boolean allowNull) {
        for (Cell cell : cells) {
            randomiseCell(cell, allowNull);
        }
    }

    public void randomiseCells(Row row) {
        randomiseCells(row.getCells());
    }

    public void randomiseCells(Row row, boolean allowNull) {
        randomiseCells(row.getCells(), allowNull);
    }

    protected void randomiseNull(Cell cell) {
        boolean setToNull = random.nextDouble() <= nullProbability;
        if (setToNull) {
            cell.setValue(null);
        }
    }

    protected void randomiseBooleanValue(BooleanValue booleanValue) {
        booleanValue.set(random.nextBoolean());
    }

    protected void randomiseDateValue(DateValue dateValue) {
        randomiseNumericValue(dateValue.getYear(), yearMin, yearMax);
        randomiseNumericValue(dateValue.getMonth(), monthMin, monthMax);
        randomiseNumericValue(dateValue.getDay(), dayMin, dayMax);
    }

    protected void randomiseDateTimeValue(DateTimeValue dateTimeValue) {
        randomiseNumericValue(dateTimeValue.getYear(), yearMin, yearMax);
        randomiseNumericValue(dateTimeValue.getMonth(), monthMin, monthMax);
        randomiseNumericValue(dateTimeValue.getDay(), dayMin, dayMax);
        randomiseNumericValue(dateTimeValue.getHour(), hourMin, hourMax);
        randomiseNumericValue(dateTimeValue.getMinute(), minuteMin, minuteMax);
        randomiseNumericValue(dateTimeValue.getSecond(), secondMin, secondMax);
    }

    protected void randomiseNumericValue(NumericValue numericValue) {
        randomiseNumericValue(numericValue, numericMin, numericMax);
    }

    protected void randomiseNumericValue(NumericValue numericValue, int min, int max) {
        randomiseNumericValue(numericValue, new BigDecimal(min), new BigDecimal(max));
    }

    protected void randomiseNumericValue(NumericValue numericValue, BigDecimal min, BigDecimal max) {
        BigDecimal value = numericValue.get();
        BigDecimal range = max.subtract(min);
        BigDecimal randomInRange = range.multiply(new BigDecimal(random.nextDouble()));
        BigDecimal newValue = min.add(randomInRange);

        // ensure the new value has the same scale as the old value
        newValue = newValue.setScale(value.scale(), BigDecimal.ROUND_FLOOR);

        numericValue.set(newValue);
    }

    protected void randomiseStringValue(StringValue stringValue) {

        stringValue.clearCharacters();

        if (stringLengthMax > 0) {
            int numCharsToGenerate = random.nextInt(stringLengthMax);

            for (int i = 0; i < numCharsToGenerate; i++) {
                boolean added = stringValue.addCharacter();
                if (added) {
                    NumericValue character = stringValue.getCharacter(i);
                    randomiseNumericValue(character, characterMin, characterMax);
                }
            }
        }
    }

    protected void randomiseTimeValue(TimeValue timeValue) {
        randomiseNumericValue(timeValue.getHour(), hourMin, hourMax);
        randomiseNumericValue(timeValue.getMinute(), minuteMin, minuteMax);
        randomiseNumericValue(timeValue.getSecond(), secondMin, secondMax);
    }

    protected void randomiseTimestampValue(TimestampValue timestampValue) {
        randomiseNumericValue(timestampValue, timestampMin, timestampMax);
    }
}
