package org.schemaanalyst.data.generation.cellvaluegeneration;

import org.schemaanalyst.data.*;
import org.schemaanalyst.util.random.Random;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by phil on 26/02/2014.
 */
public class RandomCellValueGenerator {

    private Random random;
    private ValueInitializationProfile profile;
    private ValueLibrary valueLibrary;
    private double nullProbability, useLibraryProbability;

    public RandomCellValueGenerator(
            Random random,
            ValueInitializationProfile profile,
            double nullProbability,
            ValueLibrary valueLibrary,
            double useLibraryProbability) {

        this.random = random;
        this.profile = profile;
        this.valueLibrary = valueLibrary;
        this.nullProbability = nullProbability;
        this.useLibraryProbability = useLibraryProbability;
    }

    public void generateCellValue(Cell cell) {

        // set the value to null? -- should it be set to a value as well for when the NULL status
        // is flipped?
        boolean setToNull = random.nextDouble() < nullProbability;
        cell.setNull(setToNull);
        if (setToNull) {
            return;
        }

        boolean setToLibraryValue = random.nextDouble() < useLibraryProbability;
        if (setToLibraryValue) {
            boolean success = setToLibraryValue(cell);
            if (success) {
                return;
            }
        }

        generateRandomValue(cell);
    }

    private boolean setToLibraryValue(Cell cell) {

        return new ValueVisitor() {

            boolean success = false;

            public boolean setCell(Cell cell) {
                cell.getValue().accept(this);
                return success;
            }

            @Override
            public void visit(BooleanValue value) {
                List<BooleanValue> booleanValues = valueLibrary.getBooleanValues();
                int size = booleanValues.size();
                if (size > 0) {
                    BooleanValue newValue = booleanValues.get(random.nextInt(booleanValues.size()));
                    value.set(newValue.get());
                    success = true;
                } else {
                    success = false;
                }
            }

            @Override
            public void visit(DateValue value) {
                List<DateValue> dateValues = valueLibrary.getDateValues();
                int size = dateValues.size();
                if (size > 0) {
                    DateValue newValue = dateValues.get(random.nextInt(dateValues.size()));
                    value.getYear().set(newValue.getYear().get());
                    value.getMonth().set(newValue.getMonth().get());
                    value.getDay().set(newValue.getDay().get());
                    success = true;
                } else {
                    success = false;
                }
            }

            @Override
            public void visit(DateTimeValue value) {
                List<DateTimeValue> dateTimeValues = valueLibrary.getDateTimeValues();
                int size = dateTimeValues.size();
                if (size > 0) {
                    DateTimeValue newValue = dateTimeValues.get(random.nextInt(dateTimeValues.size()));
                    value.getYear().set(newValue.getYear().get());
                    value.getMonth().set(newValue.getMonth().get());
                    value.getDay().set(newValue.getDay().get());
                    value.getHour().set(newValue.getHour().get());
                    value.getMinute().set(newValue.getMinute().get());
                    value.getSecond().set(newValue.getSecond().get());
                    success = true;
                } else {
                    success = false;
                }
            }

            @Override
            public void visit(NumericValue value) {
                List<NumericValue> numericValues = valueLibrary.getNumericValues();
                int size = numericValues.size();
                if (size > 0) {
                    NumericValue newValue = numericValues.get(random.nextInt(numericValues.size()));
                    value.set(newValue.get());
                    success = true;
                } else {
                    success = false;
                }
            }

            @Override
            public void visit(StringValue value) {
                List<StringValue> stringValues = valueLibrary.getStringValues();
                int size = stringValues.size();
                if (size > 0) {
                    StringValue newValue = stringValues.get(random.nextInt(stringValues.size()));
                    value.set(newValue.get());
                    success = true;
                } else {
                    success = false;
                }
            }

            @Override
            public void visit(TimeValue value) {
                List<TimeValue> timeValues = valueLibrary.getTimeValues();
                int size = timeValues.size();
                if (size > 0) {
                    TimeValue newValue = timeValues.get(random.nextInt(timeValues.size()));
                    value.getHour().set(newValue.getHour().get());
                    value.getMinute().set(newValue.getMinute().get());
                    value.getSecond().set(newValue.getSecond().get());
                    success = true;
                } else {
                    success = false;
                }
            }

            @Override
            public void visit(TimestampValue value) {
                List<TimestampValue> timestampValues = valueLibrary.getTimestampValues();
                int size = timestampValues.size();
                if (size > 0) {
                    NumericValue newValue = timestampValues.get(random.nextInt(timestampValues.size()));
                    value.set(newValue.get());
                    success = true;
                } else {
                    success = false;
                }
            }
        }.setCell(cell);
    }

    private void generateRandomValue(Cell cell) {

        new ValueVisitor() {

            void generateRandomValue(Cell cell) {
                cell.getValue().accept(this);
            }

            void randomize(NumericValue numericValue, int min, int max) {
                randomize(numericValue, new BigDecimal(min), new BigDecimal(max));
            }

            void randomize(NumericValue numericValue, BigDecimal min, BigDecimal max) {
                BigDecimal value = numericValue.get();
                BigDecimal range = max.subtract(min);
                BigDecimal randomInRange = range.multiply(new BigDecimal(random.nextDouble()));
                BigDecimal newValue = min.add(randomInRange);

                // ensure the new value has the same scale as the old value
                newValue = newValue.setScale(value.scale(), BigDecimal.ROUND_FLOOR);

                numericValue.set(newValue);
            }

            @Override
            public void visit(BooleanValue value) {
                value.set(random.nextBoolean());
            }

            @Override
            public void visit(DateValue value) {
                randomize(value.getYear(), profile.getYearMin(), profile.getYearMax());
                randomize(value.getMonth(), profile.getMonthMin(), profile.getMonthMax());
                randomize(value.getDay(), profile.getDayMin(), profile.getDayMax());
            }

            @Override
            public void visit(DateTimeValue value) {
                randomize(value.getYear(), profile.getYearMin(), profile.getYearMax());
                randomize(value.getMonth(), profile.getMonthMin(), profile.getMonthMax());
                randomize(value.getDay(), profile.getDayMin(), profile.getDayMax());
                randomize(value.getHour(), profile.getHourMin(), profile.getHourMax());
                randomize(value.getMinute(), profile.getMinuteMin(), profile.getMinuteMax());
                randomize(value.getSecond(), profile.getSecondMin(), profile.getSecondMax());
            }

            @Override
            public void visit(NumericValue value) {
                randomize(value, profile.getNumericMin(), profile.getNumericMax());
            }

            @Override
            public void visit(StringValue value) {
                value.clearCharacters();

                if (profile.getStringLengthMax() > 0) {
                    int numCharsToGenerate = random.nextInt(profile.getStringLengthMax());

                    for (int i = 0; i < numCharsToGenerate; i++) {
                        boolean added = value.addCharacter();
                        if (added) {
                            NumericValue character = value.getCharacter(i);
                            randomize(character, profile.getCharacterMin(), profile.getCharacterMax());
                        }
                    }
                }
            }

            @Override
            public void visit(TimeValue value) {
                randomize(value.getHour(), profile.getHourMin(), profile.getHourMax());
                randomize(value.getMinute(), profile.getMinuteMin(), profile.getMinuteMax());
                randomize(value.getSecond(), profile.getSecondMin(), profile.getSecondMax());
            }

            @Override
            public void visit(TimestampValue value) {
                randomize(value, profile.getTimestampMin(), profile.getTimestampMax());
            }
        }.generateRandomValue(cell);
    }
}
