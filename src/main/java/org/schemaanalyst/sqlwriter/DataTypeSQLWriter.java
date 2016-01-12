package org.schemaanalyst.sqlwriter;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.datatype.*;

public class DataTypeSQLWriter {

    public String writeDataType(Column column) {

        class DataTypeSQLWriterVisitor implements DataTypeVisitor {

            String sql;

            public String writeDataType(DataType type) {
                sql = "";
                type.accept(this);
                return sql;
            }

            @Override
            public void visit(BigIntDataType type) {
                sql = writeBigIntDataType(type);
            }

            @Override
            public void visit(BooleanDataType type) {
                sql = writeBooleanDataType(type);
            }

            @Override
            public void visit(CharDataType type) {
                sql = writeCharDataType(type);
            }

            @Override
            public void visit(DateDataType type) {
                sql = writeDateDataType(type);
            }

            @Override
            public void visit(DateTimeDataType type) {
                sql = writeDateTimeDataType(type);
            }

            @Override
            public void visit(DecimalDataType type) {
                sql = writeDecimalDataType(type);
            }

            @Override
            public void visit(DoubleDataType type) {
                sql = writeDoubleDataType(type);
            }

            @Override
            public void visit(FloatDataType type) {
                sql = writeFloatDataType(type);
            }

            @Override
            public void visit(IntDataType type) {
                sql = writeIntDataType(type);
            }

            @Override
            public void visit(MediumIntDataType type) {
                sql = writeMediumIntDataType(type);
            }

            @Override
            public void visit(NumericDataType type) {
                sql = writeNumericDataType(type);
            }

            @Override
            public void visit(RealDataType type) {
                sql = writeRealDataType(type);
            }

            @Override
            public void visit(SingleCharDataType type) {
                sql = writeSingleCharDataType(type);
            }            
            
            @Override
            public void visit(SmallIntDataType type) {
                sql = writeSmallIntDataType(type);
            }

            @Override
            public void visit(TextDataType type) {
                sql = writeTextDataType(type);
            }

            @Override
            public void visit(TimeDataType type) {
                sql = writeTimeDataType(type);
            }

            @Override
            public void visit(TimestampDataType type) {
                sql = writeTimestampDataType(type);
            }

            @Override
            public void visit(TinyIntDataType type) {
                sql = writeTinyIntDataType(type);
            }

            @Override
            public void visit(VarCharDataType type) {
                sql = writeVarCharDataType(type);
            }
        }

        return (new DataTypeSQLWriterVisitor()).writeDataType(column.getDataType());
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

    public String writeSingleCharDataType(SingleCharDataType type) {
        return "CHAR";
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
        Integer precision = type.getPrecision();
        Integer scale = type.getScale();

        String sql = "";
        if (precision != null) {
            sql += "(" + precision;
            if (scale != null) {
                sql += ", " + scale;
            }
            sql += ")";
        }

        return sql;
    }

    private String writeSigned(Signed type) {
        String sql = "";
        if (!type.isSigned()) {
            sql = " UNSIGNED";
        }
        return sql;
    }

    private String writeWidth(LengthLimited type) {
        Integer length = type.getLength();
        String sql = "";
        if (length != null) {
            sql += "(" + length + ")";
        }
        return sql;
    }
}
