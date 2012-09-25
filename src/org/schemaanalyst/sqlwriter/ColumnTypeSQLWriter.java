package org.schemaanalyst.sqlwriter;

import org.schemaanalyst.schema.Column;
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
import org.schemaanalyst.schema.columntype.LengthLimited;
import org.schemaanalyst.schema.columntype.MediumIntColumnType;
import org.schemaanalyst.schema.columntype.NumericColumnType;
import org.schemaanalyst.schema.columntype.PrecisionedAndScaled;
import org.schemaanalyst.schema.columntype.RealColumnType;
import org.schemaanalyst.schema.columntype.Signed;
import org.schemaanalyst.schema.columntype.SmallIntColumnType;
import org.schemaanalyst.schema.columntype.TextColumnType;
import org.schemaanalyst.schema.columntype.TimeColumnType;
import org.schemaanalyst.schema.columntype.TimestampColumnType;
import org.schemaanalyst.schema.columntype.TinyIntColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

public class ColumnTypeSQLWriter  {
		
	public String writeColumnType(Column column) {
		
		class ColumnTypeSQLWriterVisitor implements ColumnTypeVisitor {
			
			String sql;
			
			public String writeColumnType(ColumnType type) {
				sql = "";
				type.accept(this);
				return sql;
			}

			public void visit(BigIntColumnType type) {
				sql = writeBigIntColumnType(type);
			}

			public void visit(BooleanColumnType type) {
				sql = writeBooleanColumnType(type);
			}

			public void visit(CharColumnType type) {
				sql = writeCharColumnType(type);
			}

			public void visit(DateColumnType type) {
				sql = writeDateColumnType(type);
			}

			public void visit(DateTimeColumnType type) {
				sql = writeDateTimeColumnType(type);
			}

			public void visit(DecimalColumnType type) {
				sql = writeDecimalColumnType(type);
			}

			public void visit(DoubleColumnType type) {
				sql = writeDoubleColumnType(type);
			}

			public void visit(FloatColumnType type) {
				sql = writeFloatColumnType(type);
			}

			public void visit(IntColumnType type) {
				sql = writeIntColumnType(type);
			}

			public void visit(IntegerColumnType type) {
				sql = writeIntegerColumnType(type);
			}

			public void visit(MediumIntColumnType type) {
				sql = writeMediumIntColumnType(type);
			}

			public void visit(NumericColumnType type) {
				sql = writeNumericColumnType(type);
			}

			public void visit(RealColumnType type) {
				sql = writeRealColumnType(type);
			}

			public void visit(SmallIntColumnType type) {
				sql = writeSmallIntColumnType(type);
			}

			public void visit(TextColumnType type) {
				sql = writeTextColumnType(type);
			}

			public void visit(TimeColumnType type) {
				sql = writeTimeColumnType(type);			
			}

			public void visit(TimestampColumnType type) {
				sql = writeTimestampColumnType(type);
			}

			public void visit(TinyIntColumnType type) {
				sql = writeTinyIntColumnType(type);
			}

			public void visit(VarCharColumnType type) {
				sql = writeVarCharColumnType(type);
			}
		}
		
		return (new ColumnTypeSQLWriterVisitor()).writeColumnType(column.getType());
	}	
	
	public String writeBigIntColumnType(BigIntColumnType type) {
		return "BIGINT" + writeSigned(type);
	}
	
	public String writeBooleanColumnType(BooleanColumnType type) {
		return "BOOLEAN";
	}

	public String writeCharColumnType(CharColumnType type) {
		return "CHAR" + writeWidth(type);
	}
	
	public String writeDateColumnType(DateColumnType type) {
		return "DATE";
	}

	public String writeDateTimeColumnType(DateTimeColumnType type) {
		return "DATETIME";
	}	
	
	public String writeDecimalColumnType(DecimalColumnType type) {
		return "DECIMAL" + writePrecisionAndScale(type);		
	}
	
	public String writeDoubleColumnType(DoubleColumnType type) {
		return "DOUBLE";
	}

	public String writeFloatColumnType(FloatColumnType type) {
		return "FLOAT";
	}
	
	public String writeIntColumnType(IntColumnType type) {
		return "INT" + writeSigned(type);
	}
	
	public String writeIntegerColumnType(IntegerColumnType type) {
		return "INTEGER" + writeSigned(type);		
	}

	public String writeMediumIntColumnType(MediumIntColumnType type) {	
		return "MEDIUMINT" + writeSigned(type);		
	}		
		
	public String writeNumericColumnType(NumericColumnType type) {
		return "NUMERIC" + writePrecisionAndScale(type);
	}
	
	public String writeRealColumnType(RealColumnType type) {
		return "REAL";
	}
	
	public String writeSmallIntColumnType(SmallIntColumnType type) {
		return "SMALLINT" + writeSigned(type);
	}
	
	public String writeTextColumnType(TextColumnType type) {
		return "TEXT";
	}
	
	public String writeTimeColumnType(TimeColumnType type) {
		return "TIME";
	}
	
	public String writeTimestampColumnType(TimestampColumnType type) {
		return "TIMESTAMP";
	}
	
	public String writeTinyIntColumnType(TinyIntColumnType type) {
		return "TINYINT" + writeSigned(type);
	}
	
	public String writeVarCharColumnType(VarCharColumnType type) {
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

