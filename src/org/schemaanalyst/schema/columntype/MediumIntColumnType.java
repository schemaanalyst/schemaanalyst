package org.schemaanalyst.schema.columntype;

public class MediumIntColumnType extends IntColumnType {

	private static final long serialVersionUID = -5770480167229887183L;

	public MediumIntColumnType() {
		super(true);
	}
	
	public MediumIntColumnType(boolean signed) {
		super(signed);
	}
	
	public void accept(ColumnTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}
}