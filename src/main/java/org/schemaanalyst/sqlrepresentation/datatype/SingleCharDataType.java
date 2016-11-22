package org.schemaanalyst.sqlrepresentation.datatype;

import org.schemaanalyst.data.StringValue;

public class SingleCharDataType extends DataType {

    private static final long serialVersionUID = 7998484156425883831L;

    public SingleCharDataType() {
    }

    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }
    
    @Override
    public void accept(DataTypePlusVisitor typePlusVisitor) {
    	typePlusVisitor.visit(this, new StringValue());
    }

    @Override
    public void accept(DataTypeCategoryVisitor categoryVisitor) {
        categoryVisitor.visit(this);
    }
    
    public SingleCharDataType duplicate() {
    	return new SingleCharDataType();
    }   
}
