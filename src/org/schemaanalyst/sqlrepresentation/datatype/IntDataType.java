package org.schemaanalyst.sqlrepresentation.datatype;

public class IntDataType extends DataType
        implements Signed {

    private static final long serialVersionUID = 748636310116552558L;
    private boolean signed;

    public IntDataType() {
        this(true);
    }

    public IntDataType(boolean signed) {
        this.signed = signed;
    }

    public boolean isSigned() {
        return signed;
    }

    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }

    public void accept(DataTypeCategoryVisitor categoryVisitor) {
        categoryVisitor.visit((Signed) this);
    }
}
