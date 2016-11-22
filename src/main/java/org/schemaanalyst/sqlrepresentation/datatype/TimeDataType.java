package org.schemaanalyst.sqlrepresentation.datatype;

import org.schemaanalyst.data.TimeValue;

public class TimeDataType extends DataType {

    private static final long serialVersionUID = 2067286896633355675L;

    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }
    
    @Override
    public void accept(DataTypePlusVisitor typePlusVisitor) {
    	typePlusVisitor.visit(this, new TimeValue());
    }

    @Override
    public void accept(DataTypeCategoryVisitor categoryVisitor) {
        categoryVisitor.visit(this);
    }
    
    public TimeDataType duplicate() {
    	return new TimeDataType();
    }       
}
