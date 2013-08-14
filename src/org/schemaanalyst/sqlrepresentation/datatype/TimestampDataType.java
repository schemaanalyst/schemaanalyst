package org.schemaanalyst.sqlrepresentation.datatype;

public class TimestampDataType extends DataType {

    private static final long serialVersionUID = -4632277633586907266L;

    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }

    @Override
    public void accept(DataTypeCategoryVisitor categoryVisitor) {
        categoryVisitor.visit(this);
    }
    
    public TimestampDataType duplicate() {
    	return new TimestampDataType();
    }    
}
