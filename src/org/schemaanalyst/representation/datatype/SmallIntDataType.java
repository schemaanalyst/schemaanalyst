package org.schemaanalyst.representation.datatype;

public class SmallIntDataType extends IntDataType {

	private static final long serialVersionUID = 558676234375466697L;
	
	public SmallIntDataType() {
		super(true);
	}
	
	public SmallIntDataType(boolean signed) {
		super(signed);
	}	
	
	public void accept(DataTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}

}
