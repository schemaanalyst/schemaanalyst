package org.schemaanalyst.data;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by phil on 26/02/2014.
 */
public class ValueLibrary {

    private LinkedHashSet<BooleanValue> booleanValues;
    private LinkedHashSet<DateTimeValue> dateTimeValues;
    private LinkedHashSet<DateValue> dateValues;
    private LinkedHashSet<NumericValue> numericValues;
    private LinkedHashSet<StringValue> stringValues;
    private LinkedHashSet<TimeValue> timeValues;
    private LinkedHashSet<TimestampValue> timestampValues;

    private ValueVisitor valueCategorizer = new ValueVisitor() {
        @Override
        public void visit(BooleanValue value) {
            booleanValues.add(value);
        }

        @Override
        public void visit(DateValue value) {
            dateValues.add(value);
        }

        @Override
        public void visit(DateTimeValue value) {
            dateTimeValues.add(value);
        }

        @Override
        public void visit(NumericValue value) {
            numericValues.add(value);
        }

        @Override
        public void visit(StringValue value) {
            stringValues.add(value);
        }

        @Override
        public void visit(TimeValue value) {
            timeValues.add(value);
        }

        @Override
        public void visit(TimestampValue value) {
            timestampValues.add(value);
        }
    };

    public ValueLibrary() {
        booleanValues = new LinkedHashSet<>();
        dateTimeValues = new LinkedHashSet<>();
        dateValues = new LinkedHashSet<>();
        numericValues = new LinkedHashSet<>();
        stringValues = new LinkedHashSet<>();
        timeValues = new LinkedHashSet<>();
        timestampValues = new LinkedHashSet<>();
    }

    public List<BooleanValue> getBooleanValues() {
        return new ArrayList<>(booleanValues);
    }

    public List<DateTimeValue> getDateTimeValues() {
        return new ArrayList<>(dateTimeValues);
    }

    public List<DateValue> getDateValues() {
        return new ArrayList<>(dateValues);
    }

    public List<NumericValue> getNumericValues() {
        return new ArrayList<>(numericValues);
    }

    public List<StringValue> getStringValues() {
        return new ArrayList<>(stringValues);
    }

    public List<TimeValue> getTimeValues() {
        return new ArrayList<>(timeValues);
    }

    public List<TimestampValue> getTimestampValues() {
        return new ArrayList<>(timestampValues);
    }

    public void addValue(Value value) {
        Value duplicatedValue = value.duplicate();
        duplicatedValue.accept(valueCategorizer);
    }
}
