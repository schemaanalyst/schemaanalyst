package org.schemaanalyst.schema.attribute;

public class AutoIncrementAttribute extends Attribute {
	
	public void accept(AttributeVisitor visitor) {
		visitor.visit(this);
	}	
}
