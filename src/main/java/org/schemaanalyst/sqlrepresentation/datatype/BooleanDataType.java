package org.schemaanalyst.sqlrepresentation.datatype;

public class BooleanDataType extends DataType {

    private static final long serialVersionUID = -4384103397003392234L;

    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }

    @Override
    public void accept(DataTypeCategoryVisitor categoryVisitor) {
        categoryVisitor.visit(this);
    }
    
    public BooleanDataType duplicate() {
    	return new BooleanDataType();
    }
}
