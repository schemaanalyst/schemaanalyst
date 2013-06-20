package org.schemaanalyst.sqlrepresentation.datatype;

public class VarCharDataType extends CharDataType {

	private static final long serialVersionUID = 92948344507720958L;
	
	public VarCharDataType(int length) {
		super(length);
	}
	
	public void accept(DataTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}		
}
