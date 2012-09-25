package org.schemaanalyst.schema.columntype;

public interface ColumnTypeVisitor {
	
	public void visit(BigIntColumnType type);
	
	public void visit(BooleanColumnType type);

	public void visit(CharColumnType type);	
	
	public void visit(DateColumnType type);
	
	public void visit(DateTimeColumnType type);
	
	public void visit(DecimalColumnType type);
	
	public void visit(DoubleColumnType type);
	
	public void visit(FloatColumnType type);	
	
	public void visit(IntColumnType type);
	
	public void visit(IntegerColumnType type);
	
	public void visit(MediumIntColumnType type);	
	
	public void visit(NumericColumnType type);
	
	public void visit(RealColumnType type);	
	
	public void visit(SmallIntColumnType type);
	
	public void visit(TextColumnType type);
	
	public void visit(TimeColumnType type);
	
	public void visit(TimestampColumnType type);
	
	public void visit(TinyIntColumnType type);
	
	public void visit(VarCharColumnType type);	
}
