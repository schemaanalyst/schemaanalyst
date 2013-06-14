package org.schemaanalyst.sqlwriter;

import org.schemaanalyst.representation.Column;
import org.schemaanalyst.representation.datatype.BigIntDataType;
import org.schemaanalyst.representation.datatype.BooleanDataType;
import org.schemaanalyst.representation.datatype.CharDataType;
import org.schemaanalyst.representation.datatype.DataType;
import org.schemaanalyst.representation.datatype.DataTypeVisitor;
import org.schemaanalyst.representation.datatype.DateDataType;
import org.schemaanalyst.representation.datatype.DateTimeDataType;
import org.schemaanalyst.representation.datatype.DecimalDataType;
import org.schemaanalyst.representation.datatype.DoubleDataType;
import org.schemaanalyst.representation.datatype.FloatDataType;
import org.schemaanalyst.representation.datatype.IntDataType;
import org.schemaanalyst.representation.datatype.LengthLimited;
import org.schemaanalyst.representation.datatype.MediumIntDataType;
import org.schemaanalyst.representation.datatype.NumericDataType;
import org.schemaanalyst.representation.datatype.PrecisionedAndScaled;
import org.schemaanalyst.representation.datatype.RealDataType;
import org.schemaanalyst.representation.datatype.Signed;
import org.schemaanalyst.representation.datatype.SmallIntDataType;
import org.schemaanalyst.representation.datatype.TextDataType;
import org.schemaanalyst.representation.datatype.TimeDataType;
import org.schemaanalyst.representation.datatype.TimestampDataType;
import org.schemaanalyst.representation.datatype.TinyIntDataType;
import org.schemaanalyst.representation.datatype.VarCharDataType;

public class DataTypeSQLWriter  {
		
	public String writeDataType(Column column) {
		
		class DataTypeSQLWriterVisitor implements DataTypeVisitor {
			
			String sql;
			
			public String writeDataType(DataType type) {
				sql = "";
				type.accept(this);
				return sql;
			}

			public void visit(BigIntDataType type) {
				sql = writeBigIntDataType(type);
			}

			public void visit(BooleanDataType type) {
				sql = writeBooleanDataType(type);
			}

			public void visit(CharDataType type) {
				sql = writeCharDataType(type);
			}

			public void visit(DateDataType type) {
				sql = writeDateDataType(type);
			}

			public void visit(DateTimeDataType type) {
				sql = writeDateTimeDataType(type);
			}

			public void visit(DecimalDataType type) {
				sql = writeDecimalDataType(type);
			}

			public void visit(DoubleDataType type) {
				sql = writeDoubleDataType(type);
			}

			public void visit(FloatDataType type) {
				sql = writeFloatDataType(type);
			}

			public void visit(IntDataType type) {
				sql = writeIntDataType(type);
			}

			public void visit(MediumIntDataType type) {
				sql = writeMediumIntDataType(type);
			}

			public void visit(NumericDataType type) {
				sql = writeNumericDataType(type);
			}

			public void visit(RealDataType type) {
				sql = writeRealDataType(type);
			}

			public void visit(SmallIntDataType type) {
				sql = writeSmallIntDataType(type);
			}

			public void visit(TextDataType type) {
				sql = writeTextDataType(type);
			}

			public void visit(TimeDataType type) {
				sql = writeTimeDataType(type);			
			}

			public void visit(TimestampDataType type) {
				sql = writeTimestampDataType(type);
			}

			public void visit(TinyIntDataType type) {
				sql = writeTinyIntDataType(type);
			}

			public void visit(VarCharDataType type) {
				sql = writeVarCharDataType(type);
			}
		}
		
		return (new DataTypeSQLWriterVisitor()).writeDataType(column.getType());
	}	
	
	public String writeBigIntDataType(BigIntDataType type) {
		return "BIGINT" + writeSigned(type);
	}
	
	public String writeBooleanDataType(BooleanDataType type) {
		return "BOOLEAN";
	}

	public String writeCharDataType(CharDataType type) {
		return "CHAR" + writeWidth(type);
	}
	
	public String writeDateDataType(DateDataType type) {
		return "DATE";
	}

	public String writeDateTimeDataType(DateTimeDataType type) {
		return "DATETIME";
	}	
	
	public String writeDecimalDataType(DecimalDataType type) {
		return "DECIMAL" + writePrecisionAndScale(type);		
	}
	
	public String writeDoubleDataType(DoubleDataType type) {
		return "DOUBLE";
	}

	public String writeFloatDataType(FloatDataType type) {
		return "FLOAT";
	}
	
	public String writeIntDataType(IntDataType type) {
		return "INT" + writeSigned(type);
	}

	public String writeMediumIntDataType(MediumIntDataType type) {	
		return "MEDIUMINT" + writeSigned(type);		
	}		
		
	public String writeNumericDataType(NumericDataType type) {
		return "NUMERIC" + writePrecisionAndScale(type);
	}
	
	public String writeRealDataType(RealDataType type) {
		return "REAL";
	}
	
	public String writeSmallIntDataType(SmallIntDataType type) {
		return "SMALLINT" + writeSigned(type);
	}
	
	public String writeTextDataType(TextDataType type) {
		return "TEXT";
	}
	
	public String writeTimeDataType(TimeDataType type) {
		return "TIME";
	}
	
	public String writeTimestampDataType(TimestampDataType type) {
		return "TIMESTAMP";
	}
	
	public String writeTinyIntDataType(TinyIntDataType type) {
		return "TINYINT" + writeSigned(type);
	}
	
	public String writeVarCharDataType(VarCharDataType type) {
		return "VARCHAR" + writeWidth(type);		
	}
	
	private String writePrecisionAndScale(PrecisionedAndScaled type) {
		return "(" + type.getPrecision() + "," + type.getScale() + ")";
	}
	
	private String writeSigned(Signed type) {
		String sql = "";
		if (!type.isSigned()) {
			sql =  " UNSIGNED";
		}
		return sql;
	}
	
	private String writeWidth(LengthLimited type) {
		return "("+type.getLength()+")";
	}	
}	

