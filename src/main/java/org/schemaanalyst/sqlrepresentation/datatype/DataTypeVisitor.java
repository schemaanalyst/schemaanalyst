package org.schemaanalyst.sqlrepresentation.datatype;

public interface DataTypeVisitor {

    public void visit(BigIntDataType type);

    public void visit(BooleanDataType type);

    public void visit(CharDataType type);

    public void visit(DateDataType type);

    public void visit(DateTimeDataType type);

    public void visit(DecimalDataType type);

    public void visit(DoubleDataType type);

    public void visit(FloatDataType type);

    public void visit(IntDataType type);

    public void visit(MediumIntDataType type);

    public void visit(NumericDataType type);

    public void visit(RealDataType type);

    public void visit(SingleCharDataType type);    
    
    public void visit(SmallIntDataType type);

    public void visit(TextDataType type);

    public void visit(TimeDataType type);

    public void visit(TimestampDataType type);

    public void visit(TinyIntDataType type);

    public void visit(VarCharDataType type);
}
