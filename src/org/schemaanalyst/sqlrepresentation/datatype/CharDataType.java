package org.schemaanalyst.sqlrepresentation.datatype;

public class CharDataType extends DataType
        implements LengthLimited {

    private static final long serialVersionUID = 1159098580458473495L;
    private Integer length;

    public CharDataType() {
        length = null;
    }

    public CharDataType(Integer length) {
        this.length = length;
    }

    public Integer getLength() {
        return length;
    }

    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }

    public void accept(DataTypeCategoryVisitor categoryVisitor) {
        categoryVisitor.visit((LengthLimited) this);
    }
}
