package org.schemaanalyst.sqlrepresentation.datatype;

public class DoubleDataType extends DataType {

    private static final long serialVersionUID = -6971949457336742243L;

    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }

    public void accept(DataTypeCategoryVisitor categoryVisitor) {
        categoryVisitor.visit(this);
    }
}
