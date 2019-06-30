package org.schemaanalyst.data.generation.cellvaluegeneration;

import java.math.BigDecimal;

//import org.fluttercode.datafactory.impl.DataFactory;
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

public class ColNameCellValueGenerator extends RandomCellValueGenerator {
	
	private int columnCounter = 0;

	public ColNameCellValueGenerator(Random random, ValueInitializationProfile profile, double nullProbability,
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
        
        String colName = cell.getColumn().getName();
        generateReadableValue(cell, colName, this.columnCounter);
        this.columnCounter++;
    }
	
	private void generateReadableValue(Cell cell, String colName, int columnCounter) {

        new ValueVisitor() {
        	
        	String colName = null;
        	int columnCounter;

            void generateRandomValue(Cell cell, String colName, int columnCounter) {
            	this.colName = colName;
            	this.columnCounter = columnCounter;
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
            	BigDecimal newValue = new BigDecimal(this.columnCounter);
            	value.set(newValue);

                //randomize(value, profile.getNumericMin(), profile.getNumericMax());
            }

            @Override
            public void visit(StringValue value) {
                value.clearCharacters();

                if (profile.getStringLengthMax() > 0) {
                	if (!colName.isEmpty() || colName.equals(null)) {
                		value.set(colName + "_" + this.columnCounter);
                	} else {
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
        }.generateRandomValue(cell, colName, columnCounter);
    }
}