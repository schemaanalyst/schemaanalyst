package org.schemaanalyst.sqlwriter;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.representation.datatype.BigIntDataType;
import org.schemaanalyst.representation.datatype.BooleanDataType;
import org.schemaanalyst.representation.datatype.CharDataType;
import org.schemaanalyst.representation.datatype.DataTypeVisitor;
import org.schemaanalyst.representation.datatype.DateDataType;
import org.schemaanalyst.representation.datatype.DateTimeDataType;
import org.schemaanalyst.representation.datatype.DecimalDataType;
import org.schemaanalyst.representation.datatype.DoubleDataType;
import org.schemaanalyst.representation.datatype.FloatDataType;
import org.schemaanalyst.representation.datatype.IntDataType;
import org.schemaanalyst.representation.datatype.MediumIntDataType;
import org.schemaanalyst.representation.datatype.NumericDataType;
import org.schemaanalyst.representation.datatype.RealDataType;
import org.schemaanalyst.representation.datatype.SmallIntDataType;
import org.schemaanalyst.representation.datatype.TextDataType;
import org.schemaanalyst.representation.datatype.TimeDataType;
import org.schemaanalyst.representation.datatype.TimestampDataType;
import org.schemaanalyst.representation.datatype.TinyIntDataType;
import org.schemaanalyst.representation.datatype.VarCharDataType;

public class CellSQLWriter { 
	
	protected ValueSQLWriter valueSQLWriter;
	
	public void setValueSQLWriter(ValueSQLWriter valueSQLWriter) {
		this.valueSQLWriter = valueSQLWriter;
	}
	
	public String writeCell(Cell cell) {
		
		class CellSQLWriterVisitor implements DataTypeVisitor {
			String sql;
			Cell cell;
			
			public String writeCell(Cell cell) {
				sql = "";
				this.cell = cell;
				cell.getColumn().getType().accept(this);
				return sql;
			}
			
			public void visit(BigIntDataType type) {
				sql = writeBigIntCell(cell, type);
			}

			public void visit(BooleanDataType type) {
				sql = writeBooleanCell(cell, type);
			}

			public void visit(CharDataType type) {
				sql = writeCharCell(cell, type);
			}

			public void visit(DateDataType type) {
				sql = writeDateCell(cell, type);
			}

			public void visit(DateTimeDataType type) {
				sql = writeDateTimeCell(cell, type);
			}

			public void visit(DecimalDataType type) {
				sql = writeDecimalCell(cell, type);				
			}

			public void visit(DoubleDataType type) {
				sql = writeDoubleCell(cell, type);				
			}

			public void visit(FloatDataType type) {
				sql = writeFloatCell(cell, type);				
			}

			public void visit(IntDataType type) {
				sql = writeIntCell(cell, type);				
			}

			public void visit(MediumIntDataType type) {
				sql = writeMediumIntCell(cell, type);				
			}

			public void visit(NumericDataType type) {
				sql = writeNumericCell(cell, type);				
			}

			public void visit(RealDataType type) {
				sql = writeRealCell(cell, type);				
			}

			public void visit(SmallIntDataType type) {
				sql = writeSmallIntCell(cell, type);				
			}

			public void visit(TextDataType type) {
				sql = writeTextCell(cell, type);				
			}

			public void visit(TimeDataType type) {
				sql = writeTimeCell(cell, type);				
			}

			public void visit(TimestampDataType type) {
				sql = writeTimestampCell(cell, type);				
			}

			public void visit(TinyIntDataType type) {
				sql = writeTinyIntCell(cell, type);				
			}

			public void visit(VarCharDataType type) {
				sql = writeVarCharCell(cell, type);				
			}			
		}

		return (new CellSQLWriterVisitor()).writeCell(cell);
	}

	public String writeBigIntCell(Cell cell, BigIntDataType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}

	public String writeBooleanCell(Cell cell, BooleanDataType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}

	public String writeCharCell(Cell cell, CharDataType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}	

	public String writeDateCell(Cell cell, DateDataType type) {
		return valueSQLWriter.writeValue(cell.getValue());		
	}

	public String writeDateTimeCell(Cell cell, DateTimeDataType type) {
		return valueSQLWriter.writeValue(cell.getValue());		
	}

	public String writeDecimalCell(Cell cell, DecimalDataType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}

	public String writeDoubleCell(Cell cell, DoubleDataType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}

	public String writeFloatCell(Cell cell, FloatDataType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}	

	public String writeIntCell(Cell cell, IntDataType type) {
		return valueSQLWriter.writeValue(cell.getValue());		
	}

	public String writeMediumIntCell(Cell cell, MediumIntDataType type) {
		return valueSQLWriter.writeValue(cell.getValue());			
	}	

	public String writeNumericCell(Cell cell, NumericDataType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}

	public String writeRealCell(Cell cell, RealDataType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}	

	public String writeSmallIntCell(Cell cell, SmallIntDataType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}

	public String writeTextCell(Cell cell, TextDataType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}

	public String writeTimeCell(Cell cell, TimeDataType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}

	public String writeTimestampCell(Cell cell, TimestampDataType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}

	public String writeTinyIntCell(Cell cell, TinyIntDataType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}

	public String writeVarCharCell(Cell cell, VarCharDataType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}	
}
