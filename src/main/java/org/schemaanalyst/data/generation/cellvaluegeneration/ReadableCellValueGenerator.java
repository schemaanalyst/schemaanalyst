package org.schemaanalyst.data.generation.cellvaluegeneration;

import java.math.BigDecimal;

import org.fluttercode.datafactory.impl.DataFactory;
import org.schemaanalyst.data.BooleanValue;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.DateTimeValue;
import org.schemaanalyst.data.DateValue;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.data.TimeValue;
import org.schemaanalyst.data.TimestampValue;
import org.schemaanalyst.data.ValueLibrary;
import org.schemaanalyst.data.ValueVisitor;
import org.schemaanalyst.util.random.Random;

public class ReadableCellValueGenerator extends RandomCellValueGenerator {

	public ReadableCellValueGenerator(Random random, ValueInitializationProfile profile, double nullProbability,
			ValueLibrary valueLibrary, double useLibraryProbability) {
		super(random, profile, nullProbability, valueLibrary, useLibraryProbability);
		// TODO Auto-generated constructor stub
	}

	@Override
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

        generateReadableValue(cell);
    }
	
	private void generateReadableValue(Cell cell) {

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
                    DataFactory df = new DataFactory();
                	value.set(df.getRandomWord(0, profile.getStringLengthMax()));
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
