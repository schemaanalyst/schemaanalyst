package org.schemaanalyst.sqlrepresentation.datatype;

public class CharDataType extends DataType 
						  implements LengthLimited {
	
	private static final long serialVersionUID = 1159098580458473495L;
	
	private int length;
	
	public CharDataType(int length) {
		this.length = length;
	}
	
	public int getLength() {
		return length;
	}
	
	public void accept(DataTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}
	
	public void accept(DataTypeCategoryVisitor categoryVisitor) {
		categoryVisitor.visit((LengthLimited) this);
	}		
}
