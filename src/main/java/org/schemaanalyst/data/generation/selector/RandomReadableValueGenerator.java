package org.schemaanalyst.data.generation.selector;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.schemaanalyst.data.BooleanValue;
import org.schemaanalyst.data.DateTimeValue;
import org.schemaanalyst.data.DateValue;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.data.TimeValue;
import org.schemaanalyst.data.TimestampValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.sqlrepresentation.datatype.BigIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.BooleanDataType;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.DataType;
import org.schemaanalyst.sqlrepresentation.datatype.DataTypePlusVisitor;
import org.schemaanalyst.sqlrepresentation.datatype.DateDataType;
import org.schemaanalyst.sqlrepresentation.datatype.DateTimeDataType;
import org.schemaanalyst.sqlrepresentation.datatype.DecimalDataType;
import org.schemaanalyst.sqlrepresentation.datatype.DoubleDataType;
import org.schemaanalyst.sqlrepresentation.datatype.FloatDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.MediumIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.NumericDataType;
import org.schemaanalyst.sqlrepresentation.datatype.PrecisionedAndScaled;
import org.schemaanalyst.sqlrepresentation.datatype.RealDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SingleCharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TimeDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TimestampDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TinyIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

public class RandomReadableValueGenerator implements Serializable {

    private static final long serialVersionUID = -7395540027928402645L;

    public Value createValue(DataType type, Value value) {

        class ValueFactoryVisitor implements DataTypePlusVisitor {

            Value new_value;

            public Value createValue(DataType type, Value value) {
            	new_value = null;
                type.accept(this);
                return new_value;
            }

            @Override
            public void visit(BigIntDataType type, NumericValue value) {
            	new_value = createBigIntDataTypeValue(type, value);
            }

            @Override
            public void visit(BooleanDataType type, BooleanValue value) {
            	new_value = createBooleanDataTypeValue(type, value);
            }

            @Override
            public void visit(CharDataType type, StringValue value) {
            	new_value = createCharDataTypeValue(type, value);
            }

            @Override
            public void visit(DateDataType type, DateValue value) {
            	new_value = createDateDataTypeValue(type, value);
            }

            @Override
            public void visit(DateTimeDataType type, DateTimeValue value) {
            	new_value = createDateTimeDataTypeValue(type, value);
            }

            @Override
            public void visit(DecimalDataType type, NumericValue value) {
            	new_value = createDecimalDataTypeValue(type, value);
            }

            @Override
            public void visit(DoubleDataType type, NumericValue value) {
            	new_value = createDoubleDataTypeValue(type, value);
            }

            @Override
            public void visit(FloatDataType type, NumericValue value) {
            	new_value = createFloatDataTypeValue(type, value);
            }

            @Override
            public void visit(IntDataType type, NumericValue value) {
            	new_value = createIntDataTypeValue(type, value);
            }

            @Override
            public void visit(MediumIntDataType type, NumericValue value) {
            	new_value = createMediumIntDataTypeValue(type, value);
            }

            @Override
            public void visit(NumericDataType type, NumericValue value) {
            	new_value = createNumericDataTypeValue(type, value);
            }

            @Override
            public void visit(RealDataType type, NumericValue value) {
            	new_value = createRealDataTypeValue(type, value);
            }

            @Override
            public void visit(SingleCharDataType type, StringValue value) {
            	new_value = createSingleCharDataTypeValue(type, value);
            }            
            
            @Override
            public void visit(SmallIntDataType type, NumericValue value) {
            	new_value = createSmallIntDataTypeValue(type, value);
            }

            @Override
            public void visit(TextDataType type, StringValue value) {
            	new_value = createTextDataTypeValue(type, value);
            }

            @Override
            public void visit(TimeDataType type, TimeValue value) {
            	new_value = createTimeDataTypeValue(type, value);
            }

            @Override
            public void visit(TimestampDataType type, TimestampValue value) {
            	new_value = createTimestampDataTypeValue(type, value);
            }

            @Override
            public void visit(TinyIntDataType type, NumericValue value) {
            	new_value = createTinyIntDataTypeValue(type, value);
            }

            @Override
            public void visit(VarCharDataType type, StringValue value) {
            	new_value = createVarCharDataTypeValue(type, value);
            }
        }

        return (new ValueFactoryVisitor()).createValue(type, value);
    }

    public Value createBigIntDataTypeValue(BigIntDataType type, NumericValue value) {
        if (type.isSigned()) {
            return new NumericValue("-9223372036854775808", "9223372036854775807");
        } else {
            return new NumericValue("0", "18446744073709551615");
        }
    }

    public Value createBooleanDataTypeValue(BooleanDataType type, BooleanValue value) {
    	BooleanValue new_val = new BooleanValue();
    	Random ran = new Random();
    	boolean v = ran.nextBoolean();
    	new_val.set(v);
        return new_val;
    }

    public Value createCharDataTypeValue(CharDataType type, StringValue value) {
    	int size = value.getLength();
    	if (value.getLength() > 0) {
	    	String result = RandomStringUtils.random(type.getLength(), 0, 20, true, true, value.get().toCharArray());
	    	StringValue new_val = new StringValue(type.getLength());
	    	new_val.set(result);
	        return new_val;
    	} else {
    		String uuid = UUID.randomUUID().toString();
	    	StringValue new_val = new StringValue(type.getLength());
	    	new_val.set(uuid);
    		return new_val;
    	}
    }

    public Value createDateDataTypeValue(DateDataType type, DateValue value) {
		int year = 1950 + (int)Math.round(Math.random() * (2015 - 1950));
		int month = 1 + (int)Math.round(Math.random() * (12 - 1));
		int day = 1 + (int)Math.round(Math.random() * (31 - 1));
        return new DateValue(year, month, day);
    }

    public Value createDateTimeDataTypeValue(DateTimeDataType type, DateTimeValue value) {
		int year = 1950 + (int)Math.round(Math.random() * (2015 - 1950));
		int month = 1 + (int)Math.round(Math.random() * (12 - 1));
		int day = 1 + (int)Math.round(Math.random() * (31 - 1));
		int hour = 1 + (int)Math.round(Math.random() * (60 - 1));
		int mintues = 1 + (int)Math.round(Math.random() * (60 - 1));
		int seconds = 1 + (int)Math.round(Math.random() * (60 - 1));
        return new DateTimeValue(year, month, day, hour, mintues, seconds);
    }

    public Value createDecimalDataTypeValue(DecimalDataType type, NumericValue value) {
        return createPrecisionedAndScaledValue(type);
    }

    public Value createDoubleDataTypeValue(DoubleDataType type, NumericValue value) {
    	Random ran = new Random();
    	BigDecimal ranDo = new BigDecimal(Math.random()); 
        return new NumericValue(ranDo);
        // TODO: set ranges
    }

    public Value createFloatDataTypeValue(FloatDataType type, NumericValue value) {
    	Random ran = new Random();
    	BigDecimal ranDo = new BigDecimal(Math.random()); 
        return new NumericValue(ranDo);
        // TODO: set ranges
    }

    public Value createIntDataTypeValue(IntDataType type, NumericValue value) {
    	Random ran = new Random();
        if (type.isSigned()) {
        	//int ranInt = -2147483648 + (int)Math.round(Math.random() * (-2147483648 - 2147483647));
        	int ranInt = ran.nextInt(2147483647);
            return new NumericValue(ranInt);
        } else {
        	//int ranInt = 0 + (int)Math.round(Math.random() * (0 - 2147483647));
        	int ranInt = ran.nextInt(2147483647);
            return new NumericValue(ranInt);
        }
    }

    public Value createMediumIntDataTypeValue(MediumIntDataType type, NumericValue value) {
        if (type.isSigned()) {
        	int ranInt = -8388608 + (int)Math.round(Math.random() * (-8388608 - 8388607));
            return new NumericValue(ranInt);
        } else {
        	int ranInt = 0 + (int)Math.round(Math.random() * (0 - 16777215));
            return new NumericValue(ranInt);
        }
    }

    public Value createNumericDataTypeValue(NumericDataType type, NumericValue value) {
        return createPrecisionedAndScaledValue(type);
    }

    private Value createPrecisionedAndScaledValue(PrecisionedAndScaled type) {
        Integer precision = type.getPrecision();
        Integer scale = type.getScale();

        if (precision == null) {
            return new NumericValue();
        } else {
            int exponent = precision;
            if (scale != null) {
                exponent -= scale;
            }

            int range = (int) Math.pow(10, exponent);

            int min = -range + 1;
            int max = range - 1;

            return new NumericValue(min, max);
        }
    }

    public Value createRealDataTypeValue(RealDataType type, NumericValue value) {
    	Random ran = new Random();
    	BigDecimal ranDo = new BigDecimal(Math.random()); 
        return new NumericValue(ranDo);
        // TODO: set ranges
    }

    public Value createSingleCharDataTypeValue(SingleCharDataType type, StringValue value) {
        // TODO: need to restrict character type to range -127 to 128
    	String result = RandomStringUtils.random(1, 0, 20, true, true, value.get().toCharArray());
    	StringValue new_val = new StringValue(1);
    	new_val.set(result);
        return new_val;
    }
    
    public Value createSmallIntDataTypeValue(SmallIntDataType type, NumericValue value) {
        if (type.isSigned()) {
        	int ranInt = -32768 + (int)Math.round(Math.random() * (-32768 - 32767));
            return new NumericValue(ranInt);
        } else {
        	int ranInt = 0 + (int)Math.round(Math.random() * (0 - 65535));
            return new NumericValue(ranInt);
        }
    }

    public Value createTextDataTypeValue(TextDataType type, StringValue value) {
    	String result = RandomStringUtils.random(value.getLength(), 0, 20, true, true, value.get().toCharArray());
    	StringValue new_val = new StringValue(value.getLength());
    	new_val.set(result);
        return new_val;
    }

    public Value createTimeDataTypeValue(TimeDataType type, TimeValue value) {
		int hour = 1 + (int)Math.round(Math.random() * (60 - 1));
		int mintues = 1 + (int)Math.round(Math.random() * (60 - 1));
		int seconds = 1 + (int)Math.round(Math.random() * (60 - 1));
        return new TimeValue(hour, mintues, seconds);
    }

    public Value createTimestampDataTypeValue(TimestampDataType type, TimestampValue value) {
		int year = 1950 + (int)Math.round(Math.random() * (2015 - 1950));
		int month = 1 + (int)Math.round(Math.random() * (12 - 1));
		int day = 1 + (int)Math.round(Math.random() * (31 - 1));
		int hour = 1 + (int)Math.round(Math.random() * (60 - 1));
		int mintues = 1 + (int)Math.round(Math.random() * (60 - 1));
		int seconds = 1 + (int)Math.round(Math.random() * (60 - 1));
        return new TimestampValue(year, month, day, hour, mintues, seconds);
    }

    public Value createTinyIntDataTypeValue(TinyIntDataType type, NumericValue value) {
        if (type.isSigned()) {
        	int ranInt = -128 + (int)Math.round(Math.random() * (-128 - 127));
            return new NumericValue(ranInt);
        } else {
        	int ranInt = 0 + (int)Math.round(Math.random() * (0 - 255));
            return new NumericValue(ranInt);
        }
    }

    public Value createVarCharDataTypeValue(VarCharDataType type, StringValue value) {
    	String result = RandomStringUtils.random(type.getLength(), 0, 20, true, true, value.get().toCharArray());
    	StringValue new_val = new StringValue(type.getLength());
    	new_val.set(result);
        return new_val;
    }

}
