package org.schemaanalyst.sqlrepresentation.datatype;

public class SmallIntDataType extends IntDataType {

    private static final long serialVersionUID = 558676234375466697L;

    public SmallIntDataType() {
        super(true);
    }

    public SmallIntDataType(boolean signed) {
        super(signed);
    }

    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }
    
    public SmallIntDataType duplicate() {
    	return new SmallIntDataType(isSigned());
    }
}
