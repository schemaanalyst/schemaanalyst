package org.schemaanalyst.data.generation.cellvaluegeneration;

import java.util.List;

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

public class SelectCellValueGenerator extends RandomCellValueGenerator {
	
	protected ValueLibrary readableValueLibrary;
	protected double useReadableValueLibraryProbability;

	public SelectCellValueGenerator(Random random, ValueInitializationProfile profile, double nullProbability,
			ValueLibrary seededValueLibrary, double useSeededValueLibraryProbability, ValueLibrary readableValueLibrary,
			double useReadableValueLibraryProbability) {
		super(random, profile, nullProbability, seededValueLibrary, useSeededValueLibraryProbability);
		
		this.readableValueLibrary = readableValueLibrary;
		this.useReadableValueLibraryProbability = useReadableValueLibraryProbability;
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
        
        boolean setToReadableLibraryValue = random.nextDouble() < useReadableValueLibraryProbability;
        if (setToReadableLibraryValue) {
            boolean success = setToReadableLibraryValue(cell);
            if (success) {
                return;
            }
        }

        generateCellValue(cell);
    }
	
	
	protected boolean setToReadableLibraryValue(Cell cell) {

        return new ValueVisitor() {

            boolean success = false;

            public boolean setCell(Cell cell) {
                cell.getValue().accept(this);
                return success;
            }

            @Override
            public void visit(BooleanValue value) {
                List<BooleanValue> booleanValues = readableValueLibrary.getBooleanValues();
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
                List<DateValue> dateValues = readableValueLibrary.getDateValues();
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
                List<DateTimeValue> dateTimeValues = readableValueLibrary.getDateTimeValues();
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
                List<NumericValue> numericValues = readableValueLibrary.getNumericValues();
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
                List<StringValue> stringValues = readableValueLibrary.getStringValues();
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
                List<TimeValue> timeValues = readableValueLibrary.getTimeValues();
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
                List<TimestampValue> timestampValues = readableValueLibrary.getTimestampValues();
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

}
