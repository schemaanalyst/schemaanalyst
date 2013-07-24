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

    @Override
    public Integer getLength() {
        return length;
    }

    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }

    @Override
    public void accept(DataTypeCategoryVisitor categoryVisitor) {
        categoryVisitor.visit((LengthLimited) this);
    }
}
