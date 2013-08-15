package org.schemaanalyst.sqlrepresentation.datatype;

public class BigIntDataType extends IntDataType {

    private static final long serialVersionUID = -7099333135061494192L;

    public BigIntDataType() {
        super(true);
    }

    public BigIntDataType(boolean signed) {
        super(signed);
    }

    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }
    
    public BigIntDataType duplicate() {
    	return new BigIntDataType(isSigned());
    }
}
