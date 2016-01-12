package org.schemaanalyst.sqlrepresentation.datatype;

public class DoubleDataType extends DataType {

    private static final long serialVersionUID = -6971949457336742243L;

    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }

    @Override
    public void accept(DataTypeCategoryVisitor categoryVisitor) {
        categoryVisitor.visit(this);
    }
    
    public DoubleDataType duplicate() {
    	return new DoubleDataType();
    }      
}
