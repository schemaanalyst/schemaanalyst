package org.schemaanalyst.sqlrepresentation.datatype;

import org.schemaanalyst.data.*;

public interface DataTypePlusVisitor {

    public void visit(BigIntDataType type, NumericValue value);

    public void visit(BooleanDataType type, BooleanValue value);

    public void visit(CharDataType type, StringValue value);

    public void visit(DateDataType type, DateValue value);

    public void visit(DateTimeDataType type, DateTimeValue value);

    public void visit(DecimalDataType type, NumericValue value);

    public void visit(DoubleDataType type, NumericValue value);

    public void visit(FloatDataType type, NumericValue value);

    public void visit(IntDataType type, NumericValue value);

    public void visit(MediumIntDataType type, NumericValue value);

    public void visit(NumericDataType type, NumericValue value);

    public void visit(RealDataType type, NumericValue value);

    public void visit(SingleCharDataType type, StringValue value);
    
    public void visit(SmallIntDataType type, NumericValue value);

    public void visit(TextDataType type, StringValue value);

    public void visit(TimeDataType type, TimeValue value);

    public void visit(TimestampDataType type, TimestampValue value);

    public void visit(TinyIntDataType type, NumericValue value);

    public void visit(VarCharDataType type, StringValue value);
}
