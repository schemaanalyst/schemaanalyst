package org.schemaanalyst.sqlwriter;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.schema.columntype.BigIntColumnType;
import org.schemaanalyst.schema.columntype.BooleanColumnType;
import org.schemaanalyst.schema.columntype.CharColumnType;
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

public class CellSQLWriter { 
	
	protected ValueSQLWriter valueSQLWriter;
	
	public void setValueSQLWriter(ValueSQLWriter valueSQLWriter) {
		this.valueSQLWriter = valueSQLWriter;
	}
	
	public String writeCell(Cell cell) {
		
		class CellSQLWriterVisitor implements ColumnTypeVisitor {
			String sql;
			Cell cell;
			
			public String writeCell(Cell cell) {
				sql = "";
				this.cell = cell;
				cell.getColumn().getType().accept(this);
				return sql;
			}
			
			public void visit(BigIntColumnType type) {
				sql = writeBigIntCell(cell, type);
			}

			public void visit(BooleanColumnType type) {
				sql = writeBooleanCell(cell, type);
			}

			public void visit(CharColumnType type) {
				sql = writeCharCell(cell, type);
			}

			public void visit(DateColumnType type) {
				sql = writeDateCell(cell, type);
			}

			public void visit(DateTimeColumnType type) {
				sql = writeDateTimeCell(cell, type);
			}

			public void visit(DecimalColumnType type) {
				sql = writeDecimalCell(cell, type);				
			}

			public void visit(DoubleColumnType type) {
				sql = writeDoubleCell(cell, type);				
			}

			public void visit(FloatColumnType type) {
				sql = writeFloatCell(cell, type);				
			}

			public void visit(IntColumnType type) {
				sql = writeIntCell(cell, type);				
			}

			public void visit(IntegerColumnType type) {
				sql = writeIntegerCell(cell, type);				
			}

			public void visit(MediumIntColumnType type) {
				sql = writeMediumIntCell(cell, type);				
			}

			public void visit(NumericColumnType type) {
				sql = writeNumericCell(cell, type);				
			}

			public void visit(RealColumnType type) {
				sql = writeRealCell(cell, type);				
			}

			public void visit(SmallIntColumnType type) {
				sql = writeSmallIntCell(cell, type);				
			}

			public void visit(TextColumnType type) {
				sql = writeTextCell(cell, type);				
			}

			public void visit(TimeColumnType type) {
				sql = writeTimeCell(cell, type);				
			}

			public void visit(TimestampColumnType type) {
				sql = writeTimestampCell(cell, type);				
			}

			public void visit(TinyIntColumnType type) {
				sql = writeTinyIntCell(cell, type);				
			}

			public void visit(VarCharColumnType type) {
				sql = writeVarCharCell(cell, type);				
			}			
		}

		return (new CellSQLWriterVisitor()).writeCell(cell);
	}

	public String writeBigIntCell(Cell cell, BigIntColumnType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}

	public String writeBooleanCell(Cell cell, BooleanColumnType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}

	public String writeCharCell(Cell cell, CharColumnType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}	

	public String writeDateCell(Cell cell, DateColumnType type) {
		return valueSQLWriter.writeValue(cell.getValue());		
	}

	public String writeDateTimeCell(Cell cell, DateTimeColumnType type) {
		return valueSQLWriter.writeValue(cell.getValue());		
	}

	public String writeDecimalCell(Cell cell, DecimalColumnType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}

	public String writeDoubleCell(Cell cell, DoubleColumnType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}

	public String writeFloatCell(Cell cell, FloatColumnType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}	

	public String writeIntCell(Cell cell, IntColumnType type) {
		return valueSQLWriter.writeValue(cell.getValue());		
	}

	public String writeIntegerCell(Cell cell, IntegerColumnType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}

	public String writeMediumIntCell(Cell cell, MediumIntColumnType type) {
		return valueSQLWriter.writeValue(cell.getValue());			
	}	

	public String writeNumericCell(Cell cell, NumericColumnType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}

	public String writeRealCell(Cell cell, RealColumnType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}	

	public String writeSmallIntCell(Cell cell, SmallIntColumnType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}

	public String writeTextCell(Cell cell, TextColumnType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}

	public String writeTimeCell(Cell cell, TimeColumnType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}

	public String writeTimestampCell(Cell cell, TimestampColumnType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}

	public String writeTinyIntCell(Cell cell, TinyIntColumnType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}

	public String writeVarCharCell(Cell cell, VarCharColumnType type) {
		return valueSQLWriter.writeValue(cell.getValue());
	}	
}
