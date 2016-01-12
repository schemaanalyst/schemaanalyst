package org.schemaanalyst.sqlwriter;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.sqlrepresentation.datatype.*;

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
                cell.getColumn().getDataType().accept(this);
                return sql;
            }

            @Override
            public void visit(BigIntDataType type) {
                sql = writeBigIntCell(cell, type);
            }

            @Override
            public void visit(BooleanDataType type) {
                sql = writeBooleanCell(cell, type);
            }

            @Override
            public void visit(CharDataType type) {
                sql = writeCharCell(cell, type);
            }

            @Override
            public void visit(DateDataType type) {
                sql = writeDateCell(cell, type);
            }

            @Override
            public void visit(DateTimeDataType type) {
                sql = writeDateTimeCell(cell, type);
            }

            @Override
            public void visit(DecimalDataType type) {
                sql = writeDecimalCell(cell, type);
            }

            @Override
            public void visit(DoubleDataType type) {
                sql = writeDoubleCell(cell, type);
            }

            @Override
            public void visit(FloatDataType type) {
                sql = writeFloatCell(cell, type);
            }

            @Override
            public void visit(IntDataType type) {
                sql = writeIntCell(cell, type);
            }

            @Override
            public void visit(MediumIntDataType type) {
                sql = writeMediumIntCell(cell, type);
            }

            @Override
            public void visit(NumericDataType type) {
                sql = writeNumericCell(cell, type);
            }

            @Override
            public void visit(RealDataType type) {
                sql = writeRealCell(cell, type);
            }

            @Override
            public void visit(SingleCharDataType type) {
                sql = writeSingleCharCell(cell, type);
            }            
            
            @Override
            public void visit(SmallIntDataType type) {
                sql = writeSmallIntCell(cell, type);
            }

            @Override
            public void visit(TextDataType type) {
                sql = writeTextCell(cell, type);
            }

            @Override
            public void visit(TimeDataType type) {
                sql = writeTimeCell(cell, type);
            }

            @Override
            public void visit(TimestampDataType type) {
                sql = writeTimestampCell(cell, type);
            }

            @Override
            public void visit(TinyIntDataType type) {
                sql = writeTinyIntCell(cell, type);
            }

            @Override
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

    public String writeSingleCharCell(Cell cell, SingleCharDataType type) {
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
