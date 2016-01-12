package org.schemaanalyst.sqlrepresentation.datatype;

public class IntDataType extends DataType implements Signed {

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
    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }

    @Override
    public void accept(DataTypeCategoryVisitor categoryVisitor) {
        categoryVisitor.visit((Signed) this);
    }
    
    public IntDataType duplicate() {
    	return new IntDataType(signed);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (signed ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IntDataType other = (IntDataType) obj;
        return signed == other.signed;
    }
}
