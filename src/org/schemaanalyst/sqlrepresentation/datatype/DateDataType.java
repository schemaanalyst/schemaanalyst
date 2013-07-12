package org.schemaanalyst.sqlrepresentation.datatype;

public class DateDataType extends DataType {

    private static final long serialVersionUID = -4384103397003392234L;

    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }

    public void accept(DataTypeCategoryVisitor categoryVisitor) {
        categoryVisitor.visit(this);
    }
}
