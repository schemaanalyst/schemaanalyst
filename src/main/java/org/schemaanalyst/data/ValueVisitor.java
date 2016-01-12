package org.schemaanalyst.data;

public interface ValueVisitor {

    public void visit(BooleanValue value);

    public void visit(DateValue value);

    public void visit(DateTimeValue value);

    public void visit(NumericValue value);

    public void visit(StringValue value);

    public void visit(TimeValue value);

    public void visit(TimestampValue value);
}
