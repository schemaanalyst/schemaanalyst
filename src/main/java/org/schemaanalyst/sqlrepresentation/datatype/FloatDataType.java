package org.schemaanalyst.sqlrepresentation.datatype;

import org.schemaanalyst.data.NumericValue;

public class FloatDataType extends DataType {

    private static final long serialVersionUID = -1350006344393093990L;

    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }

    @Override
    public void accept(DataTypePlusVisitor typePlusVisitor) {
    	typePlusVisitor.visit(this, new NumericValue());
    }
    
    @Override
    public void accept(DataTypeCategoryVisitor categoryVisitor) {
        categoryVisitor.visit(this);
    }
    
    public FloatDataType duplicate() {
    	return new FloatDataType();
    }    
}
