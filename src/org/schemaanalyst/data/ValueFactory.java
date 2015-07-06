package org.schemaanalyst.data;

import org.schemaanalyst.sqlrepresentation.datatype.*;

import java.io.Serializable;

public class ValueFactory implements Serializable {

    private static final long serialVersionUID = -7295540027928402645L;

    public Value createValue(DataType type) {

        class ValueFactoryVisitor implements DataTypeVisitor {

            Value value;

            public Value createValue(DataType type) {
                value = null;
                type.accept(this);
                return value;
            }

            @Override
            public void visit(BigIntDataType type) {
                value = createBigIntDataTypeValue(type);
            }

            @Override
            public void visit(BooleanDataType type) {
                value = createBooleanDataTypeValue(type);
            }

            @Override
            public void visit(CharDataType type) {
                value = createCharDataTypeValue(type);
            }

            @Override
            public void visit(DateDataType type) {
                value = createDateDataTypeValue(type);
            }

            @Override
            public void visit(DateTimeDataType type) {
                value = createDateTimeDataTypeValue(type);
            }

            @Override
            public void visit(DecimalDataType type) {
                value = createDecimalDataTypeValue(type);
            }

            @Override
            public void visit(DoubleDataType type) {
                value = createDoubleDataTypeValue(type);
            }

            @Override
            public void visit(FloatDataType type) {
                value = createFloatDataTypeValue(type);
            }

            @Override
            public void visit(IntDataType type) {
                value = createIntDataTypeValue(type);
            }

            @Override
            public void visit(MediumIntDataType type) {
                value = createMediumIntDataTypeValue(type);
            }

            @Override
            public void visit(NumericDataType type) {
                value = createNumericDataTypeValue(type);
            }

            @Override
            public void visit(RealDataType type) {
                value = createRealDataTypeValue(type);
            }

            @Override
            public void visit(SingleCharDataType type) {
                value = createSingleCharDataTypeValue(type);
            }            
            
            @Override
            public void visit(SmallIntDataType type) {
                value = createSmallIntDataTypeValue(type);
            }

            @Override
            public void visit(TextDataType type) {
                value = createTextDataTypeValue(type);
            }

            @Override
            public void visit(TimeDataType type) {
                value = createTimeDataTypeValue(type);
            }

            @Override
            public void visit(TimestampDataType type) {
                value = createTimestampDataTypeValue(type);
            }

            @Override
            public void visit(TinyIntDataType type) {
                value = createTinyIntDataTypeValue(type);
            }

            @Override
            public void visit(VarCharDataType type) {
                value = createVarCharDataTypeValue(type);
            }
        }

        return (new ValueFactoryVisitor()).createValue(type);
    }

    public Value createBigIntDataTypeValue(BigIntDataType type) {
        if (type.isSigned()) {
            return new NumericValue("-9223372036854775808", "9223372036854775807");
        } else {
            return new NumericValue("0", "18446744073709551615");
        }
    }

    public Value createBooleanDataTypeValue(BooleanDataType type) {
        return new BooleanValue();
    }

    public Value createCharDataTypeValue(CharDataType type) {
        return new StringValue(type.getLength());
    }

    public Value createDateDataTypeValue(DateDataType type) {
        return new DateValue();
    }

    public Value createDateTimeDataTypeValue(DateTimeDataType type) {
        return new DateTimeValue();
    }

    public Value createDecimalDataTypeValue(DecimalDataType type) {
        return createPrecisionedAndScaledValue(type);
    }

    public Value createDoubleDataTypeValue(DoubleDataType type) {
        return new NumericValue();
        // TODO: set ranges
    }

    public Value createFloatDataTypeValue(FloatDataType type) {
        return new NumericValue();
        // TODO: set ranges
    }

    public Value createIntDataTypeValue(IntDataType type) {
        if (type.isSigned()) {
            return new NumericValue("-2147483648", "2147483647");
        } else {
            return new NumericValue("0", "4294967295");
        }
    }

    public Value createMediumIntDataTypeValue(MediumIntDataType type) {
        if (type.isSigned()) {
            return new NumericValue(-8388608, 8388607);
        } else {
            return new NumericValue(0, 16777215);
        }
    }

    public Value createNumericDataTypeValue(NumericDataType type) {
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

    public Value createRealDataTypeValue(RealDataType type) {
        return new NumericValue();
        // TODO: set ranges
    }

    public Value createSingleCharDataTypeValue(SingleCharDataType type) {
        // TODO: need to restrict character type to range -127 to 128
        return new StringValue(1);
    }
    
    public Value createSmallIntDataTypeValue(SmallIntDataType type) {
        if (type.isSigned()) {
            return new NumericValue(-32768, 32767);
        } else {
            return new NumericValue(0, 65535);
        }
    }

    public Value createTextDataTypeValue(TextDataType type) {
        return new StringValue();
    }

    public Value createTimeDataTypeValue(TimeDataType type) {
        return new TimeValue();
    }

    public Value createTimestampDataTypeValue(TimestampDataType type) {
        return new TimestampValue();
    }

    public Value createTinyIntDataTypeValue(TinyIntDataType type) {
        if (type.isSigned()) {
            return new NumericValue(-128, 127);
        } else {
            return new NumericValue(0, 255);
        }
    }

    public Value createVarCharDataTypeValue(VarCharDataType type) {
        return new StringValue(type.getLength());
    }
}