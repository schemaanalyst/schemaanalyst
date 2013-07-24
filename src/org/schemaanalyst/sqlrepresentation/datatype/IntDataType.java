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

    @Override
    public boolean isSigned() {
        return signed;
    }

    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }

    @Override
    public void accept(DataTypeCategoryVisitor categoryVisitor) {
        categoryVisitor.visit((Signed) this);
    }
}
