package org.schemaanalyst.sqlrepresentation.datatype;

public interface DataTypeCategoryVisitor {

    // no category
    public void visit(DataType type);

    public void visit(LengthLimited type);

    public void visit(PrecisionedAndScaled type);

    public void visit(Signed type);
}
