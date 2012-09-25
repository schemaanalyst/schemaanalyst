package org.schemaanalyst.data;

import org.schemaanalyst.schema.columntype.BigIntColumnType;
import org.schemaanalyst.schema.columntype.BooleanColumnType;
import org.schemaanalyst.schema.columntype.CharColumnType;
import org.schemaanalyst.schema.columntype.ColumnType;
import org.schemaanalyst.schema.columntype.ColumnTypeVisitor;
import org.schemaanalyst.schema.columntype.DateColumnType;
import org.schemaanalyst.schema.columntype.DateTimeColumnType;
import org.schemaanalyst.schema.columntype.DecimalColumnType;
import org.schemaanalyst.schema.columntype.DoubleColumnType;
import org.schemaanalyst.schema.columntype.FloatColumnType;
import org.schemaanalyst.schema.columntype.IntColumnType;
import org.schemaanalyst.schema.columntype.IntegerColumnType;
import org.schemaanalyst.schema.columntype.MediumIntColumnType;
import org.schemaanalyst.schema.columntype.NumericColumnType;
import org.schemaanalyst.schema.columntype.RealColumnType;
import org.schemaanalyst.schema.columntype.SmallIntColumnType;
import org.schemaanalyst.schema.columntype.TextColumnType;
import org.schemaanalyst.schema.columntype.TimeColumnType;
import org.schemaanalyst.schema.columntype.TimestampColumnType;
import org.schemaanalyst.schema.columntype.TinyIntColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

public class ValueFactory  {
		
	public Value createValue(ColumnType type) {

		class ValueFactoryVisitor implements ColumnTypeVisitor {
 
			Value value;
			
			public Value createValue(ColumnType type) {
				value = null;
				type.accept(this);
				return value;
			}			
			
			public void visit(BigIntColumnType type) {
				value = createBigIntColumnTypeValue(type);
			}
 
			public void visit(BooleanColumnType type) {
				value = createBooleanColumnTypeValue(type);
			}
 
			public void visit(CharColumnType type) {
				value = createCharColumnTypeValue(type);
			}
 
			public void visit(DateColumnType type) {
				value = createDateColumnTypeValue(type);
			}
 
			public void visit(DateTimeColumnType type) {
				value = createDateTimeColumnTypeValue(type);
			}
 
			public void visit(DecimalColumnType type) {
				value = createDecimalColumnTypeValue(type);
			}
 
			public void visit(DoubleColumnType type) {
				value = createDoubleColumnTypeValue(type);
			}
 
			public void visit(FloatColumnType type) {
				value = createFloatColumnTypeValue(type);
			}
 
			public void visit(IntColumnType type) {
				value = createIntColumnTypeValue(type);
			}
 
			public void visit(IntegerColumnType type) {
				value = createIntegerColumnTypeValue(type);
			}
 
			public void visit(MediumIntColumnType type) {
				value = createMediumIntColumnTypeValue(type);
			}
 
			public void visit(NumericColumnType type) {
				value = createNumericColumnTypeValue(type);
			}
 
			public void visit(RealColumnType type) {
				value = createRealColumnTypeValue(type);
			}
 
			public void visit(SmallIntColumnType type) {
				value = createSmallIntColumnTypeValue(type);
			}
 
			public void visit(TextColumnType type) {
				value = createTextColumnTypeValue(type);
			}
 
			public void visit(TimeColumnType type) {
				value = createTimeColumnTypeValue(type);
			}
 
			public void visit(TimestampColumnType type) {
				value = createTimestampColumnTypeValue(type);
			}
 
			public void visit(TinyIntColumnType type) {
				value = createTinyIntColumnTypeValue(type);
			}
 
			public void visit(VarCharColumnType type) {
				value = createVarCharColumnTypeValue(type);
			}
		}
	
		return (new ValueFactoryVisitor()).createValue(type);
	}	
	
	public Value createBigIntColumnTypeValue(BigIntColumnType type) {
		if (type.isSigned()) {
			return new NumericValue("-9223372036854775808", "9223372036854775807");
		} else {
			return new NumericValue("0", "18446744073709551615"); 
		}
	}
	
	public Value createBooleanColumnTypeValue(BooleanColumnType type) {
		return new BooleanValue();
	}

	public Value createCharColumnTypeValue(CharColumnType type) {
		return new StringValue(type.getLength());
	}	
	
	public Value createDateColumnTypeValue(DateColumnType type) {
		return new DateValue();
	}

	public Value createDateTimeColumnTypeValue(DateTimeColumnType type) {
		return new DateTimeValue();
	}
	
	public Value createDecimalColumnTypeValue(DecimalColumnType type) {
		return new NumericValue();
		// TODO: set ranges
	}
	
	public Value createDoubleColumnTypeValue(DoubleColumnType type) {
		return new NumericValue();
		// TODO: set ranges
	}
	
	public Value createFloatColumnTypeValue(FloatColumnType type) {
		return new NumericValue();
		// TODO: set ranges
	}	
	
	public Value createIntColumnTypeValue(IntColumnType type) {
		if (type.isSigned()) {
			return new NumericValue("-2147483648", "2147483647");
		} else {
			return new NumericValue("0", "4294967295");
		}
	}
	
	public Value createIntegerColumnTypeValue(IntegerColumnType type) {
		return createIntColumnTypeValue(type);
	}
	
	public Value createMediumIntColumnTypeValue(MediumIntColumnType type) {
		if (type.isSigned()) {
			return new NumericValue(-8388608, 8388607);
		} else {
			return new NumericValue(0, 16777215);
		}
	}		
	
	public Value createNumericColumnTypeValue(NumericColumnType type) {
		return new NumericValue();
		// TODO: set ranges
	}
	
	public Value createRealColumnTypeValue(RealColumnType type) {
		return new NumericValue();
		// TODO: set ranges
	}	
	
	public Value createSmallIntColumnTypeValue(SmallIntColumnType type) {
		if (type.isSigned()) {
			return new NumericValue(-32768, 32767);
		} else {
			return new NumericValue(0, 65535);
		}
	}
	
	public Value createTextColumnTypeValue(TextColumnType type)  {
		return new StringValue();
	}
	
	public Value createTimeColumnTypeValue(TimeColumnType type) {
		return new TimeValue();
	}
	
	public Value createTimestampColumnTypeValue(TimestampColumnType type) {
		return new TimestampValue();
	}
	
	public Value createTinyIntColumnTypeValue(TinyIntColumnType type) {
		if (type.isSigned()) {
			return new NumericValue(-128, 127);
		} else {
			return new NumericValue(0, 255);
		}
	}
	
	public Value createVarCharColumnTypeValue(VarCharColumnType type) {
		return new StringValue(type.getLength());
	}	
}